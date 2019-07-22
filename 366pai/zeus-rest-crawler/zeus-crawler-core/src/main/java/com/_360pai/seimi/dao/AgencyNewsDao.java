package com._360pai.seimi.dao;

import com._360pai.seimi.model.TAgencyNews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface AgencyNewsDao extends JpaRepository<TAgencyNews, Serializable> {
}
