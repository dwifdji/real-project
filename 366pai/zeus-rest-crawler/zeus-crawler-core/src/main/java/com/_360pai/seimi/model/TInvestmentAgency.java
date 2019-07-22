package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_investment_agency")
public class TInvestmentAgency {

    @Id
    @GeneratedValue()
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "official_website")
    private String officialWebsite;
    @Column(name = "city")
    private String city;
    @Column(name = "agency_type")
    private String agencyType;
    @Column(name = "company_type")
    private String companyType;
    @Column(name = "legal_representative")
    private String legalRepresentative;
    @Column(name = "organization_code")
    private String organizationCode;
    @Column(name = "established_at")
    private String establishedAt;
    @Column(name = "photo_at")
    private String photoAt;
    @Column(name = "registered_capital")
    private String registeredCapital;
    @Column(name = "office_size")
    private String officeSize;
    @Column(name = "agency_address")
    private String agencyAddress;
    @Column(name = "business_scope")
    private String businessScope;
    @Column(name = "company_desc")
    private String companyDesc;
    @Column(name = "company_image")
    private String companyImage;
    @Column(name = "contact_people")
    private String contactPeople;
    @Column(name = "contact_phone")
    private String contactPhone;
    @Column(name = "agency_members")
    private String agencyMembers;
    @Column(name = "service_deTail")
    private String serviceDeTail;

}
