package com.winback.core.service.connect.Impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.arch.common.Device;
import com.winback.arch.common.HttpUtils;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.common.enums.DeviceType;
import com.winback.arch.common.enums.EmailEnum;
import com.winback.arch.common.enums.MessageTemplateEnum;
import com.winback.arch.common.utils.DateUtil;
import com.winback.arch.common.utils.ExceptionEmailUtil;
import com.winback.arch.common.utils.PageUtils;
import com.winback.arch.core.sysconfig.properties.SystemProperties;
import com.winback.core.commons.constants.PushEnum;
import com.winback.core.condition.connect.TAccountConnectMapCondition;
import com.winback.core.condition.connect.TConnectMsgCondition;
import com.winback.core.condition.connect.TConnectRoomCondition;
import com.winback.core.condition.connect.TConnectRoomPersonCondition;
import com.winback.core.dao._case.TCaseDao;
import com.winback.core.dao.account.TLawyerDao;
import com.winback.core.dao.connect.TAccountConnectMapDao;
import com.winback.core.dao.connect.TConnectMsgDao;
import com.winback.core.dao.connect.TConnectRoomDao;
import com.winback.core.dao.connect.TConnectRoomPersonDao;
import com.winback.core.dto.assistant.PushMsgDto;
import com.winback.core.facade.connect.req.ConnectReq;
import com.winback.core.model._case.TCase;
import com.winback.core.model.account.TLawyer;
import com.winback.core.model.connect.TAccountConnectMap;
import com.winback.core.model.connect.TConnectMsg;
import com.winback.core.model.connect.TConnectRoom;
import com.winback.core.model.connect.TConnectRoomPerson;
import com.winback.core.service.assistant.AssistantService;
import com.winback.core.service.assistant.PushAppMessageService;
import com.winback.core.service.connect.ConnectService;
import com.winback.core.vo.connect.HistoryMsgVo;
import com.winback.core.vo.connect.RoomMsgBackVO;
import com.winback.core.vo.connect.RoomMsgListVO;
import com.winback.core.vo.connect.RoomPersonVo;
import com.winback.gateway.controller.req.email.EmailSendReq;
import com.winback.gateway.controller.req.push.SinglePushReq;
import com.winback.gateway.facade.EmailFacade;
import com.winback.gateway.facade.GeTuiFacade;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/22 15:39
 */
@Service
public class ConnectServiceImpl implements ConnectService {

    @Autowired
    private TCaseDao tCaseDao;
    @Autowired
    private TConnectRoomDao connectRoomDao;
    @Autowired
    private TConnectMsgDao connectMsgDao;
    @Autowired
    private TAccountConnectMapDao accountConnectMapDao;
    @Autowired
    private AssistantService assistantService;
    @Autowired
    private TConnectRoomPersonDao connectRoomPersonDao;
    @Autowired
    private TCaseDao caseDao;
    @Autowired
    private TLawyerDao lawyerDao;
    @Autowired
    private SystemProperties systemProperties;
    @Autowired
    private PushAppMessageService pushMessageService;
    @Reference(version = "1.0.0")
    private GeTuiFacade geTuiFacade;
    @Reference(version = "1.0.0")
    private EmailFacade emailFacade;



    @Override
    @Transactional
    public ResponseModel createRoom(Integer caseId) {
        //根据id 获取案件信息
        TCase tCase = tCaseDao.selectById(caseId);


        if(tCase==null||tCase.getDeleteFlag()){
            return ResponseModel.fail(ApiCallResult.DATA_IS_EMPTY);
        }

        //异步保存信息
        new Thread(()->createRoomThread(caseId,tCase)).start();


        return ResponseModel.succ();
    }

    private void createRoomThread(Integer caseId,TCase tCase) {

        //创建聊天室
        Integer roomId ;

        //获取房间信息
        TConnectRoom room =getRoom(caseId);

        if(room==null){

            roomId = saveRoom(tCase);
        }else{
            roomId = room.getId();
        }

        //添加房间内的人
        saveRoomPerson(tCase,roomId);

    }

    private TConnectRoom getRoom(Integer caseId) {

        TConnectRoomCondition condition = new TConnectRoomCondition();

        condition.setCaseId(caseId);
        condition.setDeleteFlag(false);
        return connectRoomDao.selectFirst(condition);
    }


    /**
     *
     *添加房间内的人
     */
    private void saveRoomPerson(TCase tCase, Integer roomId) {

        List<TConnectRoomPerson>  batch = new ArrayList<>();

        //添加当事人
        TConnectRoomPerson person = new TConnectRoomPerson();
        person.setRoomId(roomId);
        person.setType(PushEnum.PUSH_PERSON_TYPE.PARTY.getType());
        person.setShutupFlag(false);
        person.setDeleteFlag(false);
        person.setCaseId(tCase.getId());
        person.setRelevanceId(tCase.getAccountId());
        person.setCreateTime(DateUtil.getSysTime());
        //添加律师
        TConnectRoomPerson person1 = new TConnectRoomPerson();
        person1.setRoomId(roomId);
        person1.setType(PushEnum.PUSH_PERSON_TYPE.LAWYER.getType());
        person1.setShutupFlag(false);
        person1.setDeleteFlag(false);
        person1.setCaseId(tCase.getId());
        person1.setRelevanceId(tCase.getLawyerAccountId());
        person1.setCreateTime(DateUtil.getSysTime());
        //平台人员
        TConnectRoomPerson admin = new TConnectRoomPerson();
        admin.setRoomId(roomId);
        admin.setType(PushEnum.PUSH_PERSON_TYPE.SERVICE.getType());
        admin.setShutupFlag(false);
        admin.setDeleteFlag(false);
        admin.setRelevanceId(0);
        admin.setCaseId(tCase.getId());
        admin.setCreateTime(DateUtil.getSysTime());

        if(tCase.getLawyerAccountId()==null){
            batch.add(person);
            batch.add(admin);
        }else{
            //看看该房间是否有律师了
            TConnectRoomPersonCondition condition = new TConnectRoomPersonCondition();
            condition.setCaseId(tCase.getId());
            condition.setDeleteFlag(false);
            condition.setType(PushEnum.PUSH_PERSON_TYPE.LAWYER.getType());
            TConnectRoomPerson lawyer = connectRoomPersonDao.selectFirst(condition);
            if(lawyer!=null){
                lawyer.setRelevanceId(tCase.getLawyerAccountId());
                connectRoomPersonDao.updateById(lawyer);
                return;
            }

             batch.add(person1);
         }
        connectRoomPersonDao.batchSave(batch);
    }

    private Integer saveRoom(TCase tCase) {

        TConnectRoom room = new TConnectRoom();

        room.setCaseId(tCase.getId());
        room.setCreateTime(DateUtil.getSysTime());
        room.setName(tCase.getApplyName());
        room.setOpenFlag(false);

        connectRoomDao.insert(room);

        return room.getId();

    }

    @Override
    @Transactional
    public PageInfo getHistoryMsgList(ConnectReq.sendReq req) {

        //获取消息记录
        PageHelper.startPage(req.getPage(), req.getPerPage());

        TConnectMsgCondition condition = new TConnectMsgCondition();
        condition.setCaseId(Integer.valueOf(req.getCaseId()));
        condition.setDeleteFlag(false);
        //condition.setPersonId(Integer.valueOf(req.getAccountId()));
        List<HistoryMsgVo>  list = connectMsgDao.getMsgList(condition);
        for (HistoryMsgVo item : list) {
            if (PushEnum.PUSH_PERSON_TYPE.PARTY.getType().equals(item.getPersonType()) && StringUtils.isNotBlank(item.getAcctId())) {
                TLawyer lawyer = lawyerDao.findByAccountId(Integer.parseInt(item.getAcctId()));
                if (lawyer != null) {
                    item.setName(lawyer.getName());
                    item.setImageUrl(lawyer.getHeadImgUrl());
                }
            }
        }
        //获取了列表 更新未读信息
        deleteUnreadNum(req);

        return new PageInfo<>(list);


    }

    private void deleteUnreadNum(ConnectReq.sendReq req) {
        //获取信息
        TConnectRoomPersonCondition condition = new  TConnectRoomPersonCondition();
        condition.setCaseId(Integer.valueOf(req.getCaseId()));
        condition.setRelevanceId(Integer.valueOf(req.getAccountId()));
        condition.setDeleteFlag(false);
        TConnectRoomPerson person = connectRoomPersonDao.selectFirst(condition);

        if(person==null){
            return;
        }
        person.setUnreadNum(0);
        person.setUpdateTime(DateUtil.getSysTime());
        connectRoomPersonDao.updateById(person);
    }

    @Override
    @Transactional
    public ResponseModel sendMsg(ConnectReq.sendReq req) {
        // 新增客服之后进行修改
        List<RoomPersonVo> personVoList = null;

        if("1".equals(req.getRoomFlag())){
            personVoList = getAdminRoomPerson(req.getRoomId());
        }else{
            personVoList = getRoomPerson(req);
        }

        if(personVoList.size()<1){
            return ResponseModel.fail(ApiCallResult.DATA_IS_EMPTY);
        }
        TCase caseInfo = null;
        if(!"1".equals(req.getRoomFlag())){
            caseInfo = caseDao.selectById(req.getCaseId());
        }

        req.setPersonType(getPersonTypeInfo(personVoList,req));

        saveMsg(req);

        //更新未读信息数量
        updateUnreadNum(req,personVoList);

        String accountId = null;
       //获取用户最近登录的机器
        for(RoomPersonVo vo : personVoList){
            //除了平台后台 都推送
            if(!PushEnum.PUSH_PERSON_TYPE.SERVICE.getType().equals(vo.getType())){
                //不是自己推送 平台当事人不发送
                if("-1".equals(vo.getId())||(vo.getId().equals(req.getAccountId())&&req.getPersonType().equals(vo.getType()))){
                    continue;
                }
                if(!vo.getId().equals(accountId)){
                    pushMsg(req,vo,caseInfo);
                }
                accountId = vo.getId();
            }
        }

        return ResponseModel.succ();
    }


    /**
     *
     *获取发送消息的角色
     */
    private String getPersonTypeInfo(List<RoomPersonVo> personVoList, ConnectReq.sendReq req) {

        if(PushEnum.PUSH_PERSON_TYPE.SERVICE.getType().equals(req.getPersonType())){
            return req.getPersonType();
        }

        for(RoomPersonVo personVo:personVoList){

            if(personVo.getId().equals(req.getAccountId())){

                return personVo.getType();
            }
        }
        return req.getPersonType();
    }

    @Override
    @Transactional
    public Integer sendRoomMsg(ConnectReq.sendReq req) {
        //创建房间
        Integer roomId = null;
        TConnectRoom connectRoom = connectRoomDao.getRomeByAccountId(req.getAccountId());
        Integer saveCount = null;
        if(connectRoom == null) {
            roomId = createNoCaseRoom(req);
            if(roomId == null) {
                return null;
            }
            // 创建房间用户
            saveCount  = saveHomePerson(roomId, req.getPersonType(), req.getAccountId());
        }else {
            roomId = connectRoom.getId();

            // 修改房间用户的未读信息数量
            saveCount  = updateHomeUnreadNum(roomId, req.getAccountId());
        }

        // 保存推送信息
        req.setRoomId(roomId.toString());
        saveMsg(req);

        // 发送邮件给客服
        sendEmailToWaiter(req);

        return saveCount;
    }


    private void sendEmailToWaiter(ConnectReq.sendReq req) {
        String defaultSystemWaiterUrl1 = systemProperties.getDefaultSystemWaiterUrl();
        if(StringUtils.isNotBlank(defaultSystemWaiterUrl1)) {

            String partyName = req.getPartyName() == null ? "客户":req.getPartyName();
            String defaultSystemWaiterUrl = systemProperties.getDefaultSystemWaiterUrl();

            pushMessageService.pushWatersAppList(partyName, defaultSystemWaiterUrl);
        }
    }

    @Override
    public Integer sendAdminRoomMsg(ConnectReq.sendReq req) {
        // 获取房间内的所有人
        List<RoomPersonVo> personVoList = getAdminRoomPerson(req.getRoomId());
        // 保存信息
        saveMsg(req);
        // 更新未读信息数量
        updateUnreadNum(req,personVoList);
        //
        return null;
    }

    @Override
    public Integer updateReplyStatus(ConnectReq.sendReq req) {

            TConnectRoomPersonCondition tConnectRoomPersonCondition = new TConnectRoomPersonCondition();
            tConnectRoomPersonCondition.setRoomId(Integer.parseInt(req.getRoomId()));
            tConnectRoomPersonCondition.setRelevanceId(Integer.parseInt("-1"));
            tConnectRoomPersonCondition.setDeleteFlag(false);

            TConnectRoomPerson tConnectRoomPerson = connectRoomPersonDao.selectOneResult(tConnectRoomPersonCondition);
            tConnectRoomPerson.setUnreadNum(0);

            return connectRoomPersonDao.updateById(tConnectRoomPerson);
    }

    @Override
    @Transactional
    public PageInfo getWebRoomHistoryMsgList(ConnectReq.sendReq req) {
        List<HistoryMsgVo> historyMsgVos = connectMsgDao.getWebRoomHistoryMsgList(req.getAccountId());

        // 清空该用户未读数量
        connectRoomPersonDao.updateUnreadNum(req.getAccountId());

        if(historyMsgVos != null && historyMsgVos.size() > 0) {
            return new PageInfo(historyMsgVos);
        }

        return new PageInfo(new ArrayList());
    }

    private List<RoomPersonVo> getAdminRoomPerson(String roomId) {
        //获取房间内的人员
        TConnectRoomPersonCondition condition = new TConnectRoomPersonCondition();
        condition.setRoomId(Integer.parseInt(roomId));
        condition.setDeleteFlag(false);

        return connectRoomPersonDao.getRoomPersonList(condition);
    }

    @Override
    public PageUtils.Page getRoomMsgList(ConnectReq.sendReq req) {
        List<RoomMsgListVO> roomMsgList = connectRoomPersonDao.getRoomMsgList();

        if(roomMsgList != null && roomMsgList.size() > 0) {
            roomMsgList = processRoomMsgList(roomMsgList, req.getStatus());

            PageUtils.fen(req.getPage(), req.getPerPage(), roomMsgList);
            return PageUtils.fen(req.getPage(), req.getPerPage(), roomMsgList);
        }

        return PageUtils.fen(req.getPage(), req.getPerPage(), new ArrayList<>());
    }

    @Override
    public PageInfo getRoomMsgHistoryList(ConnectReq.sendReq req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());

        List<HistoryMsgVo> historyMsgVos = connectMsgDao.getRoomMsgHistoryList(req.getRoomId());
        if(historyMsgVos != null && historyMsgVos.size() > 0) {
            return new PageInfo(historyMsgVos);
        }

        return new PageInfo(new ArrayList());
    }




    private List<RoomMsgListVO> processRoomMsgList(List<RoomMsgListVO> roomMsgList, String status) {
        List<String> roomIds = new ArrayList<>();

        for (RoomMsgListVO model: roomMsgList) {
            roomIds.add(model.getRoomId());
        }

        List<RoomMsgBackVO> roomMsgBackVOS = connectRoomPersonDao.getRoomMsgBackList(roomIds);
        Iterator<RoomMsgListVO> iterator = roomMsgList.iterator();
        while (iterator.hasNext()){
            RoomMsgListVO model = iterator.next();
            for(RoomMsgBackVO roomMsgBackVO: roomMsgBackVOS) {
                if(roomMsgBackVO.getRoomId().equals(model.getRoomId())
                        &&  roomMsgBackVO.getUnreadNum() == 0) {
                    if(StringUtils.isNotBlank(status) && status.equals(PushEnum.REBACK_STATUS.UNANSWERED.getType())) {
                        iterator.remove();

                    }else{
                        model.setStatus(PushEnum.REBACK_STATUS.REPLIED.getType());
                        model.setStatusDesc(PushEnum.REBACK_STATUS.REPLIED.getTypeDesc());
                    }
                    break;
                }else if(roomMsgBackVO.getRoomId().equals(model.getRoomId())
                        && roomMsgBackVO.getUnreadNum() != 0) {
                    if(StringUtils.isNotBlank(status) && status.equals(PushEnum.REBACK_STATUS.REPLIED.getType())) {
                        iterator.remove();
                    }else{
                        model.setStatus(PushEnum.REBACK_STATUS.UNANSWERED.getType());
                        model.setStatusDesc(PushEnum.REBACK_STATUS.UNANSWERED.getTypeDesc());
                    }
                    break;
                }
            }
        }

        // 迭代器刪除数据
//        if(status != null && status.equals(PushEnum.REBACK_STATUS.UNANSWERED.getType())) {
//            while (iterator.hasNext() && iterator.next().getStatus().equals(PushEnum.REBACK_STATUS.REPLIED.getType())){
//                iterator.remove();
//            }
//        }else if(status != null && status.equals(PushEnum.REBACK_STATUS.REPLIED.getType())) {
//            while (iterator.hasNext() && iterator.next().getStatus().equals(PushEnum.REBACK_STATUS.UNANSWERED.getType())){
//                iterator.remove();
//            }
//        }

        return roomMsgList;
    }

    private Integer updateHomeUnreadNum(Integer roomId, String accountId) {
        TConnectRoomPersonCondition tConnectRoomPersonCondition = new TConnectRoomPersonCondition();
        tConnectRoomPersonCondition.setRoomId(roomId);
        tConnectRoomPersonCondition.setType(PushEnum.PUSH_PERSON_TYPE.SERVICE.getType());
        tConnectRoomPersonCondition.setDeleteFlag(false);

        TConnectRoomPerson tConnectRoomPersonModel = connectRoomPersonDao.
                selectOneResult(tConnectRoomPersonCondition);


        tConnectRoomPersonModel.setUnreadNum(tConnectRoomPersonModel.getUnreadNum() == null ?
                1 : tConnectRoomPersonModel.getUnreadNum() + 1);

        return connectRoomPersonDao.updateById(tConnectRoomPersonModel);
    }


    private Integer saveHomePerson(Integer roomId, String personType, String accountId) {
        for(int i = 0; i < 2; i++) {
            TConnectRoomPerson tConnectRoomPerson = new TConnectRoomPerson();

            if(i == 0) {
                tConnectRoomPerson.setType(personType);
                tConnectRoomPerson.setRelevanceId(Integer.parseInt(accountId));
                tConnectRoomPerson.setUnreadNum(0);
            }else{
                tConnectRoomPerson.setRelevanceId(-1);
                tConnectRoomPerson.setType(PushEnum.PUSH_PERSON_TYPE.SERVICE.getType());
                tConnectRoomPerson.setUnreadNum(1);
            }

            tConnectRoomPerson.setCreateTime(new Date());
            tConnectRoomPerson.setUpdateTime(new Date());
            tConnectRoomPerson.setDeleteFlag(false);
            tConnectRoomPerson.setShutupFlag(false);
            tConnectRoomPerson.setRoomId(roomId);

            connectRoomPersonDao.insert(tConnectRoomPerson);
        }
        return 1;
    }


    private Integer createNoCaseRoom(ConnectReq.sendReq req) {
        TConnectRoom tConnectRoom = new TConnectRoom();
        tConnectRoom.setCreateTime(new Date());
        tConnectRoom.setUpdateTime(new Date());
        tConnectRoom.setOpenFlag(true);
        tConnectRoom.setDeleteFlag(false);

        connectRoomDao.insert(tConnectRoom);

        return tConnectRoom.getId();
    }     private void updateUnreadNum(ConnectReq.sendReq req,List<RoomPersonVo> personVoList) {

        //更新未读信息
        for(RoomPersonVo vo :personVoList){
            //管理员不加
            if(PushEnum.PUSH_PERSON_TYPE.SERVICE.getType().equals(vo.getType())){
                continue;
            }
            //当为自己发的消息时 不加
            if(vo.getId().equals(req.getAccountId())){
                continue;
            }

            TConnectRoomPerson roomPerson = new TConnectRoomPerson();
            roomPerson.setId(Integer.valueOf(vo.getPrimaryId()));
            roomPerson.setUnreadNum(vo.getUnreadNum()+1);
            connectRoomPersonDao.updateById(roomPerson);


        }




    }


    private void saveMsg(ConnectReq.sendReq req) {

        TConnectMsg msg = new TConnectMsg();

        msg.setCreateTime(DateUtil.getSysTime());
        msg.setDeleteFlag(false);
        msg.setMsg(req.getMsgInfo());
        msg.setRoomId(StringUtils.isNotEmpty(req.getRoomId())?Integer.valueOf(req.getRoomId()):null);
        msg.setPersonId(StringUtils.isNotEmpty(req.getAccountId())?Integer.valueOf(req.getAccountId()):null);
        msg.setCaseId(StringUtils.isNotEmpty(req.getCaseId())?Integer.valueOf(req.getCaseId()):null);
        msg.setMsgType(req.getMsgType());
        msg.setSource(req.getSource());
        msg.setPersonType(req.getPersonType());
        //语音信息添加时长字段
        if("3".equals(req.getMsgType())){
            msg.setTimeInfo(getMsgTimeInfo(req.getMsgInfo()));
        }

        connectMsgDao.insert(msg);
    }



    private  String getMsgTimeInfo(String msgInfo) {

        try {
            String url = msgInfo+"?avinfo";
            //获取音频信息
            String  avinfo = HttpUtils.sendGet(url);

            JSONObject jsonObject = JSON.parseObject(avinfo);

            JSONObject format = jsonObject.getJSONObject("format");

            Double duration = Double.valueOf(format.getString("duration"));


            return  String.valueOf(Math.round(duration));

        }catch (Exception e){

            ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.PUSH, msgInfo,"七牛获取音频信息异常", null,e);

        }

        return "5";
    }

    private void pushMsg(ConnectReq.sendReq req, RoomPersonVo vo,TCase caseInfo) {

        //调用gateway个推接口
         Map<String,String> map = getClientId(vo);

         //退出登录的没登录态的 直接返回
         if(map==null){

             return;
         }

        String clientId = map.get("clientId");

        if(StringUtils.isEmpty(clientId)){
            return;
        }

        SinglePushReq pushReq = new SinglePushReq();

        pushReq.setBusType("1".equals(req.getRoomFlag()) ?
                PushEnum.BUS_TYPE.CUSTOMER_SERVICE.getType():PushEnum.BUS_TYPE.CONNECT.getType());
        //安卓添加标题
        if(DeviceType.ANDROID.getKey().equals(map.get("deviceType"))){
            pushReq.setTitle("案件沟通");
        }
        pushReq.setText(!"1".equals(req.getMsgType())?"您有一条新消息":req.getMsgInfo());//当为图片类型时处理/音频时
        pushReq.setClientId(clientId);

        pushReq.setMsgType("1".equals(req.getRoomFlag())?Integer.valueOf(PushEnum.PUSH_TYPE.SERVICE.getType())
                :PushEnum.MSG_TYPE.CONNECT.getType());
        pushReq.setMajorKey("1".equals(req.getRoomFlag())? null:caseInfo.getCaseId());
        pushReq.setChannel(map.get("deviceType"));

        geTuiFacade.pushMessageToSingle(pushReq);

    }

    private Map<String,String> getClientId(RoomPersonVo vo) {
        Map<String,String> clientMap = new HashMap<>();

        //获取缓存中的信息
        Device device = assistantService.getDevice(Integer.valueOf(vo.getId()));

        if(device==null){
            return null;
        }

        clientMap.put("deviceType",device.getDeviceType());

        if(device !=null){
            clientMap.put("clientId",device.getNotificationToken());
            return clientMap;
        }

        //获取用户的client_id、
        TAccountConnectMapCondition condition = new TAccountConnectMapCondition();
        condition.setAccountId(Integer.valueOf(vo.getId()));
        condition.setDeleteFlag(false);

        TAccountConnectMap map = accountConnectMapDao.selectFirst(condition);

        if(map!=null){
            clientMap.put("clientId",device.getNotificationToken());
        }
        return clientMap;
    }

    @Override
    public ResponseModel getRemindInfo(ConnectReq.sendReq req) {

        List<RoomPersonVo> personVoList = getRoomPerson(req);


        if(personVoList.size()<1){
            return ResponseModel.fail(ApiCallResult.DATA_IS_EMPTY);
        }


        StringBuffer names = new StringBuffer();

        for(RoomPersonVo vo: personVoList){

            if(PushEnum.PUSH_PERSON_TYPE.MANAGE.getType().equals(vo.getType())){
                names.append("平台项目经理");
                names.append("、");
            }
            //为当事人时
            if(PushEnum.PUSH_PERSON_TYPE.PARTY.getType().equals(vo.getType())){

                if(StringUtils.isNotEmpty(vo.getLawyerName())){
                    names.append(vo.getLawyerName());
                }else {
                    names.append(StringUtils.isEmpty(vo.getName())?"当事人":vo.getName());
                }
                names.append("、");
            }
            if(PushEnum.PUSH_PERSON_TYPE.LAWYER.getType().equals(vo.getType())){
                names.append(StringUtils.isEmpty(vo.getLawyerName())?"案件律师":"律师"+vo.getLawyerName());
                names.append("、");

            }

        }
        JSONObject jsonObject = new JSONObject();

        String info = names.toString();

        StringBuffer infoSb = new StringBuffer();
        infoSb.append("平台已受理案件，当前案件项目小组已成立，组员为：平台客服、");
        infoSb.append(info.substring(0,info.length()-1));
        infoSb.append(",您可以在这里进行沟通进展。");

        jsonObject.put("info",infoSb.toString());
        jsonObject.put("roomId",personVoList.get(0).getId());

        return ResponseModel.succ(jsonObject);
    }



    private List<RoomPersonVo> getRoomPerson(ConnectReq.sendReq req) {

        //获取房间内的人员
        TConnectRoomPersonCondition condition = new TConnectRoomPersonCondition();
        condition.setCaseId(Integer.valueOf(req.getCaseId()));
        condition.setDeleteFlag(false);

        return connectRoomPersonDao.getRoomPersonList(condition);

    }

}
