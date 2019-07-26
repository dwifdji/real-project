package com.mybatisPlus.demo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mybatisPlus.demo.mapper.LjTransactionDataMapper;
import com.mybatisPlus.demo.model.AddressDetail;
import com.mybatisPlus.demo.model.LjTransactionData;
import com.mybatisPlus.demo.service.LjTransactionDataService;
import com.mybatisPlus.demo.util.BaiDuMapUtil;
import com.mybatisPlus.demo.vo.LjTransactionDataVO;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuhaolei
 * @since 2019-06-12
 */
@Service
public class LjTransactionDataServiceImpl extends ServiceImpl<LjTransactionDataMapper, LjTransactionData> implements LjTransactionDataService {
	
	
	@Autowired
	private LjTransactionDataMapper ljTransactionDataMapper;
	
	@Override
	public List<LjTransactionData> getLjTransactionData(int beginIndex, int num, List<LjTransactionDataVO> liLjTransactionDatas) {
		
		List<LjTransactionData> arrayList = new ArrayList<LjTransactionData>();
		
		int flag = 0;
		try {
			for(int i = beginIndex, len = beginIndex + num; i < len; i++) {
				LjTransactionDataVO ljTransactionDataVO = liLjTransactionDatas.get(i);
				
				if(StringUtils.isNotBlank(ljTransactionDataVO.getAddress())) {
					continue;
				}
				
				String[] split = ljTransactionDataVO.getTitle().split(" ");
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("address", ljTransactionDataVO.getProvince() + 
						ljTransactionDataVO.getCity() + ljTransactionDataVO.getArea() + split[0]);
				
				AddressDetail adressDetail = BaiDuMapUtil.getAdressDetail(params);
				
				
				if(adressDetail != null) {
					params = new HashMap<String, Object>();
					params.put("location", adressDetail.getLat() + "," + adressDetail.getLng());
					
					adressDetail = BaiDuMapUtil.getAdressDetailFormat(params, adressDetail);
					
					LjTransactionData ljTransactionData = new LjTransactionData();
					BeanUtils.copyProperties(ljTransactionDataVO, ljTransactionData);
					
					ljTransactionData.setLat(adressDetail.getLat());
					ljTransactionData.setLng(adressDetail.getLng());
					ljTransactionData.setAddress(adressDetail.getAdress());
					
					arrayList.add(ljTransactionData);
					
					flag = i;
					System.out.println("已经开始获取第" + i + "条数据啦");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			System.out.println("第" + flag +"数据已经出现异常");
		}
		
		return arrayList;
	}

	@Override
	public List<LjTransactionDataVO> getLjTransactionDataVOS() {
		
		List<LjTransactionDataVO> liLjTransactionDatas = ljTransactionDataMapper.getLjTransactionData();
		if(liLjTransactionDatas == null || liLjTransactionDatas.size() <= 0) {
			return null;
		}
		
		return liLjTransactionDatas;
	}

	@Override
	public List<LjTransactionDataVO> getProblems() {
		
		return ljTransactionDataMapper.getProblems();
	}
}
