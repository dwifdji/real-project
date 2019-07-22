
package com._360pai.core.model.activity;

import lombok.Data;

import java.util.Date;

/**
 */
@Data
public class JointActivityMap implements java.io.Serializable{

	private Integer id;
	private Integer activityId;
	private Integer agencyId;
	private Integer isDel;
	private Date createTime;
	private Date updateTime;

	private Integer type;



}
