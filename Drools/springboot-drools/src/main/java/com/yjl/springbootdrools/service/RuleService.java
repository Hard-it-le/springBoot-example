package com.yjl.springbootdrools.service;

import com.yjl.springbootdrools.entity.Calculation;
import com.yjl.springbootdrools.entity.CreditCardApplyInfo;
import com.yjl.springbootdrools.entity.InsuranceInfo;
import com.yjl.springbootdrools.utils.KieSessionUtils;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yujiale
 */
@Service
public class RuleService {
    @Resource
    private KieBase kieBase;

    public void rule() {
        KieSession kieSession = kieBase.newKieSession();
        kieSession.fireAllRules();
        kieSession.dispose();
    }


    public List<String> insuranceInfoCheck(InsuranceInfo insuranceInfo) throws Exception{
        KieSession session = KieSessionUtils.getKieSessionFromXLS("D:\\insuranceInfoCheck.xls");
        session.getAgenda().getAgendaGroup("sign").setFocus();

        session.insert(insuranceInfo);

        List<String> listRules = new ArrayList<>();
        session.setGlobal("listRules", listRules);

        session.fireAllRules();
        session.dispose();

        return listRules;
    }

    public Calculation calculate(Calculation calculation){
        KieSession session = kieBase.newKieSession();
        session.insert(calculation);
        session.fireAllRules();
        session.dispose();
        return calculation;
    }

    public CreditCardApplyInfo creditCardApply(CreditCardApplyInfo creditCardApplyInfo){
        KieSession session = kieBase.newKieSession();
        session.insert(creditCardApplyInfo);
        session.fireAllRules();
        session.dispose();
        return creditCardApplyInfo;
    }
}
