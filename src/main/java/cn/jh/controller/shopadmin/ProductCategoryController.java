package cn.jh.controller.shopadmin;

import cn.jh.dao.ProductCategoryDao;
import cn.jh.dto.ProductCaregoryExecution;
import cn.jh.enums.ProductCategoryStateEnum;
import cn.jh.pojo.ProductCategory;
import cn.jh.pojo.Shop;
import cn.jh.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @ResponseBody
    @RequestMapping(value = "/getproductcategorylist",method = RequestMethod.GET)
    public Map<String,Object>getProductCategoryList(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();

        Shop currentShop= (Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory> productList=null;
        if (currentShop!=null&&currentShop.getShopId()>0){
            productList=productCategoryService.queryProductCategoryList(currentShop.getShopId());
            modelMap.put("productList",productList);
            modelMap.put("success",true);
        }
        else{
            ProductCategoryStateEnum se=ProductCategoryStateEnum.INNER_ERROR;
            modelMap.put("success",false);
            modelMap.put("errMsg",se.getStateInfo());
        }
        return modelMap;
    }

    @ResponseBody
    @RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
    public Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList, HttpServletRequest httpServletRequest) {
        HashMap<String, Object> modelMap = new HashMap<String, Object>();
        Shop currentShop = (Shop) httpServletRequest.getSession().getAttribute("currentShop");
        for (ProductCategory productCategory:productCategoryList){
            productCategory.setShopId(currentShop.getShopId());
        }
        if (productCategoryList.size()>0&&productCategoryList!=null){
            try{
                ProductCaregoryExecution pe = productCategoryService.batchInsertProductCategory(productCategoryList);
                if (pe.getState()==ProductCategoryStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",pe.getStateInfo());
                }

            }
            catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }

        }
        return modelMap;
    }

    @ResponseBody
    @RequestMapping(value = "/removeproductcategory", method = RequestMethod.POST)
    public Map<String, Object> removeProductCategory(Long productCategoryId, HttpServletRequest httpServletRequest) {
        HashMap<String, Object> modelMap = new HashMap<String, Object>();
        Shop currentShop = (Shop) httpServletRequest.getSession().getAttribute("currentShop");
        try {
            ProductCaregoryExecution pe = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
            if (pe.getState()==ProductCategoryStateEnum.SUCCESS.getState()){
                modelMap.put("success",true);
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg",pe.getStateInfo());
            }
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }



}
