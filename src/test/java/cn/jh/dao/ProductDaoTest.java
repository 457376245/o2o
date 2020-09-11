package cn.jh.dao;

import cn.jh.pojo.Product;
import cn.jh.pojo.ProductCategory;
import cn.jh.pojo.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ProductDaoTest extends BaseTest{
    @Autowired
    private  ProductDao productDao;
    @Test
    public void testProductDaoInsert(){
        Shop shop=new Shop();
        shop.setShopId(7l);
        ProductCategory category=new ProductCategory();
        category.setProductCategoryId(5l);
        Product product=new Product();
        Product product2=new Product();
        Product product3=new Product();
        product.setShop(shop);
        product.setProductName("测试1");
        product.setProductDesc("测试1");
        product.setImgAddr("测试1");
        product.setPriority(1);
        product.setEnableStatus(1);
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setProductCategory(category);


        product2.setShop(shop);
        product2.setProductName("测试2");
        product2.setProductDesc("测试2");
        product2.setImgAddr("测试2");
        product2.setPriority(1);
        product2.setCreateTime(new Date());
        product2.setLastEditTime(new Date());
        product2.setProductCategory(category);
        product2.setEnableStatus(1);

        product3.setShop(shop);
        product3.setProductName("测试3");
        product3.setProductDesc("测试3");
        product3.setImgAddr("测试3");
        product3.setPriority(1);
        product3.setCreateTime(new Date());
        product3.setLastEditTime(new Date());
        product3.setProductCategory(category);
        product3.setEnableStatus(1);

        int i = productDao.insertProduct(product);
        assertEquals(1,i);
        int i1 = productDao.insertProduct(product2);
        assertEquals(1,i1);
        int i2 = productDao.insertProduct(product3);
        assertEquals(1,i2);
    }

    @Test
    public void testQueryProductByProductId(){
        Product product = productDao.queryProductByProductId(2);
        System.out.println(product);
    }

    @Test
    public void testDelProduct(){
        int res = productDao.deleteProductByProductId(2,7);
        assertEquals(1,res);
    }
}
