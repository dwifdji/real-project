package com.tzCloud.arch.core.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/2/28 18:27
 */
public class GetParameterRequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> params = new HashMap<>();


    @SuppressWarnings("unchecked")
    public GetParameterRequestWrapper(HttpServletRequest request) {
        super(request);
        this.params.putAll(request.getParameterMap());
        getParameterMap();
    }


    @Override
    public String getParameter(String name) {
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> values = super.getParameterMap();
        if (values == null) {
            return null;
        }
        Map<String, String[]> result = new HashMap<>();
        for (String key : values.keySet()) {
            String   encodedKey    = XSSAndSQLUtil.cleanXSS(key);
            int      count         = values.get(key).length;
            String[] encodedValues = new String[count];
            for (int i = 0; i < count; i++) {
                encodedValues[i] = XSSAndSQLUtil.cleanXSS(values.get(key)[i]);
            }
            result.put(encodedKey, encodedValues);
        }
        return result;
    }

    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }

}