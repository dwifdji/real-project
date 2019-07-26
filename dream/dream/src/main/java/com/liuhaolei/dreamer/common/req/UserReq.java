package com.liuhaolei.dreamer.common.req;

public class UserReq {
	
	//userModel接收参数
	public static class userModel {
		
		private String userName;
		
		private String passWord;
		
		private String male;

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPassWord() {
			return passWord;
		}

		public void setPassWord(String passWord) {
			this.passWord = passWord;
		}

		public String getMale() {
			return male;
		}

		public void setMale(String male) {
			this.male = male;
		}
		
	}
}
