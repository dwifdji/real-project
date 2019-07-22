package com.tzCloud.core.hanLP;

import com.tzCloud.core.utils.HtmlRegexUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Container
{

    public enum name
    {
        SB_COURT("法院名称"),
        SB_TYPE("判决书类型"),
        SB_AH("案号"),
        DSRXX("当事人信息"),
        SSJL("审理经过"),
        YS_YGQQ("一审原告请求"),
        YS_BGBC("一审被告辩称"),
        YS_RDSS("一审法院认定事实"),
        YS_RW("一审法院认为"),
        YS_PJJG("一审法院判决结果"),
        SSRBC("上诉人请求"),
        BSSRBC("被上诉人辩称"),
        BYRDSS("本院认定事实"),
        RDSS("法院认定事实"),
        CPYZ("本院认为"),
        PJJG("判决结果"),
        HYTXX("合议庭信息"),
        PJSJ("判决日期"),
        OTHER("其他");

        String desc;
        name(String desc) {
            this.desc = desc;
        }
    }

    private Map<String, List<String>> content = new LinkedHashMap<>();

    public void put(String sentence)
    {
        try
        {
            put(null, sentence);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void put(String name, String sentence)
    {
        try
        {
            sentence = HtmlRegexUtils.removeAllHtml(sentence);
            if (StringUtils.isNotBlank(name))
            {
                if (content.containsKey(name))
                {
                    List<String> strings = content.get(name);
                    strings.add(sentence);
                }
                else
                {
                    List<String> strings = new LinkedList<>();
                    strings.add(sentence);
                    content.put(name, strings);
                }
            }
            else
            {
                Field tail = content.getClass().getDeclaredField("tail");
                tail.setAccessible(true);
                Map.Entry<String, List<String>> entry = (Map.Entry<String, List<String>>) tail.get(content);
                if (entry != null)
                {
                    entry.getValue().add(sentence);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public int size()
    {
        return content.size();
    }

    public Map<String, List<String>> content()
    {
        return content;
    }

    @Override
    public String toString() {
        return "Container{" +
                "content=" + content +
                '}';
    }
}
    