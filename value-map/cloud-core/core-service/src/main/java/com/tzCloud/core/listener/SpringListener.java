package com.tzCloud.core.listener;

import com.alibaba.fastjson.JSON;
import com.tzCloud.arch.common.constant.RedisKeyConstant;
import com.tzCloud.core.constant.CaseEnum;
import com.tzCloud.core.constant.CourtEnum;
import com.tzCloud.core.constant.SysConstant;
import com.tzCloud.core.dao.caseMatching.TTreeContentDao;
import com.tzCloud.core.dao.legalEngine.TCourtDao;
import com.tzCloud.core.facade.legalEngine.vo.Brief;
import com.tzCloud.core.facade.legalEngine.vo.Court;
import com.tzCloud.core.hanLP.Config;
import com.tzCloud.core.model.caseMatching.TTreeContent;
import com.tzCloud.core.model.legalEngine.TCourt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class SpringListener implements CommandLineRunner {

	@Autowired
	private TTreeContentDao treeContentDao;
	@Resource
	private TCourtDao courtDao;

	@Override
	public void run(String... args) throws Exception {
		// args 就是 main 函数的 args
		log.info("服务启动，args={}", Arrays.toString(args));
		new Config();
		loadBriefTreeContents();
		loadCourts();
	}

	private void loadBriefTreeContents() {
		SysConstant.briefTreeContents = new HashMap<>();
		log.info("服务启动，开始加载案由信息");
		long startTime = System.currentTimeMillis();
		List<TTreeContent> list = treeContentDao.findAllBrief();
		for (TTreeContent item : list) {
			SysConstant.briefTreeContents.put(item.getId(), item);
		}
		log.info("案由信息={}", SysConstant.briefTreeContents.size());
		log.info("服务启动，结束加载案由信息，耗时={}ms", (System.currentTimeMillis() - startTime));
		//log.info("对象内部信息---------{}", ClassLayout.parseInstance(SysConstant.briefTreeContents).toPrintable());
		//log.info("查看对象外部信息---------{}",  GraphLayout.parseInstance(SysConstant.briefTreeContents).toPrintable());
		log.info("获取对象总大小---------SysConstant.briefTreeContents={}kb",  GraphLayout.parseInstance(SysConstant.briefTreeContents).totalSize() / 1024);
		loadBriefLevels();
	}


	private void loadBriefLevels() {
		SysConstant.briefLevels = new HashMap<>();
		for (Integer briefId : SysConstant.briefTreeContents.keySet()) {
			SysConstant.briefLevels.put(briefId , parseBriefLevel(briefId));
		}
		log.info("获取对象总大小---------SysConstant.briefLevels={}kb",  GraphLayout.parseInstance(SysConstant.briefLevels).totalSize() / 1024);
	}

	private Brief parseBriefLevel(Integer briefId) {
		Brief brief = new Brief();
		TTreeContent b = (TTreeContent) SysConstant.briefTreeContents.get(briefId);
		if (CaseEnum.BriefLevel.First.getValue().equals(b.getField())) {
			brief.setFirstId(b.getId());
		} else if (CaseEnum.BriefLevel.Second.getValue().equals(b.getField())) {
			brief.setSecondId(b.getId());
			brief.setFirstId(b.getParentId());
		} else if (CaseEnum.BriefLevel.Third.getValue().equals(b.getField())) {
			brief.setThirdId(b.getId());
			brief.setSecondId(b.getParentId());
			TTreeContent second = (TTreeContent) SysConstant.briefTreeContents.get(b.getParentId());
			brief.setFirstId(second.getParentId());
		} else if (CaseEnum.BriefLevel.Fourth.getValue().equals(b.getField())) {
			brief.setFourthId(b.getId());
			brief.setThirdId(b.getParentId());
			TTreeContent third = (TTreeContent) SysConstant.briefTreeContents.get(b.getParentId());
			brief.setSecondId(third.getParentId());
			TTreeContent second = (TTreeContent) SysConstant.briefTreeContents.get(third.getParentId());
			brief.setFirstId(second.getParentId());
		} else if (CaseEnum.BriefLevel.Fifth.getValue().equals(b.getField())) {
			brief.setFifthId(b.getId());
			brief.setFourthId(b.getParentId());
			TTreeContent fourth = (TTreeContent) SysConstant.briefTreeContents.get(b.getParentId());
			brief.setThirdId(fourth.getParentId());
			TTreeContent third = (TTreeContent) SysConstant.briefTreeContents.get(fourth.getParentId());
			brief.setSecondId(third.getParentId());
			TTreeContent second = (TTreeContent) SysConstant.briefTreeContents.get(third.getParentId());
			brief.setFirstId(second.getParentId());
		}
		return brief;
	}

	private void loadCourts() {
		SysConstant.courts = new HashMap<>();
		log.info("服务启动，开始加载法院信息");
		long startTime = System.currentTimeMillis();
		List<TCourt> list = courtDao.selectAll();
		for (TCourt model : list) {
			Court vo = new Court();
			vo.setType(CourtEnum.Type.getKeyByValue(model.getType()));
			vo.setProvince(model.getProvince());
			vo.setCity(model.getCity());
			if (CourtEnum.Type.Type_4.getKey().equals(CourtEnum.Type.getKeyByValue(model.getType()))) {
				vo.setName("最高人民法院");
			} else {
				vo.setName(model.getSimpleName());
			}
			SysConstant.courts.put(vo.getName(), vo);
		}
		log.info("法院信息={}", SysConstant.courts.size());
		log.info("服务启动，结束加载法院信息，耗时={}ms", (System.currentTimeMillis() - startTime));
		log.info("获取对象总大小---------SysConstant.courts={}kb",  GraphLayout.parseInstance(SysConstant.courts).totalSize() / 1024);
	}
}