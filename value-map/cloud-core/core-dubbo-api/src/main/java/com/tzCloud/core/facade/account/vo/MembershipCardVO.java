package com.tzCloud.core.facade.account.vo;

import com.tzCloud.arch.common.utils.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2019-06-26 10:52
 */
@Data
public class MembershipCardVO implements Serializable {
    private static final long serialVersionUID = 6835934354551901195L;

    private String type;
    private Date startTime;
    private Date endTime;
}
