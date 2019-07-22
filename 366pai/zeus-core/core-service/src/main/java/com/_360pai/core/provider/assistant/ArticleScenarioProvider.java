package com._360pai.core.provider.assistant;

import com._360pai.core.facade.assistant.ArticleScenarioFacade;
import com._360pai.core.facade.assistant.req.ArticleScenarioReq;
import com._360pai.core.model.assistant.ArticleScenario;
import com._360pai.core.service.assistant.ArticleScenarioService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: ArticleScenarioFacadeImpl
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/21 9:19
 */
@Component
@Service(version = "1.0.0")
public class ArticleScenarioProvider implements ArticleScenarioFacade {

    @Autowired
    private ArticleScenarioService articleScenarioService;

    @Override
    public PageInfo selectArticleScenario(ArticleScenarioReq req) {
        return articleScenarioService.selectArticleScenario(req.getPage(), req.getPerPage());
    }

    @Override
    public int addArticleScenario(ArticleScenarioReq req) {
        return articleScenarioService.addArticleScenario(copyArticleScenario(req));
    }

    @Override
    public int editArticleScenario(ArticleScenarioReq req) {
        return articleScenarioService.editArticleScenario(copyArticleScenario(req));
    }

    private ArticleScenario copyArticleScenario(ArticleScenarioReq req) {
        ArticleScenario articleScenario = new ArticleScenario();
        BeanUtils.copyProperties(req, articleScenario);
        return articleScenario;
    }
}
