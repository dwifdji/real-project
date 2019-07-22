package com.tzCloud.core.facade.legalEngine.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2019-05-10 15:37
 */
@Data
public class DocVO implements Serializable {
    private static final long serialVersionUID = 6080242467981085647L;
    private String docId;
    private String brief;
    public DocVO() {
    }
    public DocVO(String docId, String brief) {
        this.docId = docId;
        this.brief = brief;
    }
}
