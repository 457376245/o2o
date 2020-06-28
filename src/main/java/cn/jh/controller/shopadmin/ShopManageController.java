package cn.jh.controller.shopadmin;

import cn.jh.dto.ImageHolder;
import cn.jh.dto.ShopExecution;
import cn.jh.enums.ShopStateEnum;
import cn.jh.pojo.Area;
import cn.jh.pojo.PersonInfo;
import cn.jh.pojo.Shop;
import cn.jh.pojo.ShopCategory;
import cn.jh.service.AreaDaoService;
import cn.jh.service.ShopCategoryService;
import cn.jh.service.ShopDaoService;
import cn.jh.util.CodeUtil;
import cn.jh.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping("/shopadmin")
public class ShopManageController {
    @Autowired
    private ShopDaoService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaDaoService areaService;

    @RequestMapping(value = "/getshopmanagementinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object>getShopManagementInfo(HttpServletRequest request){
        Map<String ,Object>modelMap=new HashMap<String, Object>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId<=0){
            Object currentShopObj=request.getSession().getAttribute("currentShop");
            if (currentShopObj==null){
                modelMap.put("redirect",true);
                modelMap.put("url","/o2o/shopadmin/shoplist");
            }else {
                Shop currentShop= (Shop) currentShopObj;
                modelMap.put("redirect",false);
                modelMap.put("shopId",currentShop.getShopId());
            }
        }else {
            Shop currentShop=new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop",currentShop);
            modelMap.put("redirect",false);
        }return modelMap;

    }

    @ResponseBody
    @RequestMapping(value = "/getshoplist",method = RequestMethod.GET)
    public Map<String,Object>getShopList(HttpServletRequest request){
        Map<String,Object>modelMap=new HashMap<String, Object>();
        PersonInfo owner=new PersonInfo();
        owner.setUserID(1l);
        request.getSession().setAttribute("user",owner);
        owner = (PersonInfo) request.getSession().getAttribute("user");
        try {
            Shop shop=new Shop();
            shop.setOwner(owner);
            ShopExecution shopList = shopService.queryShopList(shop, 0, 100);
            modelMap.put("shopList",shopList.getShopList());
            modelMap.put("user",owner);
            modelMap.put("success",true);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());

        }
        return modelMap;

    }

    @RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Long shopId1= (Long) request.getSession().getAttribute("shopId");
        Long shopId=HttpServletRequestUtil.getLong(request,"shopId");
        if (shopId>-1) {
            try {
                Shop shop = shopService.queryByShopId(shopId);
                List<Area> areaList = areaService.queryAllArea();
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        }
        else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }

            return modelMap;
        }

    @ResponseBody
    @RequestMapping(value = "/getshopinitinfo",method = RequestMethod.GET)
    public Map<String,Object>getShopInitInfo(){
        Map<String,Object>modelMap=new HashMap<String, Object>();
        ArrayList<ShopCategory> categoryList = new ArrayList<ShopCategory>();
        ArrayList<Area> areaList = new ArrayList<Area>();
        List<ShopCategory> shopCategories = shopCategoryService.queryShopCategory(new ShopCategory());
        List<Area> areas = areaService.queryAllArea();
        try{
            categoryList= (ArrayList<ShopCategory>) shopCategoryService.queryShopCategory(new ShopCategory());
            areaList= (ArrayList<Area>) areaService.queryAllArea();
            modelMap.put("categoryList",categoryList);
            modelMap.put("areaList",areaList);
            modelMap.put("success",true);

        }
        catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value="/registershop")
    @ResponseBody
    public Map<String,Object>registerShop(HttpServletRequest request)  {
        Map<String,Object> modelMap=new HashMap<String, Object>();
        if (!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        //1接受并转换相应的参数，包括店铺信息以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper=new ObjectMapper();
        Shop shop=null;
        try{
            //将前端json参数转换成shop对象
            shop=mapper.readValue(shopStr,Shop.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg=null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest) request;
            shopImg= (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }
        else {
            modelMap.put("success",false);
            modelMap.put("errMsg","上传图片不能为空");
            return modelMap;

        }
        //2.注册店铺
        if (shop!=null&&shopImg!=null){
            PersonInfo owner=(PersonInfo)request.getSession().getAttribute("user");
            shop.setOwner(owner);
            ShopExecution se= null;
            try {
                ImageHolder imageHolder=new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
                se = shopService.addShop(shop,imageHolder);
                if (se.getState()== ShopStateEnum.CHECK.getState()){
                    modelMap.put("success",true);
                    List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                    if (shopList==null||shopList.size()==0) {
                        shopList=new ArrayList<Shop>();
                    }
                    shopList.add(se.getShop());
                    request.getSession().setAttribute("shopList",shopList);
                }
                else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",se.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺信息");
            return modelMap;
        }

    }
    @RequestMapping(value="/modifyshop")
    @ResponseBody
    public Map<String,Object>modifyShop(HttpServletRequest request)  {
        Map<String,Object> modelMap=new HashMap<String, Object>();
        if (!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        //1接受并转换相应的参数，包括店铺信息以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper=new ObjectMapper();
        Shop shop=null;
        try{
            //将前端json参数转换成shop对象
            shop=mapper.readValue(shopStr,Shop.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg=null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest) request;
            shopImg= (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }
        //2.更新店铺
        if (shop!=null&&shop.getShopId()!=null){
            ShopExecution se= null;
            try {
                if (shopImg==null){
                    se = shopService.modifyShop(shop,null);
                }else {
                    ImageHolder imageHolder=new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
                    se = shopService.modifyShop(shop,imageHolder);
                }
                if (se.getState()== ShopStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }
                else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",se.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺id");
            return modelMap;
        }

    }
}
