package com.winback.core.facade.assistant;

import com.winback.arch.common.AppReq;
import com.winback.arch.common.Device;
import com.winback.arch.common.ListResp;
import com.winback.arch.common.ResponseModel;
import com.winback.core.facade.assistant.req.AppAssistantReq;
import com.winback.core.facade.assistant.req.AppletAssistantReq;
import com.winback.core.facade.assistant.resp.AppAssistantResp;
import com.winback.core.facade.assistant.vo.Area;
import com.winback.core.facade.assistant.vo.City;
import com.winback.core.facade.assistant.vo.HelpItem;
import com.winback.core.facade.assistant.vo.Province;

/**
 * @author xdrodger
 * @Title: AssistantFacade
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 16:19
 */
public interface AssistantFacade {
    ListResp<HelpItem> getHelpItemList(AppReq req);

    HelpItem getHelpItem(AppAssistantReq.GetHelpItemReq req);

    ResponseModel getQiNiuToken(String fileType);

    ResponseModel getAllCities(String type);

    ListResp<Province> getAllProvinces();

    ListResp<City> getCitiesByProvinceCode(String provinceCode);

    ListResp<Area> getAreasByCityCode(String cityCode);


    Integer buriedPointInsert(AppAssistantReq.BuriedPointReq req);

    Integer buriedPointInsert(AppletAssistantReq.BuriedPointReq req);

    Integer buriedPointUpdate(AppAssistantReq.BuriedPointUpdateReq req);


    void importContractFileToDb(String filePath);

    void resetContractImage(String filePath);

    void resetContractName(String filePath);

    void simpleSaveDevice(Integer accountId, Device device);

    AppAssistantResp.CheckUpdateResp checkUpdate(AppAssistantReq.CheckUpdateReq req);
}
