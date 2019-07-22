package com._360pai.seimi.dao;

import com._360pai.seimi.model.SoYinBigCategory;
import com._360pai.seimi.model.SoYinSmallCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface SoYinSmallCategoryDao extends JpaRepository<SoYinSmallCategory, Serializable> {
}
