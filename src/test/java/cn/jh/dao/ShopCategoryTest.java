package cn.jh.dao;

import cn.jh.pojo.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopCategoryTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Test
    public void testQueryShopCategory(){
        ShopCategory shopCategory = new ShopCategory();
        List<ShopCategory> shopCategories = shopCategoryDao.queryShopCategory(shopCategory);
        assertEquals(1,shopCategories.size());

    }
}
