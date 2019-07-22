package com.winback.core.service.assistant;

import com.winback.arch.common.ListResp;
import com.winback.arch.common.enums.SideType;
import com.winback.core.facade.assistant.vo.HelpItem;
import jdk.nashorn.internal.ir.IdentNode;

/**
 * @author xdrodger
 * @Title: HelpItemService
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 16:35
 */
public interface HelpItemService {
    ListResp<HelpItem> getHelpItemList(SideType sideType);

    HelpItem getHelpItem(Integer itemId);
}
