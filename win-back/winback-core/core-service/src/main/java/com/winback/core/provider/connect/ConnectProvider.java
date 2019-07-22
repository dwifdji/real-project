package com.winback.core.provider.connect;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.arch.common.Device;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.utils.DateUtil;
import com.winback.arch.common.utils.PageUtils;
import com.winback.core.commons.constants.PushEnum;
import com.winback.core.dto.operate.OperateIconDto;
import com.winback.core.facade.connect.ConnectFacade;
import com.winback.core.facade.connect.req.ConnectReq;
import com.winback.core.facade.finance.FinanceFacade;
import com.winback.core.facade.finance.req.FinanceReq;
import com.winback.core.facade.operate.req.OperationReq;
import com.winback.core.model.payment.TFinanceExpend;
import com.winback.core.model.payment.TFinanceInvoice;
import com.winback.core.model.payment.TFinanceReceivable;
import com.winback.core.service.assistant.AssistantService;
import com.winback.core.service.connect.ConnectService;
import com.winback.core.service.finance.FinanceService;
import com.winback.core.vo.connect.HistoryMsgVo;
import com.winback.core.vo.connect.RoomMsgListVO;
import com.winback.core.vo.operate.OperateIconListVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/22 13:50
 */
@Component
@Service(version = "1.0.0")
public class ConnectProvider implements ConnectFacade {

    @Autowired
    private ConnectService connectService;

    @Autowired
    private AssistantService assistantService;


    @Override
    public ResponseModel getHistoryMsgList(ConnectReq.sendReq req) {

        PageInfoResp<HistoryMsgVo> resp  = new PageInfoResp<>();

        PageInfo info = connectService.getHistoryMsgList(req);


        resp.setHasNextPage(info.isHasNextPage());
        resp.setTotal(info.getTotal());
        resp.setList(formatHistoryMsg(info.getList(),req));


        return ResponseModel.succ(resp);
    }

    private List<HistoryMsgVo> formatHistoryMsg(List<HistoryMsgVo> list, ConnectReq.sendReq req) {


        List<HistoryMsgVo> formatVo = new ArrayList<>();

        for(int i = 0 ;i<list.size();i++ ){

            HistoryMsgVo  vo =   list.get(list.size()-1-i);
            vo.setType(vo.getAcctId().equals(req.getAccountId())?"1":"2");

            //语音信息 版本号问题处理
            if("3".equals(vo.getMsgType())&&!PushEnum.PUSH_PERSON_TYPE.SERVICE.getType().equals(req.getPersonType())){
                vo = getMsgInfo(vo,req.getAccountId());

            }


            if(PushEnum.PUSH_PERSON_TYPE.SERVICE.getType().equals(vo.getPersonType())){
                 vo.setName("平台客服");
                if(StringUtils.isNotEmpty(req.getAdminAccountId())){
                    vo.setType("1");
                }
            }

            if(PushEnum.PUSH_PERSON_TYPE.MANAGE.getType().equals(vo.getPersonType())){
                vo.setName("平台项目经理");
            }


            formatVo.add(vo);
        }

        return formatVo;
    }

    private HistoryMsgVo getMsgInfo(HistoryMsgVo vo,String accountId) {
        try {
            //获取版号信息
            Device device = assistantService.getDevice(Integer.valueOf(accountId));

            if(device==null){
                return vo;
            }

            //当前版本号
            String   version = device.getClientVersion();

            String   versionNum = version.replaceAll("\\.","");

            //当前版本号
            if(Integer.valueOf(versionNum)<103){

                vo.setMsgInfo("此条信息为语音消息，想要收听请在应用市场更新赢回来最新版本！");
                vo.setMsgType("1");
                return vo;
            }


        }catch (Exception e) {


        }
        return vo;

    }


    @Override
    public ResponseModel sendMsg(ConnectReq.sendReq req) {

        connectService.sendMsg(req);

        return ResponseModel.succ();
    }

    @Override
    public ResponseModel getRemindInfo(ConnectReq.sendReq req) {
        return connectService.getRemindInfo(req);
    }

    @Override
    public ResponseModel sendRoomMsg(ConnectReq.sendReq req) {

        Integer saveCount = connectService.sendRoomMsg(req);
        if(saveCount == null || saveCount != 1) {
            return ResponseModel.fail();
        }

        return ResponseModel.succ();
    }

    @Override
    public ResponseModel getRoomMsgList(ConnectReq.sendReq req) {
        PageUtils.Page page = connectService.getRoomMsgList(req);

        return ResponseModel.succ(page);
    }

    @Override
    public ResponseModel getRoomMsgHistoryList(ConnectReq.sendReq req) {
        PageInfo pageInfo = connectService.getRoomMsgHistoryList(req);

        PageInfoResp<HistoryMsgVo> resp  = new PageInfoResp<>();

        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        resp.setList(formatHistoryMsg(pageInfo.getList(),req));

        return ResponseModel.succ(resp);
    }

    @Override
    @Transactional
    public ResponseModel sendAdminRoomMsg(ConnectReq.sendReq req) {

        connectService.sendMsg(req);

        updateReplyStatus(req);
        return ResponseModel.succ();
    }

    @Override
    public ResponseModel updateReplyStatus(ConnectReq.sendReq req) {
        Integer saveCount = connectService.updateReplyStatus(req);
        if(saveCount == null || saveCount != 1) {
            return ResponseModel.succ("您未回复数据哦");
        }

        return ResponseModel.succ();
    }

    @Override
    public ResponseModel getWebRoomHistoryMsgList(ConnectReq.sendReq req) {
        PageInfo pageInfo = connectService.getWebRoomHistoryMsgList(req);

        PageInfoResp<HistoryMsgVo> resp  = new PageInfoResp<>();

        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        resp.setList(formatHistoryMsg(pageInfo.getList(),req));
        return ResponseModel.succ(resp);
    }
}
