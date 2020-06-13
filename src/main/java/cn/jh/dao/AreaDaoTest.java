package cn.jh.dao;

import cn.jh.pojo.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AreaDaoTest extends BaseTest{
    @Autowired
    AreaDao dao;
    @Test
    public void testQuery(){
        List<Area> areas = dao.queryAllArea();
        for (Area area:areas){
            System.out.println(area);
        }
    }
}
