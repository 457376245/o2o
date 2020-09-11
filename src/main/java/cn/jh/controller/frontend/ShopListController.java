package cn.jh.controller.frontend;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jh.dto.ShopExecution;
import cn.jh.pojo.Area;
import cn.jh.pojo.Shop;
import cn.jh.pojo.ShopCategory;
import cn.jh.service.AreaDaoService;
import cn.jh.service.ShopCategoryService;
import cn.jh.service.ShopDaoService;
import cn.jh.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ShopListController {
	@Autowired
	private AreaDaoService areaService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private ShopDaoService shopService;

	@RequestMapping(value = "/listshopspageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long parentId = HttpServletRequestUtil.getLong(request, "parentId");
		List<ShopCategory> shopCategoryList = null;
		if (parentId != -1) {
			try {
				ShopCategory shopCategoryCondition=new ShopCategory();
				ShopCategory parent=new ShopCategory();
				parent.setShopCategoryId(parentId);
				shopCategoryCondition.setParent(parent);
				shopCategoryList = shopCategoryService
						.queryShopCategoryList(shopCategoryCondition);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
		} else {
			try {
				shopCategoryList = shopCategoryService
						.queryShopCategoryList(null);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
		}
		modelMap.put("shopCategoryList", shopCategoryList);
		modelMap.put("success", true);
		List<Area> areaList = null;
		try {
			areaList = areaService.queryAllArea();
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		return modelMap;
	}

	   /**
	   　　* @Description: 根据查询条件返回店铺
	   　　* @param request
	   　　* @return Map
	   　　* @author JH
	   　　* @date 20/8/8 17:00
	   　　*/
	@RequestMapping(value = "/listshops", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShops(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//获取分页信息
		int pageIndex=0;
		int pageSize=10;
		/* pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		 pageSize = HttpServletRequestUtil.getInt(request, "pageSize");*/
		//非空判断
		if ((pageIndex > -1) && (pageSize > -1)) {
			//尝试获取一级类别
			long parentId = HttpServletRequestUtil.getLong(request, "parentId");
			//尝试获取二级类别
			long shopCategoryId = HttpServletRequestUtil.getLong(request,
					"shopCategoryId");
			//尝试获取地区id
			int areaId = HttpServletRequestUtil.getInt(request, "areaId");
			//尝试获取店铺名称
			String shopName = HttpServletRequestUtil.getString(request,
					"shopName");
			Shop shopCondition = compactShopCondition4Search(parentId,
					shopCategoryId, areaId, shopName);
			ShopExecution se = shopService.queryShopList(shopCondition,
					pageIndex, pageSize);
			modelMap.put("shopList", se.getShopList());
			modelMap.put("count", se.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex");
		}

		return modelMap;
	}

	private Shop compactShopCondition4Search(long parentId,
			long shopCategoryId, int areaId, String shopName) {
		Shop shopCondition = new Shop();
		if (parentId != -1L) {
			ShopCategory child=new ShopCategory();
			ShopCategory parent = new ShopCategory();
			parent.setShopCategoryId(parentId);
			child.setParent(parent);
			shopCondition.setShopCategory(child);
		}
		if (shopCategoryId != -1L) {
			ShopCategory shopCategory = new ShopCategory();
			shopCategory.setShopCategoryId(shopCategoryId);
			shopCondition.setShopCategory(shopCategory);
		}
		if (areaId != -1L) {
			Area area = new Area();
			area.setAreaId(areaId);
			shopCondition.setArea(area);
		}

		if (shopName != null) {
			shopCondition.setShopName(shopName);
		}
		shopCondition.setEnableStatus(1);
		return shopCondition;
	}
}
