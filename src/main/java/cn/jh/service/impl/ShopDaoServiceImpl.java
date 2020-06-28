package cn.jh.service.impl;

import cn.jh.dao.ShopDao;
import cn.jh.dto.ImageHolder;
import cn.jh.dto.ShopExecution;
import cn.jh.enums.ShopStateEnum;
import cn.jh.exception.ShopOperationException;
import cn.jh.pojo.Shop;
import cn.jh.service.ShopDaoService;
import cn.jh.util.FileUtil;
import cn.jh.util.ImageUtil;
import cn.jh.util.PageCalulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class ShopDaoServiceImpl implements ShopDaoService {
    @Autowired
    private ShopDao shopDao;

    @Override
    public ShopExecution queryShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex= PageCalulator.calculateRowIndex(pageIndex,pageSize);
        List<Shop>shopList=shopDao.queryShopList(shopCondition,rowIndex,pageSize);
        int count=shopDao.queryShopCount(shopCondition);
        ShopExecution se=new ShopExecution();
        if (shopList!=null){
            se.setShopList(shopList);
            se.setCount(count);
        }else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    @Override
    public Shop queryByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, ImageHolder imageHolder) throws ShopOperationException {
        //1.判断是否需要处理图片
        if (shop==null||shop.getShopId()==null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
        }
        else {
            try{
                if (imageHolder.getImageName()!=null&&imageHolder.getImageName()!=null&&!"".equals(imageHolder.getImageName())){
                    Shop tempShop=shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopImg()!=null){
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop,imageHolder);
                }
                //2.更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum=shopDao.updateShop(shop);
                if (effectedNum<=0){
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                }else {
                    shop=shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS,shop);
                }
            }catch (Exception e){
                throw new ShopOperationException("modifyShop error:"+e.getMessage());

            }
        }
    }

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, ImageHolder imageHolder) throws ShopOperationException {
        if(shop==null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
        }
        try {
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            int effectedNum=shopDao.insertShop(shop);
            if (effectedNum<=0){
                throw new ShopOperationException("店铺创建失败");
            }
            else {
                if (imageHolder.getImage() !=null){
                    try{
                        addShopImg(shop, imageHolder);
                    }
                    catch (Exception e){
                        throw new ShopOperationException("addShopImg error:"+e.getMessage());
                    }
                    effectedNum=shopDao.updateShop(shop);
                    if (effectedNum<=0){
                        throw new ShopOperationException("更新图片失败");
                    }
                }
            }
        }
        catch (Exception e){
            throw new ShopOperationException("addShop error:"+e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }
    private void addShopImg(Shop shop,ImageHolder imageHolder){
        //获取shop图片目录的相对值路径
        String dest= FileUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr= ImageUtil.generateThumbnail(imageHolder,dest);
        shop.setShopImg(shopImgAddr);
    }
}
