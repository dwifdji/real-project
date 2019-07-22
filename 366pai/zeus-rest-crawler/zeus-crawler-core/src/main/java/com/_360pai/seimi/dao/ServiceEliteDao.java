package com._360pai.seimi.dao;

import com._360pai.seimi.model.JdPm;
import com._360pai.seimi.model.TServiceElite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public interface ServiceEliteDao extends JpaRepository<TServiceElite, Serializable> {

}
