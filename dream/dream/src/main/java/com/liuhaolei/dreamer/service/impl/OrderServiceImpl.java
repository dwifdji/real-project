package com.liuhaolei.dreamer.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liuhaolei.dreamer.common.req.OrderReq;
import com.liuhaolei.dreamer.common.req.OrderReq.OrderListReq;
import com.liuhaolei.dreamer.common.req.OrderReq.SaveOrderReq;
import com.liuhaolei.dreamer.common.res.PageInfoRes;
import com.liuhaolei.dreamer.common.res.ResponseModel;
import com.liuhaolei.dreamer.common.res.ResultStatus;
import com.liuhaolei.dreamer.mapper.OrderMapper;
import com.liuhaolei.dreamer.model.Order;
import com.liuhaolei.dreamer.service.OrderService;
import com.liuhaolei.dreamer.vo.CategoryVO;
import com.liuhaolei.dreamer.vo.OrderDetailVO;
import com.liuhaolei.dreamer.vo.OrderVO;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuhaolei
 * @since 2019-01-07
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Override
	public ResponseModel saveOrder(SaveOrderReq req) {
		Order order = new Order();
		
		setOrder(order, req);
		
		
		orderMapper.insert(order);
		return ResponseModel.successApi(ResultStatus.SUCCESS, null);
	}
	
	
	private void setOrder(Order order, SaveOrderReq req) {
		order.setOrderName(req.getName());
		order.setAddress(req.getAddress());
		order.setOrderDesc(req.getOrderDesc());
		order.setOrderType(req.getType());
		order.setDuration(req.getDuration());
		order.setPrice(new BigDecimal(req.getPrice()));
		order.setTotalArea(req.getTotalArea());
		order.setCreateAt(new Date());
		order.setUpdateAt(new Date());
		order.setImages(StringUtils.join(req.getImages(), ","));
	}


	@Override
	public PageInfo getOrderList(OrderListReq orderListReq) {
		PageHelper.startPage(orderListReq.getCurrentPage(), orderListReq.getPerPage());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keyWords", orderListReq.getKeyWords());
		params.put("type", orderListReq.getType());
		List<OrderVO> userList = orderMapper.getOrderList(params);
		
		return new PageInfo(userList);
	}

	
	@Override
	public ResponseModel getDetailById(OrderListReq orderListReq) {
		Order order = orderMapper.selectById(orderListReq.getOrderId());
		
		OrderDetailVO orderDetailVO = new OrderDetailVO();
		BeanUtils.copyProperties(order, orderDetailVO);
		orderDetailVO.setAddress(order.getAddress());
		orderDetailVO.setName(order.getOrderName());
		orderDetailVO.setId(order.getId().toString());
		orderDetailVO.setDuration(order.getDuration());
		orderDetailVO.setPrice(String.valueOf(order.getPrice()));
		orderDetailVO.setTotalArea(order.getTotalArea());
		orderDetailVO.setType(order.getOrderType());
		
		String images = order.getImages();
		List<String> imagsList = new ArrayList<String>();
		if(StringUtils.isNotBlank(images)) {
			
			String[] imagesArray = images.split(",");
			 for (String image : imagesArray) {
				 imagsList.add(image);
			}
		}
		orderDetailVO.setImages(imagsList);
		
		return ResponseModel.successApi(ResultStatus.SUCCESS, orderDetailVO);
	}

	
	@Override
	public ResponseModel updateOrder(SaveOrderReq req) {
		Order order = orderMapper.selectById(req.getId());
		
		setOrder(order, req);
		
		orderMapper.updateById(order);
		return ResponseModel.successApi(ResultStatus.SUCCESS, null);
	}


	
	@Override
	public ResponseModel getOrderListView(OrderReq.OrderListReq orderListReq) {
		
		List<CategoryVO> categoryList = orderMapper.getCategory();
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(StringUtils.isNotBlank(orderListReq.getLimitFlag())) {
			Map<String, Object> result = new HashMap<String, Object>();
			
			for (CategoryVO categoryVO : categoryList) {
				params.put("type", categoryVO.getType());
				params.put("limit", Integer.parseInt(orderListReq.getLimitFlag()) );
				List<OrderVO> orderList = orderMapper.getOrderList(params);
				
				categoryVO.setTypeList(orderList);
				result.put("type" + categoryVO.getType(), categoryVO);
			}
			
			return ResponseModel.successApi(ResultStatus.SUCCESS, result);
		}else {
			
			PageHelper.startPage(orderListReq.getCurrentPage(), orderListReq.getPerPage());
			params.put("type", orderListReq.getType());
			params.put("keyWords", orderListReq.getKeyWords());
			List<OrderVO> orderList = orderMapper.getOrderList(params);
			
			PageInfoRes pageRes = new PageInfoRes(new PageInfo<>(orderList));
			return ResponseModel.successApi(ResultStatus.SUCCESS, pageRes);
		}
	}
}
