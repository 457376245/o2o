package cn.jh.service;

import cn.jh.pojo.HeadLine;

import java.util.List;

/**
 * @Program: o2o
 * @ClassName: HeadLineService
 * @Author: JH
 * @Date: 2020-08-06 17:18
 * @Description:
 */
public interface HeadLineService {
       /**
       　　* @Description:
       　　* @param headLineConditon
       　　* @return List<HeadLine>
       　　* @author JH
       　　* @date 20/8/6 17:20
       　　*/
    List<HeadLine>getHeadLineList(HeadLine headLineConditon) throws RuntimeException;
}
