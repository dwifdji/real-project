package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuhaolei
 * @since 2018-11-13
 */
@Data
@Entity
@Table(name = "t_agency_recruitment")
public class TAgencyRecruitment{

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue()
    private Integer id;
    private String position;
    private String salary;
    @Column(name = "release_date")
    private String releaseDate;
    @Column(name = "agency_id")
    private String agencyId;

}
