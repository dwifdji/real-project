package com.winback.core.service._case.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.winback.arch.common.AppReq;
import com.winback.arch.common.ListResp;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.dao._case.TCaseBigBriefDao;
import com.winback.core.dao._case.TCaseBriefDao;
import com.winback.core.exception.BusinessException;
import com.winback.core.facade._case.req.AdminCaseReq;
import com.winback.core.facade._case.vo.CaseBigBrief;
import com.winback.core.facade._case.vo.CaseBrief;
import com.winback.core.facade.contract.vo.ContractBigType;
import com.winback.core.facade.contract.vo.ContractType;
import com.winback.core.model._case.TCaseBigBrief;
import com.winback.core.model._case.TCaseBrief;
import com.winback.core.model.contract.TContractBigType;
import com.winback.core.service._case.CaseBriefService;
import com.winback.core.utils.ReqConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: CaseBriefServiceImpl
 * @ProjectName winback
 * @Description:
 * @date 2019/2/14 18:59
 */
@Slf4j
@Service
public class CaseBriefServiceImpl implements CaseBriefService {

    @Autowired
    private TCaseBriefDao caseBriefDao;
    @Autowired
    private TCaseBigBriefDao caseBigBriefDao;

    @Override
    public ListResp<CaseBrief> getCaseBriefList(AdminCaseReq.QueryReq req) {
        ListResp<CaseBrief> resp = new ListResp<>();
        List<CaseBrief> resultList = new ArrayList<>();
        List<TCaseBrief> list = caseBriefDao.getList((Map<String, Object>) JSON.toJSON(req));
        for (TCaseBrief item : list) {
            CaseBrief vo = new CaseBrief();
            BeanUtils.copyProperties(item, vo);
            resultList.add(vo);
        }
        resp.setList(resultList);
        return resp;
    }

    @Override
    public PageInfoResp<CaseBrief> getCaseBriefList(AppReq req) {
        PageInfoResp<CaseBrief> resp = new PageInfoResp<>();
        List<CaseBrief> resultList = new ArrayList<>();
        PageInfo<TCaseBrief> pageInfo = caseBriefDao.getList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "");
        for (TCaseBrief item : pageInfo.getList()) {
            CaseBrief vo = new CaseBrief();
            BeanUtils.copyProperties(item, vo);
            resultList.add(vo);
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public PageInfoResp<CaseBrief> getCaseBriefListByPage(AdminCaseReq.QueryReq req) {
        PageInfoResp<CaseBrief> resp = new PageInfoResp<>();
        List<CaseBrief> resultList = new ArrayList<>();
        PageInfo<TCaseBrief> pageInfo = caseBriefDao.getList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "");
        for (TCaseBrief item : pageInfo.getList()) {
            CaseBrief vo = new CaseBrief();
            BeanUtils.copyProperties(item, vo);
            resultList.add(vo);
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public Integer addCaseBrief(AdminCaseReq.AddCaseBriefReq req) {
        if (req.getCaseBigBriefId() != null) {
            TCaseBrief caseBrief = caseBriefDao.findBy(req.getCaseBigBriefId(), req.getName());
            if (caseBrief != null) {
                throw new BusinessException("子类已存在");
            }
            caseBrief = ReqConvertUtil.convertToTCaseBrief(req);
            caseBrief.setBigBriefId(req.getCaseBigBriefId());
            int result = caseBriefDao.insert(caseBrief);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            return caseBrief.getId();
        } else {
            TCaseBigBrief bigBrief = caseBigBriefDao.findBy(req.getName());
            if (bigBrief != null) {
                throw new BusinessException("大类已存在");
            }
            bigBrief = ReqConvertUtil.convertToTCaseBigBrief(req);
            int result = caseBigBriefDao.insert(bigBrief);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            return bigBrief.getId();
        }
    }

    @Override
    public Integer editCaseBrief(AdminCaseReq.EditCaseBriefReq req) {
        if (req.getCaseBigBriefId() != null) {
            TCaseBrief caseBrief = caseBriefDao.findBy(req.getCaseBigBriefId(), req.getName());
            if (caseBrief != null && !caseBrief.getId().equals(req.getId())) {
                throw new BusinessException("子类已存在");
            }
            caseBrief = caseBriefDao.selectById(req.getId());
            if (caseBrief == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            caseBrief = ReqConvertUtil.convertToTCaseBrief(req);
            caseBrief.setBigBriefId(req.getCaseBigBriefId());
            int result = caseBriefDao.updateById(caseBrief);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            return caseBrief.getId();
        } else {
            TCaseBigBrief bigBrief = caseBigBriefDao.findBy(req.getName());
            if (bigBrief != null && !bigBrief.getId().equals(req.getId())) {
                throw new BusinessException("大类已存在");
            }
            bigBrief = caseBigBriefDao.selectById(req.getId());
            if (bigBrief == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            };
            bigBrief = ReqConvertUtil.convertToTCaseBigBrief(req);
            int result = caseBigBriefDao.updateById(bigBrief);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            return bigBrief.getId();
        }

    }

    @Override
    public Integer deleteCaseBrief(AdminCaseReq.DeleteCaseBriefReq req) {
        if (req.getCaseBigBriefId() != null) {
            TCaseBrief caseBrief = caseBriefDao.selectById(req.getId());
            if (caseBrief == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            caseBrief.setDeleteFlag(true);
            int result = caseBriefDao.updateById(caseBrief);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            return caseBrief.getId();
        } else {
            TCaseBigBrief bigBrief = caseBigBriefDao.selectById(req.getId());
            if (bigBrief == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            bigBrief.setDeleteFlag(true);
            int result = caseBigBriefDao.updateById(bigBrief);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            List<TCaseBrief> list = caseBriefDao.findBy(bigBrief.getId());
            for (TCaseBrief item : list) {
                item.setDeleteFlag(true);
                caseBriefDao.updateById(item);
            }
            return bigBrief.getId();
        }
    }

    @Override
    public ListResp<CaseBigBrief> getCaseBigBriefList() {
        ListResp<CaseBigBrief> resp = new ListResp<>();
        List<CaseBigBrief> list = new ArrayList<>();
        CaseBigBrief first = new CaseBigBrief();
        first.setName("全部");
        first.setId("");
        list.add(first);
        List<TCaseBigBrief> itemList = caseBigBriefDao.getList();
        for (TCaseBigBrief item : itemList) {
            CaseBigBrief vo = new CaseBigBrief();
            vo.setId(item.getId() + "");
            vo.setName(item.getName());
            vo.setDisplay(item.getDisplay());
            vo.setCaseBriefs(getCaseBriefList(item.getId()));
            list.add(vo);
        }
        resp.setList(list);
        return resp;
    }

    @Override
    public ListResp<CaseBigBrief> getBackgroundCaseBigBriefList(Boolean all) {
        ListResp<CaseBigBrief> resp = new ListResp<>();
        List<CaseBigBrief> list = new ArrayList<>();
        if (all) {
            List<TCaseBigBrief> itemList = caseBigBriefDao.getBackgroundList();
            for (TCaseBigBrief item : itemList) {
                CaseBigBrief vo = new CaseBigBrief();
                vo.setId(item.getId() + "");
                vo.setName(item.getName());
                vo.setDisplay(item.getDisplay());
                vo.setCaseBriefs(getBackgroundCaseBriefList(item.getId(), all));
                list.add(vo);
            }
        } else {
            List<TCaseBigBrief> itemList = caseBigBriefDao.getList();
            for (TCaseBigBrief item : itemList) {
                CaseBigBrief vo = new CaseBigBrief();
                vo.setId(item.getId() + "");
                vo.setName(item.getName());
                vo.setDisplay(item.getDisplay());
                vo.setCaseBriefs(getBackgroundCaseBriefList(item.getId(), all));
                list.add(vo);
            }
        }
        resp.setList(list);
        return resp;
    }

    private List<CaseBrief> getCaseBriefList(Integer bigBriefId) {
        List<CaseBrief> resultList = new ArrayList<>();
        CaseBrief first = new CaseBrief();
        first.setName("全部");
        first.setId("");
        resultList.add(first);
        List<TCaseBrief> list = caseBriefDao.findBy(bigBriefId, true);
        for (TCaseBrief item : list) {
            CaseBrief vo = new CaseBrief();
            vo.setId(item.getId() + "");
            vo.setName(item.getName());
            vo.setDisplay(item.getDisplay());
            vo.setBigBriefId(item.getBigBriefId());
            resultList.add(vo);
        }
        return resultList;
    }


    private List<CaseBrief> getBackgroundCaseBriefList(Integer bigBriefId, Boolean all) {
        List<CaseBrief> resultList = new ArrayList<>();
        List<TCaseBrief> list;
        if (all) {
            list = caseBriefDao.findBy(bigBriefId);
        } else {
            list = caseBriefDao.findBy(bigBriefId, true);
        }
        for (TCaseBrief item : list) {
            CaseBrief vo = new CaseBrief();
            vo.setId(item.getId() + "");
            vo.setName(item.getName());
            vo.setDisplay(item.getDisplay());
            vo.setBigBriefId(item.getBigBriefId());
            resultList.add(vo);
        }
        return resultList;
    }
}
