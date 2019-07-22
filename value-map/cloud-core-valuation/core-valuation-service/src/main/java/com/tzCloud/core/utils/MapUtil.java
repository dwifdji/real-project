package com.tzCloud.core.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MapUtil {

    public static Map<String, String> getSearchParams(String latSt, String lngSt, String redius) {
        Map<String, String> params = new HashMap<>();

        double lat = Double.parseDouble(latSt) ; //纬度
        double lng = Double.parseDouble(lngSt);//经度
        double dis = Double.parseDouble(redius);//半径

        //先计算查询点的经纬度范围
        double r = 6371;//地球半径千米
        double dlng =  2 * Math.asin(Math.sin(dis / (2 * r)) / Math.cos(lat * Math.PI / 180));
        dlng = dlng * 180 / Math.PI;//角度转为弧度
        double dlat = dis / r;
        dlat = dlat * 180 / Math.PI;
        double minlat = getSixFormatNum(lat - dlat) ;
        double maxlat = getSixFormatNum(lat + dlat);
        double minlng = getSixFormatNum(lng - dlng);
        double maxlng = getSixFormatNum(lng + dlng);

        params.put("minlat", String.valueOf(minlat));
        params.put("maxlat", String.valueOf(maxlat));
        params.put("minlng", String.valueOf(minlng));
        params.put("maxlng", String.valueOf(maxlng));




        return params;
    }

    private static double getSixFormatNum(double d) {
        BigDecimal b = new BigDecimal(d);
        d = b.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        return d;
    }
}
