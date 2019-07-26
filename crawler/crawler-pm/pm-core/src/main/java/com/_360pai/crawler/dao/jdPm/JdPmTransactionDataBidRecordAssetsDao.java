package com._360pai.crawler.dao.jdPm;

import com._360pai.crawler.model.jdPm.JdPmTransactionDataBidRecordAssets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public interface JdPmTransactionDataBidRecordAssetsDao extends JpaRepository<JdPmTransactionDataBidRecordAssets, Serializable> {

}
