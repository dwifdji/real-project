package com.tzCloud.core.provider.com;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageSerializable;

import com.tzCloud.arch.common.HttpUtils;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.arch.common.constant.SystemConstant;
import com.tzCloud.arch.core.redis.RedisCachemanager;
import com.tzCloud.arch.core.sysconfig.properties.GatewayProperties;
import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.arch.common.enums.AuctionEnum;
import com.tzCloud.core.dto.com.ComCoordinateDto;
import com.tzCloud.core.dto.com.ComFactorListDto;
import com.tzCloud.core.dto.com.ComFactorNumDto;
import com.tzCloud.core.dto.com.ComSearchDto;
import com.tzCloud.core.utils.BaiDuMapUtils;
import com.tzCloud.core.vo.com.FactorCommentsVO;
import com.tzCloud.core.vo.com.FactorInfoDetailVO;
import com.tzCloud.facade.com.ComFacade;
import com.tzCloud.facade.com.req.ComReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
@Service(version = "1.0.0")
public class ComProvider implements ComFacade {


    @Autowired
    private GatewayProperties gatewayProperties;


    @Resource
    private RedisCachemanager redisCachemanager;


    @Override
    public ResponseModel getSearchKeyList(ComReq.comReq req) {
        //获取地点输入提示信息
        String resp = BaiDuMapUtils.getSearchKeyList(req.getSearchKey(),gatewayProperties);

        JSONObject json = JSON.parseObject(resp);

        if(!SystemConstant.BAIDU_MAP_STATUS.equals(json.getString("status"))){
            return ResponseModel.fail("百度返回信息失败");
        }

        List<ComSearchDto> searchDtoList = new ArrayList<>();

        JSONArray jsonArray = json.getJSONArray("result");

        if(jsonArray.size()>0){

            for(int i=0;i<jsonArray.size();i++) {
                ComSearchDto dto = new ComSearchDto();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                dto.setName(jsonObject.getString("name"));
                dto.setCity(jsonObject.getString("city"));
                dto.setDistrict(jsonObject.getString("district"));
                dto.setLat(jsonObject.getJSONObject("location").getString("lat"));
                dto.setLng(jsonObject.getJSONObject("location").getString("lng"));
                searchDtoList.add(dto);
            }
        }
        return ResponseModel.succ(searchDtoList);
    }

    @Override
    public ResponseModel getAroundFactorList(ComReq.comReq req) {


        List<ComFactorNumDto> dtoList = new ArrayList<>();

        ExecutorService exec = Executors.newFixedThreadPool(8);//工头
        try {
            ArrayList<Future<ComFactorNumDto>> results = new ArrayList<>();//

            for(AuctionEnum.TAG_CODE tag :AuctionEnum.TAG_CODE.values()){
                //多线程执行查询百度数据
                results.add(exec.submit(new TaskCallable(tag,req)));

            }
            //获取返回的参数
            for(Future<ComFactorNumDto> fut:results){
                dtoList.add(fut.get());
            }
            for(AuctionEnum.TAG_CODE_TWO tag :AuctionEnum.TAG_CODE_TWO.values()){
                //添加不用统计数量的数据
                ComFactorNumDto dto = new  ComFactorNumDto();
                dto.setFactorName(tag.getVal());
                dto.setNum("0");
                dtoList.add(dto);
            }
        }catch (Exception e){

            exec.shutdown();
        }
        return ResponseModel.succ(dtoList);
    }

    public class TaskCallable implements Callable<ComFactorNumDto> {
        private AuctionEnum.TAG_CODE tag;

        private ComReq.comReq req;


        public TaskCallable(AuctionEnum.TAG_CODE tag ,ComReq.comReq req){
            this.tag = tag;
            this.req = req;

        }
        @Override
        public ComFactorNumDto call() {

            return getComFactorNumDto(tag,req);
        }
    }

    private ComFactorNumDto getComFactorNumDto(AuctionEnum.TAG_CODE tag, ComReq.comReq req) {

        ComFactorNumDto dto = new  ComFactorNumDto();

        String resp = BaiDuMapUtils.getFactorInfoList(req.getCity(),tag.getVal(),req.getLat(),req.getLng(),1,0,"1",gatewayProperties,req.getRadius());

        JSONObject jsonObject = JSON.parseObject(resp);

        dto.setFactorName(tag.getVal());
        dto.setNum("0");

        if(SystemConstant.BAIDU_MAP_STATUS.equals(jsonObject.getString("status"))){
            dto.setNum(jsonObject.getString("total"));
        }

        return dto;
    }

    @Override
    public ResponseModel getCoordinateById(ComReq.comReq req) {
        //获取ip信息
        String resp = BaiDuMapUtils.getLocationByIp(req.getIp(),gatewayProperties);

        JSONObject json = JSON.parseObject(resp);

        if(!SystemConstant.BAIDU_MAP_STATUS.equals(json.getString("status"))){
            //定位失败 返回 上海市默认值
            ComCoordinateDto dto = new ComCoordinateDto();
            dto.setLng("121.48789949");
            dto.setLat("31.24916171");
            dto.setCity("上海市");
            return ResponseModel.succ(dto);
        }

        JSONObject contentJson =  json.getJSONObject("content");

        JSONObject pointJson =  contentJson.getJSONObject("point");

        JSONObject addressJson =  contentJson.getJSONObject("address_detail");

        ComCoordinateDto dto = new ComCoordinateDto();
        dto.setLng(pointJson.getString("x"));
        dto.setLat(pointJson.getString("y"));
        dto.setCity(addressJson.getString("city"));
        dto.setDistrict(addressJson.getString("district"));
        return ResponseModel.succ(dto);
    }

    @Override
    public ResponseModel getFactorInfoList(ComReq.comReq req) {

        PageSerializable respInfo = new PageSerializable();


        List<ComFactorListDto>  comFactorListDtoList = new ArrayList<>();


        String resp = BaiDuMapUtils.getFactorInfoList(req.getCity(),req.getFactorName(),req.getLat(),req.getLng(),req.getPerPage(),req.getPage()-1,"2",gatewayProperties,req.getRadius());

        JSONObject jsonObject = JSON.parseObject(resp);
        respInfo.setTotal(0);

        if(SystemConstant.BAIDU_MAP_STATUS.equals(jsonObject.getString("status"))){

            respInfo.setTotal(jsonObject.getLongValue("total"));

            JSONArray jsonArray = jsonObject.getJSONArray("results");

            if(jsonArray.size()>0){

                for(int i=0;i<jsonArray.size();i++) {
                    ComFactorListDto dto = new ComFactorListDto();
                    JSONObject json = jsonArray.getJSONObject(i);
                    dto.setName(json.getString("name"));
                    dto.setLat(json.getJSONObject("location").getString("lat"));
                    dto.setLng(json.getJSONObject("location").getString("lng"));

                    dto.setPhone(StringUtils.isEmpty(json.getString("telephone"))?"暂无":json.getString("telephone"));
                    dto.setAddress(StringUtils.isEmpty(json.getString("address"))?"暂无":json.getString("address"));
                    dto.setPrimaryKey(json.getString("uid"));
                    if("1".equals(json.getString("detail"))){
                        dto.setDistance(json.getJSONObject("detail_info").getString("distance"));
                    }

                    comFactorListDtoList.add(dto);
                }
            }


        }
        respInfo.setList(comFactorListDtoList);

        return ResponseModel.succ(respInfo);
    }



    @Override
    public ResponseModel getFactorInfoDetail(ComReq.comReq req) {


        try {

            //先获取缓存信息
            String cacheInfo = (String) redisCachemanager.hGet(SystemConstant.BAIDU_MAP_FACTOR_INFO_DETAIL, req.getPrimaryKey());

            if(StringUtils.isNotEmpty(cacheInfo)){

                FactorInfoDetailVO detailVO = JSON.parseObject(cacheInfo,FactorInfoDetailVO.class);

                return ResponseModel.succ(detailVO);
            }


            FactorInfoDetailVO detailVO = new FactorInfoDetailVO();

            String  detail = BaiDuMapUtils.getLocationDetail(req.getPrimaryKey(),gatewayProperties);

            JSONObject detailJson = JSONObject.parseObject(detail).getJSONObject("result");


            JSONObject detailInfo1 = detailJson.getJSONObject("detail_info");

            detailVO.setName(detailJson.getString("name"));
            detailVO.setAddress(detailJson.getString("address"));
            detailVO.setPhone(detailJson.getString("telephone"));
            detailVO.setScore(detailInfo1.getString("overall_rating"));
            detailVO.setPictureUrl(getPictureInfo(req));
            detailVO.setPerPrice(detailInfo1.getString("price"));
            detailVO.setDistance(detailInfo1.getString("distance"));
            detailVO.setCommentsList(getCommentsListInfo(req.getPrimaryKey()));

            //缓存成功的数据
            redisCachemanager.hSet(SystemConstant.BAIDU_MAP_FACTOR_INFO_DETAIL, req.getPrimaryKey(), JSON.toJSONString(detailVO));


            return ResponseModel.succ(detailVO);
        }catch (Exception e){


        }

        return ResponseModel.fail(ApiCallResult.DATA_EMPTY.getDesc());
    }

    private String getPictureInfo(ComReq.comReq req) {

        String imageUrl = null;
        try {
            //
            String  detailUrl ="https://map.baidu.com/?uid="+req.getPrimaryKey()+"&primaryUid=1257871714839166975&ugc_type=3&ugc_ver=1&qt=detailConInfo&device_ratio=2&compat=1&t=1561095786073&auth=XBYeNOU3wdWL2PgdSEfHJCBAAKOw3fVyuxHLxVTHNNztDpnSCE%40%40By1uVt1GgvPUDZYOYIZuRt3jD5CEB2dd9dv7uPWv3GuLt8BnlQcWlADEGJM5S7YZzcEWe1GD8zvSugeVCXBlCGBexZFTHrwzBvprGnrFHQUHcaNJQQEswVVH32s99XvI";

            //获取详情信息
            String  detailResp =   HttpUtils.sendGet(detailUrl);


            JSONObject jsonObject = JSONObject.parseObject(detailResp);

            JSONObject content1 = jsonObject.getJSONObject("content");

            JSONObject detailInfo = content1.getJSONObject("ext").getJSONObject("detail_info");

            imageUrl = detailInfo.getString("image");

            //百度地图的图片
            if(StringUtils.isNotEmpty(imageUrl)&&imageUrl.contains("hiphotos.baidu.com")){

                imageUrl = BaiDuMapUtils.getQiNiuUrl(imageUrl,gatewayProperties);
            }


        }catch (Exception e){


        }


        return imageUrl;
    }

    private List<FactorCommentsVO> getCommentsListInfo(String primaryKey) {
        List<FactorCommentsVO> commentsVOS = new ArrayList<>();

        try {
            String commentUrl = "https://ugcapi.baidu.com/richindex/2/comment?uid="+primaryKey+"&pageIndex=1&pageCount=10&pic_videos=1&tab=1&from=map_zhongtai";

            //获取评论信息
            String  commentResp =   HttpUtils.sendGet(commentUrl);

            JSONObject jsonResp =JSONObject.parseObject(commentResp);

            if(!"0".equals(jsonResp.getString("errno"))){

                return commentsVOS;
            }


            JSONArray jsonArray = jsonResp.getJSONObject("data").getJSONArray("comment_list");

            for(int i=0;i<jsonArray.size();i++) {

                FactorCommentsVO commentsVO = new FactorCommentsVO();

                // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                commentsVO.setComments(jsonObject.getString("content"));
                commentsVO.setUserName(jsonObject.getString("user_name"));
                commentsVO.setDate(jsonObject.getString("date"));
                commentsVO.setSource(jsonObject.getString("cn_name"));
                commentsVOS.add(commentsVO);
            }
        }catch (Exception e){

        }




        return commentsVOS;
    }
}
