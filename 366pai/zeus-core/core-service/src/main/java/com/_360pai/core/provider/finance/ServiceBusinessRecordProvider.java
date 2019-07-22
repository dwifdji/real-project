package com._360pai.core.provider.finance;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.common.constants.ServiceOrderEnum;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.finance.ServiceBusinessRecordFacade;
import com._360pai.core.facade.finance.req.BusinessRecordReq;
import com._360pai.core.facade.finance.resp.PurchaseHistoryDTO;
import com._360pai.core.model.order.TServiceBusinessRecord;
import com._360pai.core.service.finance.ServiceBusinessRecordService;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xiaolei
 * @create 2018-10-03 07:23
 */
@Component
@Service(version = "1.0.0")
public class ServiceBusinessRecordProvider implements ServiceBusinessRecordFacade {

    @Autowired
    private ServiceBusinessRecordService serviceBusinessRecordService;

    @Override
    public PageInfoResp<PurchaseHistoryDTO> getPurchaseHistoryByBuyerPartyId(BusinessRecordReq.GetBusinessRecord req) {
        if (req.getPartyId() == null) {
            throw new BusinessException(ApiCallResult.EMPTY, "buyId 不能为空");
        }
        PageInfo<TServiceBusinessRecord> pageInfo = serviceBusinessRecordService.getBusinessRecordByBuyerPartyId(
                req.getPartyId(), req.getPage(), req.getPerPage());
        return convertRecordToPageInfoResp(pageInfo);
    }

    private List<PurchaseHistoryDTO> convertRecordToDTO(List<TServiceBusinessRecord> record) {
        List<PurchaseHistoryDTO> dtoList = new LinkedList<>();
        for (TServiceBusinessRecord tmp : record) {
            PurchaseHistoryDTO dto = new PurchaseHistoryDTO();
            BeanUtils.copyProperties(tmp, dto);
            dto.setOrderTypeDesc(ServiceOrderEnum.OrderType.getKeyByValue(tmp.getOrderType()));
            dto.setTuneReportUrl(convertUrlArray(tmp.getAdditional()));
            dtoList.add(dto);
        }
        return dtoList;
    }

    private String[] convertUrlArray(String additional) {

        try {

            JSONObject jsonObject = JSON.parseObject(additional);

            JSONArray images =(JSONArray) jsonObject.get("images");

            String[] str = new String[images.size()];

            for (int i = 0; i < images.size(); i++) {
                Object o = images.get(i);
                str[i] = (String) o;
            }

            return str;

        } catch (Exception e) {

            return new String[0];
        }
    }

    private PageInfoResp<PurchaseHistoryDTO> convertRecordToPageInfoResp(PageInfo<TServiceBusinessRecord> pageInfo) {
        PageInfoResp pageInfoResp = new PageInfoResp<>();
        pageInfoResp.setTotal(pageInfo.getTotal());
        pageInfoResp.setList(convertRecordToDTO(pageInfo.getList()));
        pageInfoResp.setHasNextPage(pageInfo.isHasNextPage());
        return pageInfoResp;
    }
}
