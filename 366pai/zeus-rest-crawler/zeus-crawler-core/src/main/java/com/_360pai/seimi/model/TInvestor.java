package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_investor")
public class TInvestor {

    @Id
    @GeneratedValue()
    private Integer id;

    @Column(name = "investor_name")
    private String investorName;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "city_name")
    private String cityName;
}
