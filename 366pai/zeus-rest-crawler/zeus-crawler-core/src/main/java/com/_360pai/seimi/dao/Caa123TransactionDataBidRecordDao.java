package com._360pai.seimi.dao;

import com._360pai.seimi.model.Caa123TransactionDataBidRecord;
import com._360pai.seimi.model.RMFYSSTransactionDataBidRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public interface Caa123TransactionDataBidRecordDao extends JpaRepository<Caa123TransactionDataBidRecord, Serializable> {

}
