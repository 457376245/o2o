package cn.jh.dao;

import java.util.List;

import cn.jh.pojo.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopDao {

	//分页查询店铺,可输入的条件有：店铺名（模糊），店铺状态，店铺Id,店铺类别,区域ID
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
							 @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

	//根据传入的shop条件返回queryShopList总数
	int queryShopCount(@Param("shopCondition") Shop shopCondition);

	// 查询店铺
	List<Shop> queryByEmployeeId(long employeeId);

	// 通过owner id 查询店铺
	Shop queryByShopId(long shopId);

	//新增店铺
	int insertShop(Shop shop);


	 // 更新店铺信息
	int updateShop(Shop shop);

	 //删除店铺（初版，即只支持删除尚且没有发布商品的店铺）
	int deleteShopByName(String shopName);

}
