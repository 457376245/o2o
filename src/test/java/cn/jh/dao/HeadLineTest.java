package cn.jh.dao;

import cn.jh.pojo.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @Program: o2o
 * @ClassName: HeadLineTest
 * @Author: JH
 * @Date: 2020-08-06 15:12
 * @Description:
 */
public class HeadLineTest extends BaseTest{
    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void textQueryHeadLine() {
        HeadLine headLine = new HeadLine();
        List<HeadLine> headLines = headLineDao.queryHeadLine(headLine);
        System.out.println(headLines.size());
        assertEquals(2,headLines.size());
    }
}
