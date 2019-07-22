package com._360pai.seimi.dao;

import com._360pai.seimi.model.WinningBid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface WinningBidDao extends JpaRepository<WinningBid, Serializable> {

}
