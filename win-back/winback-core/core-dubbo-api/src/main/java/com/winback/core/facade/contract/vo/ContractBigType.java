package com.winback.core.facade.contract.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author xdrodger
 * @Title: ContractBigType
 * @ProjectName winback
 * @Description:
 * @date 2019/2/12 16:09
 */
@Data
public class ContractBigType implements Serializable {
    private String id;
    private String name;
    private List<ContractType> contractTypes = Collections.EMPTY_LIST;
}
