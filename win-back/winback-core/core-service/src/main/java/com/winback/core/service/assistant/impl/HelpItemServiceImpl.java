package com.winback.core.service.assistant.impl;

import com.winback.arch.common.ListResp;
import com.winback.arch.common.enums.SideType;
import com.winback.core.dao.assistant.THelpItemDao;
import com.winback.core.facade.assistant.vo.HelpItem;
import com.winback.core.model.assistant.THelpItem;
import com.winback.core.service.assistant.HelpItemService;
import com.winback.core.utils.RespConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xdrodger
 * @Title: HelpItemServiceImpl
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 16:35
 */
@Slf4j
@Service
public class HelpItemServiceImpl implements HelpItemService {

    @Autowired
    private THelpItemDao helpItemDao;

    @Override
    public ListResp<HelpItem> getHelpItemList(SideType sideType) {
        Boolean display = null;
        if (SideType.APP.equals(sideType)) {
            display = true;
        }
        ListResp<HelpItem> resp = new ListResp<>();
        resp.setList(helpItemDao.getHelpItemList(display));
        return resp;
    }

    @Override
    public HelpItem getHelpItem(Integer itemId) {
        THelpItem helpItem = helpItemDao.selectById(itemId);
        return RespConvertUtil.convertToHelpItem(helpItem);
    }
}
