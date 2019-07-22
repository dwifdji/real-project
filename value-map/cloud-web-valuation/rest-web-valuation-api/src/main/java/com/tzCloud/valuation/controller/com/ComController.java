package com.tzCloud.valuation.controller.com;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.facade.com.ComFacade;
import com.tzCloud.facade.com.req.ComReq;
import com.tzCloud.valuation.controller.AbstractController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author wcq
 * @Title: ComController   通用接口
 * @ProjectName zeus-parent
 * @Description:
 * @date 2019/06/13 13:09
 */
@RestController
@RequestMapping("/open/valuationMap/com")
public class ComController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(ComController.class);


    @Reference(version = "1.0.0")
    private ComFacade comFacade;



    /**
     * 功能描述: 获取搜索提示列表
     *
     * @param:
     * @return:
     * @auther: wcq
     * @date: 2019/06/13
     */
    @GetMapping(value = "/getSearchKeyList")
    public ResponseModel getSearchKeyList(ComReq.comReq req) {

        if(StringUtils.isBlank(req.getSearchKey())){

            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        return comFacade.getSearchKeyList(req);
    }



    /**
     * 功能描述: 获取区域因素列表
     *
     * @param:
     * @return:
     * @auther: wcq
     * @date: 2019/06/13
     */
    @GetMapping(value = "/getAroundFactorList")
    public ResponseModel getAroundFactorList(ComReq.comReq req) {

        if(StringUtils.isBlank(req.getLat())||StringUtils.isBlank(req.getLng())){

            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        if(StringUtils.isEmpty(req.getRadius())){
            req.setRadius("1");
        }

        return comFacade.getAroundFactorList(req);
    }



    /**
     * 功能描述: 根据IP获取经纬度信息
     *
     * @param:
     * @return:
     * @auther: wcq
     * @date: 2019/06/13
     */
    @GetMapping(value = "/getCoordinateById")
    public ResponseModel getCoordinateById(ComReq.comReq req) {

        req.setIp(getCurRequestIp());

        //req.setIp("101.227.131.220");



        return comFacade.getCoordinateById(req);
    }



    /**
     * 功能描述: 获取周边详情信息列表
     *
     * @param:
     * @return:
     * @auther: wcq
     * @date: 2019/06/13
     */
    @GetMapping(value = "/getFactorInfoList")
    public ResponseModel getFactorInfoList(ComReq.comReq req) {

        if(StringUtils.isBlank(req.getLat())||StringUtils.isBlank(req.getLng())){

            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        if(StringUtils.isEmpty(req.getRadius())){
            req.setRadius("1");
        }

        return comFacade.getFactorInfoList(req);
    }



    /**
     * 功能描述: 获取周边详情信息
     *
     * @param:
     * @return:
     * @auther: wcq
     * @date: 2019/06/13
     */
    @GetMapping(value = "/getFactorInfoDetail")
    public ResponseModel getFactorInfoDetail(ComReq.comReq req) {


        if(StringUtils.isBlank(req.getPrimaryKey())){

            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        return comFacade.getFactorInfoDetail(req);
    }


}
