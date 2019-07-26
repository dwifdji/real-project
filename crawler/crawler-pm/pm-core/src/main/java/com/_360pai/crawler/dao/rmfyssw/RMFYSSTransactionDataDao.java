package com._360pai.crawler.dao.rmfyssw;

import com._360pai.crawler.model.rmfyssw.RMFYSSTransactionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public interface RMFYSSTransactionDataDao extends JpaRepository<RMFYSSTransactionData, Serializable> {

    @Query(value = "select * from rmfyss_transaction_data b where b.code=?1", nativeQuery = true)
    RMFYSSTransactionData getOneByCode(String code);

}
