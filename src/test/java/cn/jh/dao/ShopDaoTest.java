package cn.jh.dao;

import cn.jh.pojo.Area;
import cn.jh.pojo.PersonInfo;
import cn.jh.pojo.Shop;
import cn.jh.pojo.ShopCategory;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class ShopDaoTest extends BaseTest{
    @Autowired
    private ShopDao shopDao;

    @Test
    public void testInsertShop() throws Exception {
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
        shop.setShopName("mytest1");
        shop.setShopDesc("mytest1");
        shop.setShopAddr("testaddr1");
        shop.setPhone("13810524526");
        shop.setShopImg("test1");
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1, effectedNum);
    }
    @Test
    public void testUpdateShop(){
        Shop shop = new Shop();
        shop.setShopId(7l);
        shop.setShopName("mytest3");
        shop.setShopAddr("mytest3");
        shop.setLastEditTime(new Date());
        int effectedNum=shopDao.updateShop(shop);
        assertEquals(1, effectedNum);

    }
}
