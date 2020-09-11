package cn.jh.service;

import cn.jh.dao.BaseTest;
import cn.jh.dao.ProductDao;
import cn.jh.dto.ImageHolder;
import cn.jh.dto.ProductExecution;
import cn.jh.enums.ProductStateEnum;
import cn.jh.pojo.Product;
import cn.jh.pojo.ProductCategory;
import cn.jh.pojo.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDao productDao;

    @Test
    public void testAddProduct() throws Exception{
        Shop shop = new Shop();
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(5l);
        shop.setShopId(7l);
        Product product = new Product();
        product.setShop(shop);
        product.setProductName("ke kou ke le");
        product.setProductDesc("ke kou ke le");
        product.setPriority(1);
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        product.setCreateTime(new Date());

        File thumbnailFile = new File("/E:/Maven_Product/o2o/pictures/kele1.jpg");
        FileInputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail=new ImageHolder(thumbnailFile.getName(),is);

        File kele1 = new File("/E:/Maven_Product/o2o/pictures/kele1.jpg");
        FileInputStream is1 = new FileInputStream(kele1);
        File kele2 = new File("/E:/Maven_Product/o2o/pictures/kele2.jpg");
        FileInputStream is2 = new FileInputStream(kele2);

        ArrayList<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        productImgList.add(new ImageHolder(kele1.getName(),is1));
        productImgList.add(new ImageHolder(kele2.getName(),is2));

        ProductExecution productExecution = productService.addProduct(product, thumbnail, productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(),productExecution.getState());


    }

    @Test
    public void testModifyProduct()throws Exception{
        Product product = productService.getProductById(3);
        File thumbnailFile = new File("/E:/Maven_Product/o2o/pictures/kele1.jpg");
        FileInputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail=new ImageHolder(thumbnailFile.getName(),is);

        File kele1 = new File("/E:/Maven_Product/o2o/pictures/kele1.jpg");
        FileInputStream is1 = new FileInputStream(kele1);
        File kele2 = new File("/E:/Maven_Product/o2o/pictures/kele2.jpg");
        FileInputStream is2 = new FileInputStream(kele2);

        ArrayList<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        productImgList.add(new ImageHolder(kele1.getName(),is1));
        productImgList.add(new ImageHolder(kele2.getName(),is2));

        ProductExecution productExecution = productService.modifyProduct(product, thumbnail, productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(),productExecution.getState());
    }
}
