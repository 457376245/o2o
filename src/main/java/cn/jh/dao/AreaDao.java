package cn.jh.dao;

import cn.jh.pojo.Area;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AreaDao {
    List<Area> queryAllArea();
}
