package cn.jh.service.impl;

import cn.jh.dao.AreaDao;
import cn.jh.pojo.Area;
import cn.jh.service.AreaDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AreaDaoServiceImpl implements AreaDaoService {
    @Autowired
    private AreaDao areaDao;
    public List<Area> queryAllArea() {
        return areaDao.queryAllArea();
    }
}
