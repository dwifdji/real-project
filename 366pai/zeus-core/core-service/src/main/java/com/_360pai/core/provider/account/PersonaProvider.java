package com._360pai.core.provider.account;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.exception.BaseBusinessException;

import com._360pai.core.facade.account.PersonaFacade;
import com._360pai.core.facade.account.req.PersonaReq;
import com._360pai.core.facade.account.resp.PersonaDetailResp;
import com._360pai.core.facade.account.resp.PersonaResp;
import com._360pai.core.service.account.PersonaService;
import com._360pai.core.service.assistant.StaffService;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xdrodger
 * @Title: PersonaProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 29/08/2018 13:35
 */
@Component
@Service(version = "1.0.0")
public class PersonaProvider implements PersonaFacade {

    @Autowired
    private PersonaService personaService;
    @Autowired
    private StaffService staffService;

    @Override
    public ResponseModel create(PersonaReq req) {
        try {
            PersonaResp resp = personaService.createPersona(req);
            return ResponseModel.succ(resp);
        } catch (BaseBusinessException ee) {
            return ResponseModel.fail(ee.getErrorCode(), ee.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
        }
    }

    @Override
    public ResponseModel update(PersonaReq req) {
        try {
            PersonaResp resp = personaService.updatePersona(req);
            return ResponseModel.succ(resp);
        } catch (BaseBusinessException ee) {
            return ResponseModel.fail(ee.getErrorCode(), ee.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
        }
    }

    @Override
    public ResponseModel detail(PersonaReq req) {
        try {
            PersonaDetailResp resp = personaService.getPersonaById(req);
            return ResponseModel.succ(resp);
        } catch (BaseBusinessException ee) {
            return ResponseModel.fail(ee.getErrorCode(), ee.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
        }
    }

    @Override
    public ResponseModel list(PersonaReq req) {
        try {
            return ResponseModel.succ(personaService.getPersonaListByPage(req));
        } catch (BaseBusinessException ee) {
            return ResponseModel.fail(ee.getErrorCode(), ee.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
        }
    }

    @Override
    public ResponseModel login(PersonaReq req) {
        try {
            if (StringUtils.isEmpty(req.getUsername()) || StringUtils.isEmpty(req.getPassword())) {
                return ResponseModel.fail(ApiCallResult.EMPTY);
            }
            return ResponseModel.succ(staffService.login(req));
        } catch (BaseBusinessException ee) {
            return ResponseModel.fail(ee.getErrorCode(), ee.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
        }
    }
}
