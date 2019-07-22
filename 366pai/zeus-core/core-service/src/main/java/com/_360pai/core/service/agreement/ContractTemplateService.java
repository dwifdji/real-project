package com._360pai.core.service.agreement;


import com._360pai.core.condition.agreement.ContractTemplateCondition;
import com._360pai.core.model.agreement.ContractTemplate;

public interface ContractTemplateService{

    ContractTemplate getTemplate(ContractTemplateCondition template);


}