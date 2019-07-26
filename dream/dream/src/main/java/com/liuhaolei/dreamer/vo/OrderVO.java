package com.liuhaolei.dreamer.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderVO {
	
	private String orderId;
	private String orderName;
	private String type;
	private String duration;
	private String price;
	private String totalArea;
	private String createAt;
	private String address;
	private String imageUrl;
	
}
