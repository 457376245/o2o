package cn.jh.dao;

import cn.jh.dto.ShopExecution;
import cn.jh.pojo.Area;
import cn.jh.pojo.PersonInfo;
import cn.jh.pojo.Shop;
import cn.jh.pojo.ShopCategory;
import cn.jh.service.ShopDaoService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class ShopDaoTest extends BaseTest{
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private ShopDaoService shopService;

    @Test
    public void testQueryShopCount(){
        Shop shop=new Shop();
        shop.setShopName("test");
        System.out.println(shopDao.queryShopCount(shop));
    }

    @Test
    public void testQueryShopList(){
        Shop shop=new Shop();
        shop.setShopName("test");
        List<Shop> shopList = shopDao.queryShopList(shop, 0, 100);
        System.out.println(shopList.size());
        for (Shop shop1:shopList){
            System.out.println(shop1);
        }
    }
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
    @Test
    public void testQueryShopById(){
        long id=1l;
        try{
            Shop shop = shopDao.queryByShopId(id);
            System.out.println(shop.toString());
        }
        catch (Exception e){
            System.out.println("errot:"+e.getMessage());
        }
    }
    @Test
    public void testModifyShop() throws Exception{
        Shop shop=new Shop();
        shop.setShopId(7l);
        shop.setShopName("修改后的店铺");
        File shopImg=new File("I:/我的文件/图片/图标/斗鱼.png");
        InputStream is=new FileInputStream(shopImg);
        ShopExecution shopExecution = shopService.modifyShop(shop, is, "斗鱼.png");
        System.out.println(shopExecution.toString());

    }
}
