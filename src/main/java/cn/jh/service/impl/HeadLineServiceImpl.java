package cn.jh.service.impl;

import cn.jh.dao.HeadLineDao;
import cn.jh.pojo.HeadLine;
import cn.jh.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Program: o2o
 * @ClassName: HeadLineServiceImpl
 * @Author: JH
 * @Date: 2020-08-06 17:21
 * @Description:
 */
@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;

    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineConditon) throws RuntimeException {

        return headLineDao.queryHeadLine(headLineConditon);
    }
}
