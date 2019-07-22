package com._360pai.core.service.account;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.account.req.PersonaReq;
import com._360pai.core.facade.account.resp.PersonaDetailResp;
import com._360pai.core.facade.account.resp.PersonaListResp;
import com._360pai.core.facade.account.resp.PersonaResp;


/**
 * @author xdrodger
 * @Title: PersonaService
 * @ProjectName zeus-parent
 * @Description:
 * @date 29/08/2018 13:30
 */
public interface PersonaService {

    PersonaResp createPersona(PersonaReq req);

    PersonaResp updatePersona(PersonaReq req);

    PersonaDetailResp getPersonaById(PersonaReq req);

    PageInfoResp<PersonaListResp> getPersonaListByPage(PersonaReq req);
}
