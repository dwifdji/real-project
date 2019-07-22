package com.tzCloud.core.facade.caseMatching;

/**
 * @author xiaolei
 * @create 2019-03-18 10:23
 */
public interface HanLPFacade {
    String getDictionary(String key);
    void reload();
    void loadConfig();
}
