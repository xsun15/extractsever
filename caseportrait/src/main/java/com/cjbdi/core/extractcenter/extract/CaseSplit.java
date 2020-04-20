package com.cjbdi.core.extractcenter.extract;

import com.cjbdi.core.extractcenter.model.CasecauseModel;
import com.cjbdi.core.extractcenter.model.FirstTrialModel;
import com.cjbdi.core.extractcenter.model.IndicitmentModel;

public class CaseSplit {
    public static CasecauseModel run (IndicitmentModel indicitmentModel) {
        CasecauseModel casecauseModel = new CasecauseModel();
        casecauseModel.setDefendant(indicitmentModel.getDefendant());
        casecauseModel.setJustice(indicitmentModel.getJustice());
        casecauseModel.setAccuse(indicitmentModel.getInvestigate());
        casecauseModel.setOpinion(indicitmentModel.getProcuOpinion());
        casecauseModel.setJudgeDate(indicitmentModel.getJudgeDate());
        casecauseModel.setProvince(indicitmentModel.getProvince());
        return casecauseModel;
    }
    public static CasecauseModel run (FirstTrialModel firstTrialModel) {
        CasecauseModel casecauseModel = new CasecauseModel();
        casecauseModel.setDefendant(firstTrialModel.getDefendant());
        casecauseModel.setJustice(firstTrialModel.getJustice());
        casecauseModel.setAccuse(firstTrialModel.getAccuse());
        casecauseModel.setOpinion(firstTrialModel.getCourtOpinion());
        casecauseModel.setJudgeDate(firstTrialModel.getJudgeDate());
        casecauseModel.setProvince(firstTrialModel.getProvince());
        return casecauseModel;
    }

}
