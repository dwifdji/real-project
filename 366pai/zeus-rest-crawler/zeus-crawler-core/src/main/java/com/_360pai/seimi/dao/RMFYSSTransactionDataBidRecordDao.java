package com._360pai.seimi.dao;

import com._360pai.seimi.model.JdPmTransactionDataLegal;
import com._360pai.seimi.model.RMFYSSTransactionData;
import com._360pai.seimi.model.RMFYSSTransactionDataBidRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public interface RMFYSSTransactionDataBidRecordDao extends JpaRepository<RMFYSSTransactionDataBidRecord, Serializable> {

}
