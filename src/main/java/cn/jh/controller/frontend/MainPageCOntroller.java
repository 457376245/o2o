package cn.jh.controller.frontend;

import cn.jh.pojo.HeadLine;
import cn.jh.pojo.ShopCategory;
import cn.jh.service.HeadLineService;
import cn.jh.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Program: o2o
 * @ClassName: MainPageCOntroller
 * @Author: JH
 * @Date: 2020-08-06 17:08
 * @Description: 响应主页请求
 */
@Controller
@RequestMapping(value = "/frontend")
public class MainPageCOntroller {
    @Autowired
    private HeadLineService headLineService;
    @Autowired
    private ShopCategoryService shopCategoryService;

    @ResponseBody
    @RequestMapping(value = "/listmainpageinfo",method = RequestMethod.GET)
    private Map<String,Object> listMainPageInfo(){
        Map<String,Object> modelMap=new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList=new ArrayList<ShopCategory>();
        try {
            //获取一级店铺类别（parentid为空的类别）
            shopCategoryList=shopCategoryService.queryShopCategoryList(null);
            modelMap.put("shopCategoryList",shopCategoryList);
            modelMap.put("success",true);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }List<HeadLine> headLineList=new ArrayList<HeadLine>();
        try {
            HeadLine headLine = new HeadLine();
            headLine.setEnableStatus(1);
            headLineList=headLineService.getHeadLineList(headLine);
            modelMap.put("headLineList",headLineList);
            modelMap.put("success",true);
        }catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }


}
