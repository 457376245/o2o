package cn.jh.dao;

import cn.jh.pojo.ProductImg;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {
    @Autowired
    private ProductImgDao productImgDao;
    @Test
    public void testABatchInsertProductImg(){
        ProductImg img1 = new ProductImg();
        img1.setImgAddr("img1");
        img1.setImgDesc("img1");
        img1.setPriority(1);
        img1.setCreateTime(new Date());
        img1.setProductId(1L);
        ProductImg img2 = new ProductImg();
        img2.setImgAddr("img2");
        img2.setImgDesc("img2");
        img2.setPriority(1);
        img2.setCreateTime(new Date());
        img2.setProductId(1L);
        List<ProductImg> productImgs=new ArrayList<ProductImg>();
        productImgs.add(img1);
        productImgs.add(img2);
        int i = productImgDao.batchInsertProductImg(productImgs);
        assertEquals(2,i);
    }
}
