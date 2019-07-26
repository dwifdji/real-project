package com.liuhaolei.dreamer.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.liuhaolei.dreamer.model.Order;
import com.liuhaolei.dreamer.vo.CategoryVO;
import com.liuhaolei.dreamer.vo.OrderVO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuhaolei
 * @since 2019-01-07
 */
public interface OrderMapper extends BaseMapper<Order> {

	List<OrderVO> getOrderList(Map<String, Object> params);

	List<CategoryVO> getCategory();

}
