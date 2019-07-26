package com.liuhaolei.dreamer.common.req;

import com.liuhaolei.dreamer.model.BaseModel;

import lombok.Getter;
import lombok.Setter;

public class OrderReq {
	
	@Getter
	@Setter
	public static class SaveOrderReq{
		private String id;
		private String name;
		private String type;
		private String address;
		private String duration;
		private String price;
		private String totalArea;
		private String orderDesc;
        private String[] images;
	}
	
	
	@Getter
	@Setter
	public static class OrderListReq extends BaseModel{
		private String keyWords;
			
		private String startAt;
		
		private String endAt;
		
		private String type;
		
		private String orderId;
		
		private String limitFlag;
	}
	
	
	@Getter
	@Setter
	public static class OrderPay extends BaseModel{
		private String fee;
			
	}
	

}
