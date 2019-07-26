package com.liuhaolei.dreamer.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;
import com.liuhaolei.dreamer.common.req.OrderReq;
import com.liuhaolei.dreamer.common.req.OrderReq.OrderListReq;
import com.liuhaolei.dreamer.common.req.OrderReq.SaveOrderReq;
import com.liuhaolei.dreamer.common.res.ResponseModel;
import com.liuhaolei.dreamer.model.Order;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuhaolei
 * @since 2019-01-07
 */
public interface OrderService extends IService<Order> {

	ResponseModel saveOrder(SaveOrderReq req);

	PageInfo getOrderList(OrderListReq orderListReq);

	ResponseModel getDetailById(OrderListReq orderListReq);

	ResponseModel updateOrder(SaveOrderReq req);

	ResponseModel getOrderListView(OrderReq.OrderListReq orderListReq);


}
