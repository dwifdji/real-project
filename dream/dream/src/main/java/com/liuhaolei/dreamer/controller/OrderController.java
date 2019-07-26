package com.liuhaolei.dreamer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.liuhaolei.dreamer.common.req.OrderReq;
import com.liuhaolei.dreamer.common.req.OrderReq.OrderPay;
import com.liuhaolei.dreamer.common.res.PageInfoRes;
import com.liuhaolei.dreamer.common.res.ResponseModel;
import com.liuhaolei.dreamer.common.res.ResultStatus;
import com.liuhaolei.dreamer.service.OrderService;
import com.liuhaolei.dreamer.service.WXPayService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liuhaolei
 * @since 2019-01-07
 */
@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private WXPayService wxPayService;
	
	/**
	 * 保存订单
	 * @param req
	 * @return
	 */
	@PostMapping("/confind/order/saveOrder.api")
	public ResponseModel saveOrder(@RequestBody OrderReq.SaveOrderReq req) {
		 
		
		return orderService.saveOrder(req);
	}
	
	/**
	 * 修改订单
	 * @param req
	 * @return
	 */
	@PostMapping("/confind/order/updateOrder.api")
	public ResponseModel updateOrder(@RequestBody OrderReq.SaveOrderReq req) {
		
		return orderService.updateOrder(req);
	}
	
	/**
	 * 获取装修订单列表
	 * 
	 */
	@GetMapping("/confind/order/getOrderList.api")
	public ResponseModel getOrderList(OrderReq.OrderListReq orderListReq) {
		PageInfo page = orderService.getOrderList(orderListReq);
		PageInfoRes pageRes = new PageInfoRes(page);
		
		return ResponseModel.successApi(ResultStatus.SUCCESS, pageRes);
	}
	
	/**
	 * 根据id获取详情
	 * @param orderListReq
	 * @return
	 */
	@RequestMapping("/order/getDetailById.api")
	public ResponseModel getDetailById(OrderReq.OrderListReq orderListReq) {
		if(StringUtils.isBlank(orderListReq.getOrderId())) {
			return ResponseModel.failApi(ResultStatus.PARAMS_EMPTY);
		}
		
		return orderService.getDetailById(orderListReq);
	}
	
	
	/**
	 * 这是测试数据
	 * @param orderListReq
	 * @return
	 */
	@RequestMapping("/order/getOrderListView.api")
	public ResponseModel getOrderListView(OrderReq.OrderListReq orderListReq) {
		
		return orderService.getOrderListView(orderListReq);
	}
	
	/**
	 * app发起微信支付生成预订单
	 * @param orderPay
	 * @return
	 */
	@RequestMapping("/order/appPay.api")
	public ResponseModel appPay(OrderReq.OrderPay orderPay) {
		if(StringUtils.isBlank(orderPay.getFee())) {
			return ResponseModel.failApi(ResultStatus.PARAMS_EMPTY);
		}
		
		return wxPayService.appPayOrder(orderPay.getFee());
	}
	
	/**
	 * 
	 * @param res
	 * @param res
	 * @return
	 */
	@RequestMapping("/order/getPayStatus.api")
	public void getPayStatus(HttpServletRequest req, HttpServletResponse res) {
		
		
		wxPayService.getPayStatus(req, res);
	}
	
	 
}

