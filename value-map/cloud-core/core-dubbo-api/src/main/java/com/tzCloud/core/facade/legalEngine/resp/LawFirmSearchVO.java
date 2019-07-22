package com.tzCloud.core.facade.legalEngine.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author xiaolei
 * @create 2019-05-16 14:30
 */
@Data
public class LawFirmSearchVO implements Serializable {
    private static final long serialVersionUID = 8656164773744387970L;

    List<LawFirmVO> lawFirmVOList;

    Set<String> provinceSet;

    List<String> foundTimeList;

    List<String> lawyerNumList;

    private long total;

    private boolean hasNextPage;
}
