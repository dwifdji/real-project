package com._360pai.seimi.dao;

import com._360pai.seimi.model.JdPmTransactionDataBankLegal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public interface JdPmTransactionDataBankLegalDao extends JpaRepository<JdPmTransactionDataBankLegal, Serializable> {


}
