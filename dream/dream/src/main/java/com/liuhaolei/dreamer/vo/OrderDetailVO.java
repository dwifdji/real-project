package com.liuhaolei.dreamer.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDetailVO {
	private String id;
	private String name;
	private String type;
	private String address;
	private String duration;
	private String price;
	private String totalArea;
	private String orderDesc;
	private List<String> images;
}
