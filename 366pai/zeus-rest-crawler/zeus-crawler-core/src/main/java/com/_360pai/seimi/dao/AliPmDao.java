package com._360pai.seimi.dao;

import com._360pai.seimi.model.AliPm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public interface AliPmDao extends JpaRepository<AliPm, Serializable> {

    AliPm findByCode(String code);
}
