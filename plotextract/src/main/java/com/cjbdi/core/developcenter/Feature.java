package com.cjbdi.core.developcenter;

import com.cjbdi.core.configurecentent.extractfeature.sentence.DrunkDriving;
import com.cjbdi.core.configurecentent.extractfeature.sentence.IndividualIllegallyAbsorbingPublicDeposits;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Traffic;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.SentenceExtractor;
import com.cjbdi.core.extractcenter.sentence.drunkdriving.InitExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.PublicFeatureExtract;

import java.util.LinkedHashMap;
import java.util.List;

public class Feature {
    public static LinkedHashMap<String,LinkedHashMap<String, PublicFeatureExtract>> basicPureRuleExtractors = new LinkedHashMap<>();
//    public static LinkedHashMap<String, List<PublicFeatureExtract>> basicModelRuleExtractors = new LinkedHashMap<>();
    public static LinkedHashMap<String,List<BasicSentenceFeatureClass>> basicPrivateExtractors = new LinkedHashMap<>();
    public static LinkedHashMap<String, BasicCaseClass> basicCaseClass = new LinkedHashMap<>();
    static {
        InitExtractor initExtractor = SentenceExtractor.initExtractor.getDrunkDrivingExtractor().getInitExtractor();
        basicPrivateExtractors.put("危险驾驶罪（醉驾）",initExtractor.getBasicPrivateExtractors());
        basicPureRuleExtractors.put("危险驾驶罪（醉驾）",initExtractor.getBasicPureRuleExtractors());
        DrunkDriving drunkDriving = new DrunkDriving();
        basicCaseClass.put("危险驾驶罪（醉驾）",drunkDriving);

        com.cjbdi.core.extractcenter.sentence.individualillegallyabsorbingpublicdeposits.InitExtractor initExtractor1 = SentenceExtractor.initExtractor.getIndividualIllegallyAbsorbingPublicDepositsExtractor().getInitExtractor();
        basicPrivateExtractors.put("非法吸收公众存款罪（个人）",initExtractor1.getBasicPrivateExtractors());
        basicPureRuleExtractors.put("非法吸收公众存款罪（个人）",initExtractor.getBasicPureRuleExtractors());
        IndividualIllegallyAbsorbingPublicDeposits individualIllegallyAbsorbingPublicDeposits = new IndividualIllegallyAbsorbingPublicDeposits();
        basicCaseClass.put("非法吸收公众存款罪（个人）",individualIllegallyAbsorbingPublicDeposits);
    }

    public static Label extract(DefendantModel defendantModel, CasecauseModel casecauseModel, String extractorType, String extractorFrom, String code) {
        DefendantModel defendantModel1 = defendantModel;
        CasecauseModel casecauseModel1 = casecauseModel;
        if (extractorType.equals("私有")){
            if(extractorFrom.equals("本院认为")){
                casecauseModel.setJustice("");
                casecauseModel.setOpinion(casecauseModel.getOpinion().split("。")[0]);
                if (basicPrivateExtractors.get(casecauseModel.getCasecause()) != null){
                    for(BasicSentenceFeatureClass basicSentenceFeatureClass :basicPrivateExtractors.get(casecauseModel.getCasecause())){
                        Label label = basicSentenceFeatureClass.run(defendantModel,casecauseModel, basicCaseClass.get(casecauseModel.getCasecause()));
                        if (label != null &&  label.getFlag() == Long.valueOf(code)){
                            return label;
                        }
                    }

                }


            }else if(extractorFrom.equals("经审理查明")) {
                casecauseModel.setOpinion("");
                if (basicPrivateExtractors.get(casecauseModel.getCasecause()) != null){
                    for(BasicSentenceFeatureClass basicSentenceFeatureClass :basicPrivateExtractors.get(casecauseModel.getCasecause())){
                        Label label = basicSentenceFeatureClass.run(defendantModel,casecauseModel,basicCaseClass.get(casecauseModel.getCasecause()));
                        if (label != null &&  label.getFlag() == Long.valueOf(code)){
                            return label;
                        }
                    }

                }

            }

        }else if (extractorType.equals("共有纯正则")){
            if(extractorFrom == "本院认为"){

            }else if(extractorFrom == "经审理查明") {
                casecauseModel.setOpinion("");
            }

        }
        return null;
    }


}
