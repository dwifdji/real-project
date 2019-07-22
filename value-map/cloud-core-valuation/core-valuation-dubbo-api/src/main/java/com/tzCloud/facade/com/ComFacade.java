package com.tzCloud.facade.com;


import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.facade.com.req.ComReq;

public interface ComFacade {

    ResponseModel getSearchKeyList(ComReq.comReq req);

    ResponseModel getAroundFactorList(ComReq.comReq req);

    ResponseModel getCoordinateById(ComReq.comReq req);

    ResponseModel getFactorInfoList(ComReq.comReq req);

    ResponseModel getFactorInfoDetail(ComReq.comReq req);


}
