package cn.jh.service;

import cn.jh.dto.ShopExecution;
import cn.jh.exception.ShopOperationException;
import cn.jh.pojo.Shop;

import java.io.InputStream;


public interface ShopDaoService {
    /**
     * 创建商铺
     * @throws Exception
     */
    ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName) throws ShopOperationException;
}
