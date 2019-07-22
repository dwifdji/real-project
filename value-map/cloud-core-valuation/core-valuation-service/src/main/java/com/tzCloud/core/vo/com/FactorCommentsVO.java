package com.tzCloud.core.vo.com;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class FactorCommentsVO implements Serializable {

    private String userName;
    private String comments;
    private String source;
    private String date;

}
