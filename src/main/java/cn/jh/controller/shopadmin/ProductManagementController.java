package cn.jh.controller.shopadmin;


import cn.jh.dto.ImageHolder;
import cn.jh.dto.ProductExecution;
import cn.jh.enums.ProductStateEnum;
import cn.jh.exception.ProductCategoryOperationException;
import cn.jh.pojo.Product;
import cn.jh.pojo.ProductCategory;
import cn.jh.pojo.Shop;
import cn.jh.service.ProductCategoryService;
import cn.jh.service.ProductService;
import cn.jh.util.CodeUtil;
import cn.jh.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/shopadmin")
public class ProductManagementController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    //支持上传最大详情图数量
    private static final int IMAGEMAXCONT=6;

    @RequestMapping(value = "/addproduct")
    @ResponseBody
    private Map<String ,Object>addProduct(HttpServletRequest request) throws Exception{
        Map<String,Object> modelMap=new HashMap<String, Object>();
        //验证码校验
        if (!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","验证码错误");
            return modelMap;
        }
        //接受前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        MultipartHttpServletRequest multipartRequest=null;
        ImageHolder thumbnail=null;
        ArrayList<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            //尝试获取前端传回的product信息并转为实体类
            product=mapper.readValue(productStr,Product.class);
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        try {
            //若请求中存在文件流，则取出相关文件
            if (multipartResolver.isMultipart(request)) {
                thumbnail=handleImage(request,thumbnail, productImgList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        if (product != null && thumbnail != null && productImgList.size() > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute(
                        "currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                ProductExecution pe = productService.addProduct(product,
                        thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;

    }

    @RequestMapping(value = "/getproductcategorylistbyshopId")
    @ResponseBody
    private Map<String ,Object>getProductcategoryListByShopId(HttpServletRequest request) throws ProductCategoryOperationException{
        HashMap<String, Object> modelMap = new HashMap<String, Object>();
        Shop currentShop= (Shop) request.getSession().getAttribute("currentShop");
        if (currentShop!=null&&currentShop.getShopId()!=null){
            try{
                List<ProductCategory> productCategoryList = productCategoryService.queryProductCategoryList(currentShop.getShopId());
                modelMap.put("productCategoryList",productCategoryList);
                modelMap.put("success",true);
            }catch (Exception e){
                modelMap.put("success",false);
            }

        }else {
            modelMap.put("success",false);
        }
        return modelMap;
    }

    @RequestMapping(value = "/getproductbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String ,Object>getProductById(@RequestParam Long productId,HttpServletRequest request){
        HashMap<String, Object> modelMap = new HashMap<String, Object>();
        Shop currentShop = (Shop) request.getSession().getAttribute(
                "currentShop");
        if (productId>-1){
            Product product = productService.getProductById(productId);
            List<ProductCategory> productCategoryList = productCategoryService.queryProductCategoryList(currentShop.getShopId());
            modelMap.put("product",product);
            modelMap.put("productCategoryList",productCategoryList);
            modelMap.put("success",true);
        }else {
            modelMap.put("success",false);
        }
        return modelMap;
    }

    @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyProduct(HttpServletRequest request) throws IOException {
        //判断是修改还是下架
        boolean statusChange = HttpServletRequestUtil.getBoolean(request,
                "statusChange");
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        //接受前端参数并转换为对象
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgs = new ArrayList<ImageHolder>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //若请求中存在文件里，则取出相关文件
        if (multipartResolver.isMultipart(request)) {
            thumbnail=handleImage(request,thumbnail, productImgs);
        }

        try {
            String productStr= HttpServletRequestUtil.getString(request,"productStr");
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if (product != null) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute(
                        "currentShop");
                product.setShop(currentShop);
                ProductExecution pe = productService.modifyProduct(product,
                        thumbnail, productImgs);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    private ImageHolder handleImage(HttpServletRequest request, ImageHolder thumbnail, List<ImageHolder> productImgs) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //取出缩略图并构建ImageHolder对象
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest
                .getFile("thumbnail");
        if (thumbnailFile!=null){
            thumbnail=new ImageHolder(thumbnailFile.getOriginalFilename(),thumbnailFile.getInputStream());
        }
        //取出详情图列表并构建list对象
        for (int i = 0; i < IMAGEMAXCONT; i++) {
            CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest
                    .getFile("productImg" + i);
            if (productImgFile != null) {
                ImageHolder productImg=new ImageHolder(productImgFile.getOriginalFilename(),productImgFile.getInputStream());
                productImgs.add(productImg);
            }else {
                break;
            }
        }
        return thumbnail;
    }
}
