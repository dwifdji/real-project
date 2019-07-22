package com.winback.core.commons;

import com.winback.arch.common.utils.EnumUtils;
import com.winback.core.commons.constants.AccountEnum;
import com.winback.core.commons.constants.ContractEnum;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 描述 系统中数据字典
 */
public class SystemDict {
    private SystemDict() {
        initAccountDict();
        initContractDict();
        initCaseDict();
    }

    public static SystemDict                       instance = new SystemDict();
    private       Map<Object, Map<Object, Object>> result   = new LinkedHashMap<>();

    public Map<Object, Map<Object, Object>> getSystemDict() {

        return result;
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

    /**
     * 账户 数据字典
     *
     */
    private void initAccountDict() {
        result.put("registerSource", EnumUtils.EnumToMap(AccountEnum.RegisterSource.class));

        result.put("lawFirmType", EnumUtils.EnumToMap(AccountEnum.LawFirmType.class));
        result.put("lawFirmTeamSize", EnumUtils.EnumToMap(AccountEnum.LawFirmTeamSize.class));

        Map<Object, Object> applyStatusMap = new LinkedHashMap<>();
        applyStatusMap.put(AccountEnum.ApplyStatus.PENDING.getKey(), AccountEnum.ApplyStatus.PENDING.getValue());
        applyStatusMap.put(AccountEnum.ApplyStatus.APPROVED.getKey(), AccountEnum.ApplyStatus.APPROVED.getValue());
        applyStatusMap.put(AccountEnum.ApplyStatus.REJECT.getKey(), AccountEnum.ApplyStatus.REJECT.getValue());
        result.put("applyStatus", applyStatusMap);
    }

    /**
     * 合同 数据字典
     *
     */
    private void initContractDict() {
        result.put("orderSource", EnumUtils.EnumToMap(ContractEnum.OrderSource.class));
        result.put("payType", EnumUtils.EnumToMap(ContractEnum.PayType.class));
        Map<Object, Object> payStatusMap = new LinkedHashMap<>();
        payStatusMap.put(ContractEnum.PayStatus.INIT.getKey() + "", ContractEnum.PayStatus.INIT.getValue());
        payStatusMap.put(ContractEnum.PayStatus.SUCCESS.getKey() + "", ContractEnum.PayStatus.SUCCESS.getValue());
        payStatusMap.put(ContractEnum.PayStatus.FAIL.getKey() + "", ContractEnum.PayStatus.FAIL.getValue());
        result.put("payStatus", payStatusMap);
    }

    /**
     * 案件 数据字典
     *
     */
    private void initCaseDict() {
    }
}
