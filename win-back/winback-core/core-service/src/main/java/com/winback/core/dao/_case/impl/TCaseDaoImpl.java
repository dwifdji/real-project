
package com.winback.core.dao._case.impl;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.winback.core.commons.constants.CaseEnum;
import com.winback.core.condition.assistant.TComAreaCondition;
import com.winback.core.condition.assistant.TComCityCondition;
import com.winback.core.condition.assistant.TComProvinceCondition;
import com.winback.core.condition.connect.TConnectRoomPersonCondition;
import com.winback.core.dao._case.TCaseBriefDao;
import com.winback.core.dao._case.TCaseProjectManagerMapDao;
import com.winback.core.dao._case.mapper.TCaseAssetMapper;
import com.winback.core.dao._case.mapper.TCaseLawyerOrderMapper;
import com.winback.core.dao.assistant.mapper.TComAreaMapper;
import com.winback.core.dao.assistant.mapper.TComCityMapper;
import com.winback.core.dao.assistant.mapper.TComProvinceMapper;
import com.winback.core.dao.connect.mapper.TConnectRoomPersonMapper;
import com.winback.core.facade._case.req.CaseAssetReq;
import com.winback.core.facade._case.req.CaseCommReq;
import com.winback.core.facade._case.req.CaseLawyerOrderReq;
import com.winback.core.facade._case.vo.Case;
import com.winback.core.facade._case.vo.HomePageCaseVO;

import com.winback.core.model._case.TCaseAsset;
import com.winback.core.model._case.TCaseLawyerOrder;
import com.winback.core.model._case.TCaseStatusUpdateRecord;
import com.winback.core.model.assistant.TComArea;
import com.winback.core.model.assistant.TComCity;
import com.winback.core.model.assistant.TComProvince;
import com.winback.core.model.connect.TConnectRoomPerson;
import com.winback.core.vo.finance.WorkBenchVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.winback.core.condition._case.TCaseCondition;
import com.winback.core.dao._case.mapper.TCaseMapper;
import com.winback.core.model._case.TCase;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao._case.TCaseDao;

import java.util.List;
import java.util.Map;

@Service
public class TCaseDaoImpl extends AbstractDaoImpl<TCase, TCaseCondition, BaseMapper<TCase,TCaseCondition>> implements TCaseDao{
	
	@Resource
	private TCaseMapper tCaseMapper;

	@Resource
	private TComProvinceMapper tComProvinceMapper;

	@Resource
	private TComCityMapper tComCityMapper;

	@Resource
	private TComAreaMapper tComAreaMapper;

	@Resource
	private TCaseLawyerOrderMapper tCaseLawyerOrderMapper;

	@Resource
	private TCaseBriefDao caseBriefDao;

	@Resource
	private TCaseAssetMapper tCaseAssetMapper;
	@Resource
	private TCaseProjectManagerMapDao caseProjectManagerMapDao;

	@Resource
	private TConnectRoomPersonMapper tConnectRoomPersonMapper;


	@Override
	protected BaseMapper<TCase, TCaseCondition> daoSupport() {
		return tCaseMapper;
	}

	@Override
	protected TCaseCondition blankCondition() {
		return new TCaseCondition();
	}

	@Override
	public PageInfo<TCase> getListByAccountId(int page, int perPage, Integer accountId, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		TCaseCondition condition = new TCaseCondition();
		condition.setAccountId(accountId);
		List<TCase> list = tCaseMapper.selectByCondition(condition);
		return new PageInfo<>(list);
	}


	@Override
	public PageInfo<TCase> searchCase(int page, int perPage, TCaseCondition condition, String orderBy,String personType) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TCase> list = tCaseMapper.searchCase(condition);
		if(list != null){
			setAreaName(list);

			setUnreadFlag(list,personType,condition.getAccountId());
		}
		return new PageInfo<>(list);
	}

	private void setUnreadFlag(List<TCase> list,String personType,Integer accountId) {

		if(StringUtils.isEmpty(personType)||accountId==null){
			return;
		}
		TConnectRoomPersonCondition condition = new TConnectRoomPersonCondition();
		condition.setRelevanceId(accountId);
		condition.setType(personType);
		List<TConnectRoomPerson> personList = tConnectRoomPersonMapper.selectByCondition(condition);

		for(TCase tCase : list){
			tCase.setUnReadFlag(false);

			for(TConnectRoomPerson roomPerson :personList){
				if(tCase.getId().equals(roomPerson.getCaseId())){

					if(roomPerson.getUnreadNum()!=null&&roomPerson.getUnreadNum()>0){
						tCase.setUnReadFlag(true);
					}
				}
			}
		}
	}

	@Override
	public PageInfo<TCase> searchCaseByName(int page, int perPage, String name, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TCase> list = tCaseMapper.searchCaseByName(name);
		if(list != null){
			setAreaName(list);
		}
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<TCase> searchSelfCaseByName(int page, int perPage, String name, Integer accountId, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TCase> list = tCaseMapper.searchSelfCaseByName(name,accountId);
		if(list != null){
			setAreaName(list);
			for(TCase tCase : list){
				if(String.valueOf(accountId).equals(String.valueOf(tCase.getAccountId()))){
					tCase.setCaseSource("0");
				}else{
					tCase.setCaseSource("1");
				}
				if (tCase.getLawyerAccountId() != null && tCase.getLawyerAccountId().equals(accountId)) {
					tCase.setHasAcceptedFlag(true);
				} else if (caseProjectManagerMapDao.isCaseAllocatedToThisAccount(tCase.getCaseId(), accountId)) {
					tCase.setHasAcceptedFlag(true);
				}
			}
		}
		return new PageInfo<>(list);
	}

	private void setAreaName(List<TCase> list){
		for(TCase tCase : list){

			if(!StringUtils.isEmpty(tCase.getProvinceCode())){
				TComProvinceCondition provinceCondition = new TComProvinceCondition();
				provinceCondition.setCode(tCase.getProvinceCode());
				List<TComProvince> provinceList = tComProvinceMapper.selectByCondition(provinceCondition);
				if(provinceList != null && !provinceList.isEmpty() && provinceList.get(0) != null){
					tCase.setProvinceName(provinceList.get(0).getName());
				}
			}

			if(!StringUtils.isEmpty(tCase.getCityCode())){
				TComCityCondition cityCondition = new TComCityCondition();
				cityCondition.setCode(tCase.getCityCode());
				List<TComCity> cityList = tComCityMapper.selectByCondition(cityCondition);
				if(cityList != null && !cityList.isEmpty() && cityList.get(0) != null){
					tCase.setCityName(cityList.get(0).getName());
				}
			}

			if(!StringUtils.isEmpty(tCase.getAreaCode())){
				TComAreaCondition areaCondition = new TComAreaCondition();
				areaCondition.setCode(tCase.getAreaCode());
				List<TComArea> areaList = tComAreaMapper.selectByCondition(areaCondition);
				if(areaList != null && !areaList.isEmpty() && areaList.get(0) != null){
					tCase.setAreaName(areaList.get(0).getName());
				}
			}


			tCase.setCaseReason(caseBriefDao.getName(tCase.getCaseBriefId()));
			tCase.setEndFlag(tCase.getMainStatus().equals(CaseEnum.MainStatus.SUCCESS.getKey()));
			tCase.setStatusDesc(CaseEnum.MainStatus.getValueByKey(tCase.getMainStatus()));
		}
	}

	@Override
	public PageInfo<TCase> getApplyedCase(CaseCommReq req) {
		PageHelper.startPage(req.getPage(), req.getPerPage());
		PageHelper.orderBy("id desc");

		List<TCase> list = tCaseMapper.getApplyCase(req);
		if(list != null){
			setAreaName(list);
			setUnreadFlag(list,req.getPersonType(),req.getAccountId());

		}
		for(TCase tCase : list){
			tCase.setHasAcceptedFlag(String.valueOf(req.getAccountId()).equals(String.valueOf(tCase.getLawyerAccountId())));
		}
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<TCaseLawyerOrder> searchLawyerOrder(int page, int perPage, String orderBy,CaseLawyerOrderReq req) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TCaseLawyerOrder> list = tCaseLawyerOrderMapper.searchLawyerOrder(req);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<TCaseAsset> searchCaseAsset(int page, int perPage, String orderBy, CaseAssetReq req) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TCaseAsset> list = tCaseAssetMapper.searchCaseAsset(req);
		return new PageInfo<>(list);
	}

	@Override
	public List<TCaseAsset> searchCaseList(CaseAssetReq req) {
		return tCaseAssetMapper.searchCaseAsset(req);
	}

	@Override
	public PageInfo<TCase> getPublishedCaseList(int page, int perPage, String orderBy, Integer caseTypeId) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TCase> list = tCaseMapper.getPublishedCaseList(caseTypeId);
		return new PageInfo<>(list);
	}

	@Override
	public TCase getCaseByCaseId(String caseId) {
		TCaseCondition condition = new TCaseCondition();
		condition.setCaseId(caseId);
		List<TCase> caseList = tCaseMapper.selectByCondition(condition);
		if(caseList != null && !caseList.isEmpty()){
			setAreaName(caseList);
			return caseList.get(0);
		}else{
			return null;
		}
	}


	@Override
	public int applyCaseCount(Integer accountId) {
		return 0;
	}

	@Override
	public List<HomePageCaseVO> getRecommendedCaseList(Integer caseSize) {
		return tCaseMapper.getRecommendedCaseList(caseSize);
	}

	@Override
	public List<WorkBenchVO> getStatusCase() {
		return tCaseMapper.getStatusCase();
	}

	@Override
	public PageInfo<Case> getProjectManagerAllocatedCaseList(int page, int perPage, Integer accountId, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<Case> list = tCaseMapper.getProjectManagerAllocatedCaseList(accountId);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<TCase> getMyManageCaseList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TCase> list = tCaseMapper.getMyManageCaseList(params);
		setAreaName(list);
		return new PageInfo<>(list);
	}
}
