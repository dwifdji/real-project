package com._360pai.core.service.agreement;


import com._360pai.core.model.agreement.DealAgreement;

public interface DealAgreementService{
    DealAgreement getByOrderId(Long orderId,String contractType);

    DealAgreement getByContractId(String contractId);

    public  boolean saveDealAgreement(DealAgreement dealAgreement);

    boolean updateDealAgreement(DealAgreement dealAgreement);
}