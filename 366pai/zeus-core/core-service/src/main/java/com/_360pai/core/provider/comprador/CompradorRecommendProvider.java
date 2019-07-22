package com._360pai.core.provider.comprador;

import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.OrderCodeGenerator;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.common.constants.CompradorEnum;
import com._360pai.core.condition.comprador.TCompradorRecommendCondition;
import com._360pai.core.facade.comprador.CompradorRecommendFacade;
import com._360pai.core.facade.comprador.req.MyRecommendListReq;
import com._360pai.core.facade.comprador.req.RecommendAddReq;
import com._360pai.core.facade.comprador.req.RecommendListReq;
import com._360pai.core.facade.comprador.req.RecommendReq;
import com._360pai.core.facade.comprador.resp.RecommendDetailForAdminResp;
import com._360pai.core.facade.comprador.resp.RecommendDetailResp;
import com._360pai.core.model.comprador.TCompradorRecommend;
import com._360pai.core.service.comprador.CompradorRecommendService;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/5 09:14
 */
@Component
@Service(version = "1.0.0")
public class CompradorRecommendProvider implements CompradorRecommendFacade {

    @Autowired
    private CompradorRecommendService compradorRecommendService;

    @Override
    public int recommendAdd(RecommendAddReq req) {
        TCompradorRecommend compradorRecommend = new TCompradorRecommend();
        BeanUtils.copyProperties(req, compradorRecommend);
        compradorRecommend.setRecommendNo(OrderCodeGenerator.INSTANCE.getOrderCode(OrderCodeGenerator.COMPRADOR_RECOMMEND_NO));
        compradorRecommend.setCreatedTime(new Date());
        compradorRecommend.setRecommendStatus(CompradorEnum.RecommendStatus.RECOMMENDING.getValue() + "");
        return compradorRecommendService.recommendAdd(compradorRecommend);
    }

    @Override
    public int recommendMatchSuccess(RecommendReq req) {
        return compradorRecommendService.recommendMatchSuccess(req);
    }


    @Override
    public int recommendFinished(RecommendReq req) {
        return compradorRecommendService.recommendFinished(req);
    }

    @Override
    public PageUtils.Page recommendList(RecommendListReq req) {
        TCompradorRecommendCondition condition = new TCompradorRecommendCondition();

        condition.setCreatedTime(StringUtils.isNotBlank(req.getCreatedTime()) ? DateUtil.parse(req.getCreatedTime(), DateUtil.NORM_DATE_PATTERN) : null);
        condition.setRemark(req.getRemarked());

        condition.setRecommendStatus(req.getRecommendStatus());
        List<RecommendDetailResp> list = compradorRecommendService.recommendList(condition);

        if (StringUtils.isNotBlank(req.getContact())) {
            list.removeIf(model -> !(model.getContactPhone().contains(req.getContact()) || model.getContactName().contains(req.getContact())));
        }

        return PageUtils.fen(req.getPage(), req.getPerPage(), list);
    }

    @Override
    public PageUtils.Page recommendListForAdmin(RecommendListReq req) {
        TCompradorRecommendCondition condition = new TCompradorRecommendCondition();

        condition.setCreatedTime(StringUtils.isNotBlank(req.getCreatedTime()) ? DateUtil.parse(req.getCreatedTime(), DateUtil.NORM_DATE_PATTERN) : null);
        condition.setRemark(req.getRemarked());

        condition.setRecommendStatus(req.getRecommendStatus());
        List<RecommendDetailForAdminResp> list = compradorRecommendService.recommendListForAdmin(condition);

        if (StringUtils.isNotBlank(req.getContact())) {
            list.removeIf(model -> !(model.getContactPhone().contains(req.getContact()) || model.getContactName().contains(req.getContact())));
        }

        return PageUtils.fen(req.getPage(), req.getPerPage(), list);
    }

    @Override
    public PageUtils.Page myRecommendList(MyRecommendListReq req) {
        List<RecommendDetailResp> list = compradorRecommendService.myRecommendList(req);
        return PageUtils.fen(req.getPage(), req.getPerPage(), list);
    }

}
