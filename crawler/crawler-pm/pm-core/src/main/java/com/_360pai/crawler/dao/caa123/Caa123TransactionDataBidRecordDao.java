package com._360pai.crawler.dao.caa123;

import com._360pai.crawler.model.caa123.Caa123TransactionDataBidRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public interface Caa123TransactionDataBidRecordDao extends JpaRepository<Caa123TransactionDataBidRecord, Serializable> {

}
