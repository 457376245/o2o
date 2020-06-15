package cn.jh.controller.shopadmin;

import cn.jh.dto.ShopExecution;
import cn.jh.enums.ShopStateEnum;
import cn.jh.pojo.PersonInfo;
import cn.jh.pojo.Shop;
import cn.jh.service.ShopDaoService;
import cn.jh.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import sun.awt.SunHints;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ShopManageController {
    @Autowired
    private ShopDaoService shopService;
    @ResponseBody
    @RequestMapping(value="/registrshop",method = RequestMethod.POST)
    public Map<String,Object>registerShop(HttpServletRequest request)  {
        Map<String,Object> modelMap=new HashMap<String, Object>();
        //1接受并转换相应的参数，包括店铺信息以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper=new ObjectMapper();
        Shop shop=null;
        try{
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
        if (shop!=null&&shopImg!=null){
            PersonInfo owner=new PersonInfo();
            owner.setUserID(1L);
            shop.setOwner(owner);
            ShopExecution se= null;
            try {
                se = shopService.addShop(shop,shopImg.getInputStream(),shopImg.getOriginalFilename());
                if (se.getState()== ShopStateEnum.CHECK.getState()){
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
            modelMap.put("errMsg","请输入店铺信息");
            return modelMap;
        }

    }
}
