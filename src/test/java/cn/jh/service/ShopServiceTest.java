package cn.jh.service;

import cn.jh.dao.BaseTest;
import cn.jh.dto.ShopExecution;
import cn.jh.enums.ShopStateEnum;
import cn.jh.pojo.Area;
import cn.jh.pojo.PersonInfo;
import cn.jh.pojo.Shop;
import cn.jh.pojo.ShopCategory;
import org.apache.commons.fileupload.FileItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopDaoService shopDaoService;
    @Test
    public void testQueryListAndCount(){
        Shop shop = new Shop();
        shop.setShopName("test");
        ShopExecution shopExecution = shopDaoService.queryShopList(shop, 1, 10);
        System.out.println(shopExecution.getShopList().size());
        System.out.println(shopExecution.getCount());

    }
    @Test
    public void testAddShop() throws FileNotFoundException {
        Shop shop = new Shop();
        PersonInfo owner=new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory=new ShopCategory();
        owner.setUserID(1l);
        area.setAreaId(1);
        shopCategory.setShopCategoryId(1l);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("mytest3");
        shop.setShopDesc("mytest3");
        shop.setShopAddr("testaddr3");
        shop.setPhone("13810524526");
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File img= new File("C:/Users/H/Pictures/图标/edge.png");
        InputStream is=new FileInputStream(img);
        ShopExecution shopExecution = shopDaoService.addShop(shop, is,img.getName());
        assertEquals(ShopStateEnum.CHECK.getState(),shopExecution.getState());

    }
}
