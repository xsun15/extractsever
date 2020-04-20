package com.cjbdi.core.extractcenter.split;

import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.configcenter.structurateConfig.utils.NoprosBasicConfig;
import com.cjbdi.core.extractcenter.model.NoprosModel;

import java.util.ArrayList;
import java.util.List;

public class NoprosSplit extends BasicSplit {
    private NoprosModel noprosModel;
    private NoprosBasicConfig noprosBasicConfig = BeanConfigCenter.structurateConfig.getNoprosConfig().getNopros();

    public NoprosModel split(String content) {
        List<Integer> partList = new ArrayList<>();
        List<String> contentList = docProc(content);
        noprosModel = new NoprosModel();
        noprosModel.setProcuName(contentList.get(0));
        noprosModel.setDocType(contentList.get(1));
        noprosModel.setProcuCaseId(contentList.get(2));
        // 根据正则构建案件画像
        partList.add(getParaIndex(contentList, noprosBasicConfig.getDefendant()));
        partList.add(getParaIndex(contentList, noprosBasicConfig.getInvestigate()));
        partList.add(getParaIndex(contentList, noprosBasicConfig.getJustice()));
        partList.add(getParaIndex(contentList, noprosBasicConfig.getProcuopinion()));
        partList.add(getParaIndex(contentList, noprosBasicConfig.getDefendantappeal()));
        partList.add(getParaIndex(contentList, noprosBasicConfig.getVictimappeal()));
        partList.add(getParaIndex(contentList, noprosBasicConfig.getProcuratorate()));
        partList.add(getParaIndex(contentList, noprosBasicConfig.getJudgeDate()));
        partList.add(getParaIndex(contentList, noprosBasicConfig.getAppend()));
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
        noprosModel.setDefendant(getPara(partList.get(0), partList.get(1), contentList));
        noprosModel.setInvestigate(getPara(partList.get(1), partList.get(2), contentList));
        noprosModel.setJustice(getPara(partList.get(2), partList.get(3), contentList));
        noprosModel.setProcuOpinion(getPara(partList.get(3), partList.get(4), contentList));
        noprosModel.setDefendantappeal(getPara(partList.get(4), partList.get(5), contentList));
        noprosModel.setVictimappeal(getPara(partList.get(5), partList.get(6), contentList));
        noprosModel.setProcuratorate(getPara(partList.get(6), partList.get(7), contentList));
        noprosModel.setJudgeDate(getPara(partList.get(7), partList.get(8), contentList));
        noprosModel.setAppend(getPara(partList.get(8), contentList.size(), contentList));
        // 将证据从事实段剥离
        return noprosModel;
    }
}
