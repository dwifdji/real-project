package com._360pai.seimi.dao;

import com._360pai.seimi.model.AliPmTransactionDataBidRecord;
import com._360pai.seimi.model.JdPmTransactionDataAssets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public interface AliPmTransactionDataBidRecordDao extends JpaRepository<AliPmTransactionDataBidRecord, Serializable> {

}
