package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.ListResp;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.account.BankAccountCondition;
import com._360pai.core.dao.account.BankAccountDao;
import com._360pai.core.dao.assistant.BankDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.BankAccountReq;
import com._360pai.core.facade.account.resp.BankAccountResp;
import com._360pai.core.facade.account.vo.BankAccountVo;
import com._360pai.core.facade.assistant.vo.BankVo;
import com._360pai.core.model.account.BankAccount;
import com._360pai.core.model.assistant.Bank;
import com._360pai.core.service.assistant.BankService;
import com._360pai.core.utils.ReqConvertUtil;
import com._360pai.core.utils.RespConvertUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankServiceImpl	implements BankService{

	@Autowired
	private BankDao bankDao;
	@Autowired
	private BankAccountDao bankAccountDao;


	@Override
	public ListResp<BankVo> getAllBanks() {
		ListResp<BankVo> resp = new ListResp<>();
		List<Bank> list = bankDao.selectAll();
		List<BankVo> resultList = new ArrayList<>();
		for (Bank bank : list) {
			BankVo vo = new BankVo();
			BeanUtils.copyProperties(bank, vo);
			vo.setValue(vo.getId() + "");
			resultList.add(vo);
		}
		resp.setList(resultList);
		return resp;
	}

	@Override
	public ListResp<BankAccountVo> getBankAccounts(BankAccountReq.BaseReq req) {
		ListResp<BankAccountVo> resp = new ListResp<>();
		BankAccountCondition condition = new BankAccountCondition();
		condition.setPartyId(req.getPartyId());
		List<BankAccount> list = bankAccountDao.selectList(condition);
		List<BankAccountVo> resultList = new ArrayList<>();
		for (BankAccount bankAccount : list) {
			BankAccountVo vo = new BankAccountVo();
			vo.setId(bankAccount.getId());
			vo.setName(bankAccount.getName());
			vo.setNumber(bankAccount.getNumber());
			vo.setBank(RespConvertUtil.convertToBankVo(bankDao.selectById(bankAccount.getBankId())));
			resultList.add(vo);
		}
		resp.setList(resultList);
		return resp;
	}

	@Override
	public BankAccountResp addBankAccount(BankAccountReq.CreateReq req) {
		BankAccountResp resp = new BankAccountResp();
		List<BankAccount> bankAccounts = bankAccountDao.getByPartyId(req.getPartyId());
		if (bankAccounts.size() > 0) {
			throw new BusinessException("已经绑定银行卡");
		}
		BankAccountCondition condition = new BankAccountCondition();
		condition.setPartyId(req.getPartyId());
		condition.setNumber(req.getNumber());
		List<BankAccount> list = bankAccountDao.selectList(condition);
		if (list.size() > 0) {
			throw new BusinessException("该卡号已经绑定");
		}
		Bank bank = bankDao.selectById(req.getBankId());
		if (bank == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID, "bankId");
		}
		BankAccount bankAccount = ReqConvertUtil.convertToBankAccount(req);
		int result = bankAccountDao.insert(bankAccount);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		return resp;
	}

	@Override
	public BankAccountResp updateBankAccount(BankAccountReq.UpdateReq req) {
		BankAccountResp resp = new BankAccountResp();
		BankAccount bankAccount = bankAccountDao.selectById(req.getId());
		if (bankAccount == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID, "id");
		}
		if (!bankAccount.getPartyId().equals(req.getPartyId())) {
			throw new BusinessException("不能修改其他用户的银行账号");
		}
		BankAccountCondition condition = new BankAccountCondition();
		condition.setPartyId(req.getPartyId());
		condition.setNumber(req.getNumber());
		BankAccount bankAccount2 = bankAccountDao.selectFirst(condition);
		if (bankAccount2 != null && bankAccount2.getId().equals(bankAccount.getId())) {
			throw new BusinessException("该卡号已经绑定");
		}
		Bank bank = bankDao.selectById(req.getBankId());
		if (bank == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID, "bankId");
		}
		bankAccount = ReqConvertUtil.convertToBankAccount(req);
		int result = bankAccountDao.updateById(bankAccount);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		return resp;
	}

	@Override
	public BankAccountResp deleteBankAccount(BankAccountReq.BaseReq req) {
		BankAccountResp resp = new BankAccountResp();
		BankAccount bankAccount = bankAccountDao.selectById(req.getId());
		if (bankAccount == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID, "id");
		}
		if (!bankAccount.getPartyId().equals(req.getPartyId())) {
			throw new BusinessException("不能修改其他用户的银行账号");
		}
		int result = bankAccountDao.deleteById(bankAccount.getId());
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		return resp;
	}
}