package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_investment_agency_office")
public class TInvestmentOffice {
    @Id
    @GeneratedValue()
    private Integer id;

    @Column(name = "office_name")
    private String officeName;
    @Column(name = "office_type")
    private String officeType;
    @Column(name = "office_city")
    private String officeCity;
    @Column(name = "begin_at")
    private String beginAt;
    @Column(name = "investment_id")
    private String investmentId;
}
