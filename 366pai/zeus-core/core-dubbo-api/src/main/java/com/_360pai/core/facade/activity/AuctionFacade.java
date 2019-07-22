package com._360pai.core.facade.activity;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.activity.req.AuctionOfflineFinanceReq;
import com._360pai.core.facade.activity.req.AuctionReq;
import com._360pai.core.facade.activity.resp.DfftResp;
import com._360pai.core.facade.activity.resp.SignContractResp;
import com._360pai.core.facade.activity.vo.AuctionOfflineFinaceVo;
import com._360pai.core.facade.activity.vo.ContractVo;

import java.util.Date;
import java.util.List;

/**
 * @author RuQ
 * @Title: AuctionFacade
 * @ProjectName zeus-core
 * @Description: 拍卖流程
 * @date 2018/9/5 15:27
 */
public interface AuctionFacade {


/**
 *  交保证金
 *
 */
    public boolean payDeposit(AuctionReq req);



 /**
  * 竞拍出价
  */
 public boolean bid(AuctionReq req);


 /**
  * 优先购买人处理
  */
 public boolean yxBuyerDeal(AuctionReq req);


 /**
  * 活动结束处理
  **/

 public boolean activityEndDeal(Integer activityId);


 /**
  * 降价拍自动降价处理
  **/

 public boolean dutchPriceDeal(Integer activityId);


 /**
  * 获取成交协议
  */

 public ContractVo getContractInfo(AuctionReq req);


 /**
  * 签订成交协议
  */

 public SignContractResp signContract(AuctionReq req);


 /**
  * 签订租赁协议
  */

 public SignContractResp signLeaseContract(AuctionReq req);

 /**
  * 机构签订成交协议
  */

 public SignContractResp agencySignContract(AuctionReq req);


 /**
  * 法大大回调
  */
 public boolean signCallBack(String signRole,Integer partyId,Integer activityId,String contractId,boolean hasSuccess);


 /**
  * 发货
  */
public boolean confirmSend(AuctionReq req);


 /**
  * 收货
  */
 public boolean revSend(AuctionReq req);


 public DfftResp pay(AuctionReq req);


 /**
  * 尾款佣金支付成功回调
  * callBackStatus
  * SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS
  * SystemConstant.PAY_ORDER_STATUS_PAID_FAIL
  */
 public void payCallBack(String busId,String payId, String callBackStatus);


 public void insertAuctionStep(Integer activityId,Long orderId,Integer partyId,String step,String req,String resp,String excep,String status);


 void shareCommission(Long orderId);

 Integer dealOnlineActivityData();

 void payTimeoutDeal(Integer activityId);

 void signTimeourDeal(Integer activityId);

 boolean forceExecute(Long orderId);

 ResponseModel searchOfflineFinanceList(AuctionOfflineFinanceReq req);

 ResponseModel getOfflineFinanceDetailById(AuctionOfflineFinanceReq req);

 ResponseModel confirmOfflineFinance(AuctionOfflineFinanceReq req);

 List<AuctionOfflineFinaceVo> searchAllOfflineFinanceList(AuctionOfflineFinanceReq req);

 public ResponseModel invokeFddCreateLeaseDeal(Long orderId, Date currentTime);

 public void insertAuctionPayOrder(String channel,Long orderId,String depositPayId);
}
