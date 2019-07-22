package com._360pai.core.service.withfudig.impl;

import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.condition.withfudig.TWithfudigConfigCondition;
import com._360pai.core.dao.withfudig.TWithfudigConfigDao;
import com._360pai.core.facade.assistant.req.ServiceConfigReq;
import com._360pai.core.facade.withfudig.req.WithfudigConfigReq;
import com._360pai.core.facade.withfudig.resp.WithfudigConfigResp;
import com._360pai.core.model.withfudig.TWithfudigConfig;
import com._360pai.core.service.withfudig.WithfudigConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述 配资乐 设置
 *
 * @author : whisky_vip
 * @date : 2018/9/6 16:22
 */
@Service
public class WithfudigConfigServiceImpl implements WithfudigConfigService {

    @Autowired
    private TWithfudigConfigDao tWithfudigConfigDao;

    @Override
    public List<WithfudigConfigResp> getConfigList() {
        List<TWithfudigConfig>    list   = tWithfudigConfigDao.selectAll();
        List<WithfudigConfigResp> result = new ArrayList<>(list.size());
        WithfudigConfigResp       resp;
        for (TWithfudigConfig model : list) {
            resp = new WithfudigConfigResp();
            BeanUtils.copyProperties(model, resp);
            result.add(resp);
        }
        return result;
    }

    @Override
    public PageUtils.Page getConfigListWithPages(ServiceConfigReq.ConfigList req) {
        PageHelper.startPage(req.getPage(),req.getPerPage());
        List<TWithfudigConfig>    list   = tWithfudigConfigDao.selectAll();
        PageInfo<TWithfudigConfig> pageInfo = new PageInfo<>(list);

        List<WithfudigConfigResp> result = new ArrayList<>(list.size());
        WithfudigConfigResp       resp;
        for (TWithfudigConfig model : list) {
            resp = new WithfudigConfigResp();
            BeanUtils.copyProperties(model, resp);
            result.add(resp);
        }

        PageUtils.Page<WithfudigConfigResp> pageResult = new PageUtils.Page<>();
        pageResult.setTotal((int) pageInfo.getTotal());
        pageResult.setList(result);
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setPage(req.getPage());
        pageResult.setPerPage(req.getPerPage());
        return pageResult;
    }

    @Override
    public int delConfigById(WithfudigConfigReq.DelConfigData req) {
        TWithfudigConfigCondition condition = new TWithfudigConfigCondition();
        condition.setId(req.getConfigId());
        TWithfudigConfig tWithfudigConfig = tWithfudigConfigDao.selectOneResult(condition);
        int              count            = 0;
        if (tWithfudigConfig != null) {
            tWithfudigConfig.setIsDelete(true);
            tWithfudigConfig.setUpdateTime(new Date());
            tWithfudigConfig.setOperatorId(req.getOperatorId().toString());
            count = tWithfudigConfigDao.updateById(tWithfudigConfig);
        }
        return count;
    }

    @Override
    public int updateConfig(WithfudigConfigReq.UpdateConfigData req) {
        TWithfudigConfigCondition condition = new TWithfudigConfigCondition();
        condition.setId(req.getConfigId());
        TWithfudigConfig tWithfudigConfig = tWithfudigConfigDao.selectOneResult(condition);

        int     count = 0;
        Boolean flag  = req.getDescription() != null || req.getRank() != null;
        if (tWithfudigConfig != null && flag) {
            if (req.getDescription() != null) {
                tWithfudigConfig.setDescription(req.getDescription());
            }
            if (req.getRank() != null) {
                tWithfudigConfig.setRank(req.getRank());
            }

            tWithfudigConfig.setOperatorId(req.getOperatorId().toString());
            tWithfudigConfig.setUpdateTime(new Date());
            count = tWithfudigConfigDao.updateById(tWithfudigConfig);

        }

        return count;
    }

    @Override
    public int addConfig(WithfudigConfigReq.AddConfigData req) {
        TWithfudigConfig tWithfudigConfig = new TWithfudigConfig();
        tWithfudigConfig.setDescription(req.getDescription());
        tWithfudigConfig.setOperatorId(req.getOperatorId().toString());
        tWithfudigConfig.setCreatedTime(new Date());
        Integer rank = tWithfudigConfigDao.selectMaxRank();
        tWithfudigConfig.setRank((rank == null ? 0 : rank) + 1);
        return tWithfudigConfigDao.insert(tWithfudigConfig);
    }
}
