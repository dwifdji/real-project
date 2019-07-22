package com._360pai.core.common;

import com._360pai.arch.common.utils.EnumUtils;
import com._360pai.core.common.constants.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 描述 系统中数据字典
 *
 * @author : whisky_vip
 * @date : 2018/9/14 13:02
 */
public class SystemDict {
    private SystemDict() {
        initCompradorDict();
        initWithfudigDict();
        initAssetDict();
        initAuctionActivityDict();
        initAccountDict();
        initDisposalDict();
    }

    public static SystemDict                       instance = new SystemDict();
    private       Map<Object, Map<Object, Object>> result   = new LinkedHashMap<>();

    public Map<Object, Map<Object, Object>> getSystemDict() {
        return result;
    }

    /**
     * 资产大买办 数据字典
     *
     * @see CompradorEnum
     */
    private void initCompradorDict() {
        result.put("compradorRequirementStatus", convert(EnumUtils.EnumToMap(CompradorEnum.RequirementStatus.class)));
        result.put("compradorRecommendStatus", convert(EnumUtils.EnumToMap(CompradorEnum.RecommendStatus.class)));
        result.put("compradorRequirementTransactionMode", convert(EnumUtils.EnumToMap(CompradorEnum.RequirementTransactionMode.class)));
        result.put("compradorRequirementBuildingType", convert(EnumUtils.EnumToMap(CompradorEnum.RequirementBuildingType.class)));
        result.put("compradorRecommenderStatus", convert(EnumUtils.EnumToMap(CompradorEnum.RecommenderStatus.class)));
    }

    /**
     * 资产大买办 数据字典
     *
     * @see WithfudigEnum
     */
    private void initWithfudigDict() {
        result.put("withfudigRequirementStatus", convert(EnumUtils.EnumToMap(WithfudigEnum.RequirementStatus.class)));
        result.put("withfudigInvestStatus", convert(EnumUtils.EnumToMap(WithfudigEnum.InvestStatus.class)));
        result.put("withfudigRequirementAssetType", convert(EnumUtils.EnumToMap(WithfudigEnum.AssetType.class)));
    }

    /**
     *
     * 标的 数据字典
     *
     * @see AssetEnum
     */
    private void initAssetDict() {
        Map<Object, Object> assetStatusMap = EnumUtils.EnumToMap(AssetEnum.Status.class);
        // 新增租赁权状态的数据字段
        assetStatusMap.put(AssetEnum.LeaseStatus.WAITING_FIRST_REVIEW.getKey(),  AssetEnum.LeaseStatus.WAITING_FIRST_REVIEW.getRecordValue());
        assetStatusMap.put(AssetEnum.LeaseStatus.WAITING_SECOND_REVIEW.getKey(),  AssetEnum.LeaseStatus.WAITING_SECOND_REVIEW.getShowValue());
        assetStatusMap.put(AssetEnum.LeaseStatus.FIRST_REVIEW_REJECTION.getKey(),  AssetEnum.LeaseStatus.FIRST_REVIEW_REJECTION.getRecordValue());
        assetStatusMap.put(AssetEnum.LeaseStatus.SECOND_REVIEW_REJECTION.getKey(),  AssetEnum.LeaseStatus.SECOND_REVIEW_REJECTION.getRecordValue());
        result.put("assetStatus", assetStatusMap);
        result.put("assetType", EnumUtils.EnumToMap(AssetEnum.AssetType.class));
        result.put("assetExpectedMode", EnumUtils.EnumToMap(AssetEnum.ExpectedMode.class));

    }

    /**
     * 拍卖活动 数据字典
     * @see AssetEnum
     */
    private void initAuctionActivityDict() {
        Map<Object, Object> statusMap = new LinkedHashMap<>();
        statusMap.put(ActivityEnum.Status.PLATFORM_REVIEWING.getKey(), ActivityEnum.Status.PLATFORM_REVIEWING.getValue());
        statusMap.put(ActivityEnum.Status.PLATFORM_REJECTED.getKey(), ActivityEnum.Status.PLATFORM_REJECTED.getValue());
        statusMap.put(ActivityEnum.Status.PLATFORM_APPROVED.getKey(), ActivityEnum.Status.PLATFORM_APPROVED.getValue());
        statusMap.put(ActivityEnum.Status.ONLINE.getKey(), ActivityEnum.Status.ONLINE.getValue());
        statusMap.put(ActivityEnum.Status.CANCELLED.getKey(), ActivityEnum.Status.CANCELLED.getValue());
        statusMap.put(ActivityEnum.Status.UNCONFIRMED.getKey(), ActivityEnum.Status.UNCONFIRMED.getValue());
        statusMap.put(ActivityEnum.Status.SUCCESS.getKey(), ActivityEnum.Status.SUCCESS.getValue());
        statusMap.put(ActivityEnum.Status.FAILED.getKey(), ActivityEnum.Status.FAILED.getValue());
        // 新增租赁权的拍卖活动状态
        statusMap.put(AssetEnum.LeaseStatus.WAITING_RELEASE.getKey(), AssetEnum.LeaseStatus.WAITING_RELEASE.getParentValue());
        statusMap.put(AssetEnum.LeaseStatus.REGISTRATION_PERIOD.getKey(),AssetEnum.LeaseStatus.REGISTRATION_PERIOD.getParentValue());
        statusMap.put(AssetEnum.LeaseStatus.QUALIFICATION_REVIEW.getKey(),AssetEnum.LeaseStatus.QUALIFICATION_REVIEW.getParentValue());

        result.put("auctionActivityStatus", statusMap);
        result.put("auctionOrderStatus", EnumUtils.EnumToMap(OrderEnum.Status.class));
        result.put("auctionActivityMode", EnumUtils.EnumToMap(ActivityEnum.ActivityMode.class));
        result.put("auctionActivityUnionStatus", EnumUtils.EnumToMap(ActivityEnum.UnionStatus.class));

    }

    /**
     * 账户 数据字典
     *
     * @see AccountEnum
     */
    private void initAccountDict() {
        result.put("registerSource", EnumUtils.EnumToMap(AccountEnum.RegisterSource.class));
        result.put("applyStatus", EnumUtils.EnumToMap(AccountEnum.ApplyStatus.class));
        result.put("disposeType", EnumUtils.EnumToMap(DisposalEnum.DisposeType.class));
        result.put("fundProviderCompanyType", EnumUtils.EnumToMap(WithfudigEnum.CompanyType.class));

        result.put("disposeProviderService", EnumUtils.EnumToMap(DisposalEnum.RequirementType.class));
        Map<Object, Object> disposeProviderServiceMap = new LinkedHashMap<>();
        disposeProviderServiceMap.put(DisposalEnum.RequirementType.JINDIAO.getKey(), DisposalEnum.RequirementType.JINDIAO.getValue());
        disposeProviderServiceMap.put(DisposalEnum.RequirementType.SIFACHUZHI.getKey(), DisposalEnum.RequirementType.SIFACHUZHI.getValue());
        disposeProviderServiceMap.put(DisposalEnum.RequirementType.ZHIXINGCHUZHI.getKey(), DisposalEnum.RequirementType.ZHIXINGCHUZHI.getValue());
        disposeProviderServiceMap.put(DisposalEnum.RequirementType.QINGFANG.getKey(), DisposalEnum.RequirementType.QINGFANG.getValue());
        disposeProviderServiceMap.put(DisposalEnum.RequirementType.CUISHOU.getKey(), DisposalEnum.RequirementType.CUISHOU.getValue());
        disposeProviderServiceMap.put(DisposalEnum.RequirementType.CHAZHAOCANCHANXIANSUO.getKey(), DisposalEnum.RequirementType.CHAZHAOCANCHANXIANSUO.getValue());
        result.put("disposeProviderServiceForLawFirm", disposeProviderServiceMap);

        Map<Object, Object> partyCompanyTypesMap = new LinkedHashMap<>();
        partyCompanyTypesMap.put(PartyEnum.Category.BANK_COMPANY.getKey(), PartyEnum.Category.BANK_COMPANY.getValue());
        partyCompanyTypesMap.put(PartyEnum.Category.AMC_COMPANY.getKey(), PartyEnum.Category.AMC_COMPANY.getValue());
        partyCompanyTypesMap.put(PartyEnum.Category.FOLK_ASSET_COMPANY.getKey(), PartyEnum.Category.FOLK_ASSET_COMPANY.getValue());
        partyCompanyTypesMap.put(PartyEnum.Category.NORMAL_COMPANY.getKey(), PartyEnum.Category.NORMAL_COMPANY.getValue());
        partyCompanyTypesMap.put(PartyEnum.Category.AUCTION_COMPANY.getKey(), PartyEnum.Category.AUCTION_COMPANY.getValue());
        partyCompanyTypesMap.put(PartyEnum.Category.DISPOSE_PROVIDER.getKey(), PartyEnum.Category.DISPOSE_PROVIDER.getValue());
        partyCompanyTypesMap.put(PartyEnum.Category.EXCHANGE.getKey(), PartyEnum.Category.EXCHANGE.getValue());

        partyCompanyTypesMap.put(PartyEnum.Category.LEASE_COMPANY.getKey(), PartyEnum.Category.LEASE_COMPANY.getValue());

        result.put("partyCompanyType", partyCompanyTypesMap);

        result.put("acctType", EnumUtils.EnumToMap(AccountEnum.AcctType.class));
        result.put("acctDetailStatus", EnumUtils.EnumToMap(AccountEnum.AcctDetailStatus.class));
        result.put("frontAcctDetailStatus", EnumUtils.EnumToMap(AccountEnum.FrontAcctDetailStatus.class));

        result.put("firstVerifyWithdrawStatus",  EnumUtils.EnumToMap(AccountEnum.FirstVerifyWithdrawStatus.class));
        result.put("invoiceVerifyWithdrawStatus",  EnumUtils.EnumToMap(AccountEnum.InvoiceVerifyWithdrawStatus.class));
        result.put("acctOperateType", EnumUtils.EnumToMap(AccountEnum.AcctOperateType.class));
    }

    /**
     * 处置服务词典
     * @see AccountEnum
     */
    private void initDisposalDict() {
        result.put("disposalBiddingStatus", EnumUtils.EnumToMap(DisposalEnum.BiddingStatus.class));
        result.put("disposalRequirementStatus", convert(EnumUtils.EnumToMap(DisposalEnum.RequirementStatus.class)));
    }

    private static Map<Object, Object> convert(Map map) {
        HashMap<Object, Object> hashMap2 = new HashMap<>();
        int                     size     = map.size();
        Object[]                key      = new String[size];
        Object[]                val      = new String[size];

        int i = 0;
        //keySet 取出hashMap中的所有key
        for (Object a : map.keySet()) {
            val[i] = a;
            key[i] = map.get(a);
            i++;
        }
        for (int a = 0; a < size; a++) {
            hashMap2.put(key[a], val[a]);
        }
        return hashMap2;
    }

    public static final String AUCTIONSTATUS = "[{\"id\": \"ahead\",\"name\": \"" + ActivityEnum.OnlineStatus.UPCOMING.getValue() + "\"}," +
            "{\"id\": \"beginAuction\",\"name\": \"" + ActivityEnum.OnlineStatus.SALE.getValue() + "\"}," +
            "{\"id\": \"success\",\"name\": \"成交\"}," +
            "{\"id\": \"end\",\"name\": \"结束\"}]";
}
