package com._360pai.crawler.dao.rmfyssw;

import com._360pai.crawler.model.rmfyssw.RMFYSSTransactionDataBidRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public interface RMFYSSTransactionDataBidRecordDao extends JpaRepository<RMFYSSTransactionDataBidRecord, Serializable> {

}
