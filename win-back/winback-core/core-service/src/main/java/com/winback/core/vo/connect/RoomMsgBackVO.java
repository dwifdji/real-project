package com.winback.core.vo.connect;

import java.io.Serializable;

public class RoomMsgBackVO implements Serializable {

    private String roomId;

    private Integer unreadNum;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Integer getUnreadNum() {
        return unreadNum;
    }

    public void setUnreadNum(Integer unreadNum) {
        this.unreadNum = unreadNum;
    }
}
