package cn.jh.controller.superadmincontrol;

import cn.jh.pojo.Area;
import cn.jh.service.AreaDaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/superadmin")
public class AreaController {
    Logger logger= LoggerFactory.getLogger(AreaController.class);
    @Autowired
    private AreaDaoService areaDaoService;
    @RequestMapping(value = "/listarea",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getAllArea(){
        logger.info("==========start======");
        long startTime=System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Area> list = new ArrayList<Area>();
        try{
            list=areaDaoService.queryAllArea();
            map.put("totle",list.size());
            map.put("row",list);
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
            map.put("errMsg",e.toString());

        }
        long currentTime=System.currentTimeMillis();
        logger.debug("costTime:[{}ms]",currentTime-startTime);
        return map;
    }
    public String test(){
        return "test";
    }
}
