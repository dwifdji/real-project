package com.liuhaolei.dreamer.common;

public class ReponseEnum {
	
	public enum OrderCategoryEnum{
		
		ONE_HOUES("1", "一室一厅"),
		TWO_HOUES("2", "两室一厅"),
		THREE_HOUESE("3", "三室一厅");
		
		private String type;
		
		private String title;
		
		
		private OrderCategoryEnum(String type, String title) {
			this.type = type;
			this.title = title;
		}
		
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		
		public static String getTitleByType(String type) {
			
			for (OrderCategoryEnum orderCategoryEnum : OrderCategoryEnum.values()) {
				if(orderCategoryEnum.getType().equals(type)) {
					return orderCategoryEnum.title;
				}
			}
			
			return "";
		}
		
	}
	
	

}
