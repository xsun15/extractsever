package com.cjbdi.core.extractcenter.split;

import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.configcenter.structurateConfig.utils.IndicitOpinionBasicConfig;
import com.cjbdi.core.extractcenter.model.IndictOpinionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 起诉意见书
 * */
public class IndictOpinionSplit extends BasicSplit {
    private IndictOpinionModel indictOpinionModel;
    private IndicitOpinionBasicConfig indicitOpinionBasicConfig = BeanConfigCenter.structurateConfig.getIndicitOpinionConfig().getIndicitOpinion();

    public IndictOpinionModel split(String content) {
        indictOpinionModel = new IndictOpinionModel();
        List<Integer> partList = new ArrayList<>();
        List<String> contentList = docProc(content);
        // 根据正则构建案件画像
        partList.add(getParaIndex(contentList, indicitOpinionBasicConfig.getDefendant()));
        partList.add(getParaIndex(contentList, indicitOpinionBasicConfig.getExperience()));
        partList.add(getParaIndex(contentList, indicitOpinionBasicConfig.getInvestigate()));
        partList.add(getParaIndex(contentList, indicitOpinionBasicConfig.getJustice()));
        partList.add(getParaIndex(contentList, indicitOpinionBasicConfig.getProcuopinion()));
        partList.add(getParaIndex(contentList, indicitOpinionBasicConfig.getSalute()));
        partList.add(getParaIndex(contentList, indicitOpinionBasicConfig.getCourt()));
        partList.add(getParaIndex(contentList, indicitOpinionBasicConfig.getAppend()));
        partList.add(getParaIndex(contentList, indicitOpinionBasicConfig.getJudgeDate()));
        // 调整案件画像
        for (int i=0; i<partList.size(); i++) {
            for (int j=i+1; j<partList.size(); j++) {
                if (partList.get(i) > partList.get(j)) {
                    partList.set(i, partList.get(j));
                }
                if (partList.get(i) != -1) {
                    break;
                }
            }
        }
        // 得到案件画像内容
        indictOpinionModel.setDefendant(getPara(partList.get(0), partList.get(1), contentList));
        indictOpinionModel.setExperience(getPara(partList.get(1), partList.get(2), contentList));
        indictOpinionModel.setInvestigate(getPara(partList.get(2), partList.get(3), contentList));
        indictOpinionModel.setJustice(getPara(partList.get(3), partList.get(4), contentList));
        indictOpinionModel.setProcuOpinion(getPara(partList.get(4), partList.get(5), contentList));
        indictOpinionModel.setSalute(getPara(partList.get(5), partList.get(6), contentList));
        indictOpinionModel.setCourt(getPara(partList.get(6), partList.get(7), contentList));
        indictOpinionModel.setAppend(getPara(partList.get(8), partList.get(9), contentList));
        indictOpinionModel.setJudgeDate(getPara(partList.get(9), contentList.size(), contentList));

        return indictOpinionModel;
    }
}
