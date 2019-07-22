package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_investment_agency_recruitment")
public class TInvestmentRecruitment {

    @Id
    @GeneratedValue()
    private Integer id;

    @Column(name = "position")
    private String position;
    @Column(name = "salary")
    private String salary;
    @Column(name = "publish_date")
    private String publishDate;
    @Column(name = "investment_id")
    private String investmentId;


}
