package cn.jh.dao;

import cn.jh.pojo.ProductImg;

import java.util.List;

public interface ProductImgDao {
    List<ProductImg> queryProductImgList(long productId);

    /**
     * 批量添加商品图片
     * @param productImgList
     * @return
     */

    int batchInsertProductImg(List<ProductImg> productImgList);


    int deleteProductImgByProductId(long productId);
}
