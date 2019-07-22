
package com._360pai.core.dao.account.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TPersonaDisposalPartyCondition;
import com._360pai.core.dao.account.TPersonaDisposalPartyDao;
import com._360pai.core.dao.account.mapper.TPersonaDisposalPartyMapper;
import com._360pai.core.model.account.TPersonaDisposalParty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TPersonaDisposalPartyDaoImpl extends AbstractDaoImpl<TPersonaDisposalParty, TPersonaDisposalPartyCondition, BaseMapper<TPersonaDisposalParty,TPersonaDisposalPartyCondition>> implements TPersonaDisposalPartyDao {
	
	@Resource
	private TPersonaDisposalPartyMapper tPersonaDisposalPartyMapper;
	
	@Override
	protected BaseMapper<TPersonaDisposalParty, TPersonaDisposalPartyCondition> daoSupport() {
		return tPersonaDisposalPartyMapper;
	}

	@Override
	protected TPersonaDisposalPartyCondition blankCondition() {
		return new TPersonaDisposalPartyCondition();
	}

	@Override
	public TPersonaDisposalParty getByPersonaId(Integer personaId) {
		TPersonaDisposalPartyCondition condition = new TPersonaDisposalPartyCondition();
		condition.setPersonaId(personaId);
		List<TPersonaDisposalParty> list = tPersonaDisposalPartyMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
