package com._360pai.core.facade.account;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.req.PersonaReq;

/**
 * @author xdrodger
 * @Title: PersonaFacade
 * @ProjectName zeus-parent
 * @Description:
 * @date 29/08/2018 13:35
 */
public interface PersonaFacade {

    ResponseModel create(PersonaReq req);

    ResponseModel update(PersonaReq req);

    ResponseModel detail(PersonaReq req);

    ResponseModel list(PersonaReq req);

    ResponseModel login(PersonaReq req);
}
