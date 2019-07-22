package com.tzCloud.core.provider.caseMatching;

import com.alibaba.dubbo.config.annotation.Service;
import com.hankcs.hanlp.dictionary.CoreDictionary;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.tzCloud.core.facade.caseMatching.HanLPFacade;
import com.tzCloud.core.hanLP.Config;
import org.springframework.stereotype.Component;

/**
 * @author xiaolei
 * @create 2019-03-18 10:23
 */
@Component
@Service(version = "1.0.0")
public class HanLPProvider implements HanLPFacade {


    @Override
    public String getDictionary(String key)
    {
        CoreDictionary.Attribute attribute = CustomDictionary.get(key);
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public void reload() {
        CustomDictionary.reload();
        System.out.println("CustomDictionary test" + CustomDictionary.get("一九九〇年一月九日"));
        System.out.println("CustomDictionary test" + CustomDictionary.get("上诉人"));
        System.out.println("CustomDictionary test" + CustomDictionary.get("承担"));
    }

    @Override
    public void loadConfig() {
        new Config();
    }
}
