package cn.jh.service;

import cn.jh.dto.ImageHolder;
import cn.jh.dto.ShopExecution;
import cn.jh.exception.ShopOperationException;
import cn.jh.pojo.Shop;

import java.io.InputStream;
import java.util.List;


public interface ShopDaoService {
    /**
     * 根据条件分页查询商铺列表
     */
    ShopExecution queryShopList(Shop shopCondition,int pageIndex,int pageSize);
    //通过id查找商铺
    Shop queryByShopId(long shopId);
    /**
     * 更新商铺
     * @throws Exception
     */
    ShopExecution modifyShop(Shop shop, ImageHolder imageHolder)throws ShopOperationException;
    /**
     * 创建商铺
     * @throws Exception
     */
    ShopExecution addShop(Shop shop, ImageHolder imageHolder) throws ShopOperationException;
}
