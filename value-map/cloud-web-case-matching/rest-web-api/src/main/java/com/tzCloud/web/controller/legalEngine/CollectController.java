package com.tzCloud.web.controller.legalEngine;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.core.facade.legalEngine.CollectFacade;
import com.tzCloud.core.facade.legalEngine.req.CollectReq;
import com.tzCloud.web.controller.AbstractController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaolei
 * @create 2019-04-24 15:10
 */
@RestController
public class CollectController extends AbstractController {

    @Reference(version = "1.0.0")
    private CollectFacade collectFacade;

    @PostMapping("/confined/legalEngine/collect")
    public ResponseModel collect(@RequestBody CollectReq.Collect req)
    {
        Integer curLoginId = loadCurLoginId();
        req.setAccountId(curLoginId);
        Integer id = collectFacade.collect(req);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return ResponseModel.succ(resultMap);
    }

    @PostMapping("/confined/legalEngine/cancelCollect")
    public ResponseModel cancelCollect(@RequestBody CollectReq.Collect req)
    {
        req.setAccountId(loadCurLoginId());
        collectFacade.cancelCollect(req);
        return ResponseModel.succ();
    }

    @PostMapping("/confined/legalEngine/getCollectLawyer")
    public ResponseModel getCollectLawyer(@RequestBody CollectReq.Collect req)
    {
        Integer curLoginId = loadCurLoginId();
        req.setAccountId(curLoginId);
        return ResponseModel.succ(collectFacade.getCollectLawyer(req));
    }

    @PostMapping("/confined/legalEngine/getCollectCase")
    public ResponseModel getCollectCase(@RequestBody CollectReq.Collect req)
    {
        Integer curLoginId = loadCurLoginId();
        req.setAccountId(curLoginId);
        return ResponseModel.succ(collectFacade.getCollectCase(req));
    }
}
