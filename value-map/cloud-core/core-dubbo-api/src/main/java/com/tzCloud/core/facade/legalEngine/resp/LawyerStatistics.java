package com.tzCloud.core.facade.legalEngine.resp;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiaolei
 * @create 2019-05-15 16:55
 */
public class LawyerStatistics implements Serializable{
    private static final long serialVersionUID = 6957485031049558762L;
    public Long id;
    public Integer caseCount;
    public List<String> docIds;
}
