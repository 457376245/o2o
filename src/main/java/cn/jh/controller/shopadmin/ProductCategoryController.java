package cn.jh.controller.shopadmin;

import cn.jh.dao.ProductCategoryDao;
import cn.jh.enums.ProductCategoryStateEnum;
import cn.jh.pojo.ProductCategory;
import cn.jh.pojo.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    private ProductCategoryDao productCategoryDao;
    @ResponseBody
    @RequestMapping(value = "/getproductcategorylist",method = RequestMethod.GET)
    public Map<String,Object>getProductCategoryList(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();

        Shop shop = new Shop();
        shop.setShopId(7l);

        request.getSession().setAttribute("currentShop",shop);

        Shop currentShop= (Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory> productList=null;
        if (currentShop!=null&&currentShop.getShopId()>0){
            productList=productCategoryDao.queryProductCategoryById(currentShop.getShopId());
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

}
