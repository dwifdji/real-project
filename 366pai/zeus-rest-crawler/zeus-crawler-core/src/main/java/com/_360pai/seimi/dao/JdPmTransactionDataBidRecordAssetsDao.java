package com._360pai.seimi.dao;

import com._360pai.seimi.model.JdPmTransactionDataBidRecordAssets;
import com._360pai.seimi.model.JdPmTransactionDataBidRecordLegal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public interface JdPmTransactionDataBidRecordAssetsDao extends JpaRepository<JdPmTransactionDataBidRecordAssets, Serializable> {

}
