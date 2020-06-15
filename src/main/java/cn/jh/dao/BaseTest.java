package cn.jh.dao;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
/*
* 配置spring和junit整合
* */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件的位置
@ContextConfiguration("classpath:spring/applicationContext.xml")
@WebAppConfiguration("src/main/resources")
public class BaseTest {
}
