package com.winback.arch.common.exception;

@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
public enum ExceptionEnumImpl implements ExceptionEnum {
	DATA_COLLECTION_EXCEPTION("S0001","行为收集系统记录异常！"),
	SEND_SMS_EXCEPTION("S0002","短信发送异常！"),
	SEND_EMAIL_EXCEPTION("S0003","邮件发送异常！"),
	USER_REGISTER_EXCEPTION("Y0001","用户注册失败！"),
	NO_THIS_ITEM("0000","没有此商品"),
	NO_THIS_USER("0001","没有此用户"),
	DB_CANOT_CONNECT("1000","数据库无法连接"),
	INSUFFICIENT_BALANCE("0002","余额不足"),
	PASSWORD_NOT_CORRECT("0003","密码不正确"),
	NO_THIS_FILE("S0004","获取图片信息异常"),
	 SYSTEM_ERROR("SYSTEM_ERROR","系统忙,请稍后再试"),
	 DO_CACHE_ERROR("DO_CACHE_ERROR", "执行缓存操作发生异常"),
	 MOBILE_HAS_AUTH("MOBILE_HAS_AUTH", "用户已认证"),
	IDCARD_HAS_AUTH("IDCARD_HAS_AUTH", "身份证已认证"),
	MOBILE_BEING_AUTH("MOBILE_BEING_AUTH", "正在审核,请勿重复提交"),
	COMPANY_HAS_AUTH("COMPANY_HAS_AUTH", "企业已认证"),
	AGENCY_HAS_AUTH("AGENCY_HAS_AUTH", "机构已认证"),
	SELLER_CANNOT_PAY("SELLER_CANNOT_PAY", "委托人不能参拍"),
	NO_DELEGATION_AGREEMENT("NO_DELEGATION_AGREEMENT", "未签订送拍协议"),
	NO_REQUIRED_AUTH("NO_REQUIRED_AUTH", "未开通东方付通或法大大"),
	AGENCY_NO_REQUIRED_AUTH("NO_REQUIRED_AUTH", "该联排机构未开通东方付通或法大大"),
	INVALID_REQUEST("INVALID_REQUEST", "非法请求"),
	HAS_PAY_DEPOSIT("HAS_PAY_DEPOSIT", "请勿重复交保证金"),
	BE_CONFIRMING_DEPOSIT("BE_CONFIRMING_DEPOSIT", "财务正在确认是否收到您的保证金，请勿重复操作"),
	INVALID_DEPOSIT("INVALID_DEPOSIT", "非法金额"),
	NO_PAY_DEPOSIT("NO_PAY_DEPOSIT", "未付保证金"),
	AUCTION_HAS_END("AUCTION_HAS_END", "拍卖已结束"),
	BEEN_HIGH_PRICE("BEEN_HIGH_PRICE", "您当前已经是最高价"),
	PRICE_TOO_HIGH("PRICE_TOO_HIGH", "不能高于当前价"),
	PRICE_TOO_LESS("PRICE_TOO_LESS", "不能低于当前价"),
	SEALED_ONLY_THREE("SEALED_ONLY_THREE", "暗标出价次数已超过三次"),
	DFFT_HAS_ERROR("DFFT_HAS_ERROR", "调用东方付通错误"),
	BE_PAYING_ERROR("BE_PAYING_ERROR", "支付结果等待中,请勿重复支付"),
	AUTH_CANNOT_SPV("BE_PAYING_ERROR", "认证用户不能申请成为spv"),
	BEING_SPV_ERROR("BE_PAYING_ERROR", "该营业执照或手机号正在审核或已通过,请勿重复提交"),
	PARAM_IS_NULL("PARAM_IS_NULL", "必要参数缺失"),
	DFFT_AMOUNT_NOT_ENOUGH("DFFT_AMOUNT_NOT_ENOUGH", "东方付通余额不足，请充值！"),
	AMOUNT_MUST_BE_EQ_REPRICE("AMOUNT_MUST_BE_EQ_REPRICE", "出价金额必须等于保留价"),
	HAS_NO_ACCT_ERROR("HAS_NO_ACCT_ERROR", "无此账户"),
	AVAIL_AMOUNT_NOT_ENOUGH_ERROR("AVAIL_AMOUNT_NOT_ENOUGH_ERROR", "可用余额不足"),
	DETAIL_STATUS_ERROR("DETAIL_STATUS_ERROR", "只能勾选发票审核通过状态"),
	HAS_IN_BATCH_ERROR("HAS_IN_BATCH_ERROR", "记录已经在其它批次,请勿重复勾选"),
	HAS_SIGN_ERROR("HAS_SIGN_ERROR", "请勿重复签署合同"),

	YX_BEING_ERROR("YX_BEING_ERROR", "优先竞买人环节,无法操作"),
	HAS_APPLY_ERROR("HAS_APPLY_ERROR", "请勿重复申请"),
	HAS_LAST_STEP_ERROR("HAS_LAST_STEP_ERROR", "已经是当前状态最后步骤,无法更新"),
	STATUS_ERROR("STATUS_ERROR", "案件状态只有诉讼或执行才能更新"),
	MUST_SET_LAWYER("MUST_SET_LAWYER", "签约前必须指定承接律师"),
	MUST_RISK_SUCCESS("MUST_RISK_SUCCESS", "签约前必须风险审核通过"),
	MUST_UPLOAD_LAWSUIT("MUST_UPLOAD_LAWSUIT", "签约前必须上传诉讼投资合同"),
	MUST_UPLOAD_EMPOWER_DOC("MUST_UPLOAD_EMPOWER_DOC", "签约前必须上传授权文书"),
	MUST_UPLOAD_FILE("MUST_UPLOAD_FILE", "审核通过前必须上传相应资料"),
	CAN_NOT_DELETE("CAN_NOT_DELETE", "审核通过后不能删除附件"),
	ONLY_RISK_SUCCESS_CAN_SET_LAWYER("ONLY_RISK_SUCCESS_CAN_SET_LAWYER", "有且只有风险审核通过状态才能指派律师"),
	 ;
	// 构造枚举值
    ExceptionEnumImpl(String code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public String toString() { // 覆盖了父类Enum的toString()
		return super.toString() + "(" + code + "," + message + ")";
	}
	
	private String code;
	private String message;
	
	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
