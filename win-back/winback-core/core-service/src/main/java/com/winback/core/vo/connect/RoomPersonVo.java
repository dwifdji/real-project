package com.winback.core.vo.connect;


import lombok.Data;

import java.io.Serializable;

@Data
public class RoomPersonVo implements Serializable {

    private String id;

    private String name;

    private String type;

    private String lawyerName;

    private Integer unreadNum;

    private String primaryId;

}
