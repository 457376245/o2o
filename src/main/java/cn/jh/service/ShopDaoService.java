package cn.jh.service;

import cn.jh.dto.ShopExecution;
import cn.jh.exception.ShopOperationException;
import cn.jh.pojo.Shop;

import java.io.InputStream;


public interface ShopDaoService {
    //通过id查找商铺
    Shop queryByShopId(long shopId);
    ShopExecution modifyShop(Shop shop,InputStream shopImgInputstream,String fileName)throws ShopOperationException;
    /**
     * 创建商铺
     * @throws Exception
     */
    ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName) throws ShopOperationException;
}
