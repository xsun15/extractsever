package com.cjbdi.core.developcenter;

import com.cjbdi.core.configurecentent.extractfeature.sentence.DrunkDriving;
import com.cjbdi.core.configurecentent.extractfeature.sentence.IndividualIllegallyAbsorbingPublicDeposits;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Traffic;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.SentenceExtractor;
import com.cjbdi.core.extractcenter.sentence.common.time.StringUtil;
import com.cjbdi.core.extractcenter.sentence.drunkdriving.InitExtractor;
import com.cjbdi.core.extractcenter.sentence.share.RecidivismExtractor;
import com.cjbdi.core.extractcenter.sentence.share.ShareExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.LabelExtractor;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.PublicFeatureExtract;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class Feature {
    public static LinkedHashMap<String,LinkedHashMap<String, PublicFeatureExtract>> basicPureRuleExtractors = new LinkedHashMap<>();
    public static LinkedHashMap<String, List<PublicFeatureExtract>> basicModelRuleExtractors = new LinkedHashMap<>();
    public static LinkedHashMap<String,List<BasicSentenceFeatureClass>> basicPrivateExtractors = new LinkedHashMap<>();
    public static LinkedHashMap<String, BasicCaseClass> basicCaseClass = new LinkedHashMap<>();
    public static LinkedHashMap<String, RecidivismExtractor> basicShareExtractors=new LinkedHashMap<>();
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


        com.cjbdi.core.extractcenter.sentence.share.InitExtractor initExtractor_share = new com.cjbdi.core.extractcenter.sentence.share.InitExtractor();
        basicShareExtractors.put("总则量刑情节", initExtractor_share.getRecidivismExtractor());
        com.cjbdi.core.extractcenter.sentence.traffic.InitExtractor initExtractor_traffic = new com.cjbdi.core.extractcenter.sentence.traffic.InitExtractor();
        basicPureRuleExtractors.put("交通肇事罪",initExtractor_traffic.getBasicPureRuleExtractors());
        basicPrivateExtractors.put("交通肇事罪",initExtractor_traffic.getBasicPrivateExtractors());
        com.cjbdi.core.extractcenter.sentence.steal.InitExtractor initExtractor_steal =new com.cjbdi.core.extractcenter.sentence.steal.InitExtractor();
        basicPureRuleExtractors.put("盗窃罪", initExtractor_steal.getBasicPureRuleExtractors());
        com.cjbdi.core.extractcenter.sentence.extortion.InitExtractor initExtractor_extortion = new com.cjbdi.core.extractcenter.sentence.extortion.InitExtractor();
        basicPureRuleExtractors.put("敲诈勒索罪", initExtractor_extortion.getBasicPureRuleExtractors());
        com.cjbdi.core.extractcenter.sentence.injury.InitExtractor initExtractor_injure = new com.cjbdi.core.extractcenter.sentence.injury.InitExtractor();
        basicPureRuleExtractors.put("故意伤害罪", initExtractor_injure.getBasicPureRuleExtractors());
        com.cjbdi.core.extractcenter.sentence.fraud.InitExtractor initExtractor_fraud = new com.cjbdi.core.extractcenter.sentence.fraud.InitExtractor();
        basicPureRuleExtractors.put("诈骗罪", initExtractor_fraud.getBasicPureRuleExtractors());
        com.cjbdi.core.extractcenter.sentence.findtrouble.InitExtractor initExtractor_findtrouble = new com.cjbdi.core.extractcenter.sentence.findtrouble.InitExtractor();
        basicPureRuleExtractors.put("寻衅滋事罪",initExtractor_findtrouble.getBasicPureRuleExtractors());
        com.cjbdi.core.extractcenter.sentence.robbery.InitExtractor initExtractor_robbery = new com.cjbdi.core.extractcenter.sentence.robbery.InitExtractor();
        basicPureRuleExtractors.put("抢劫罪",initExtractor_robbery.getBasicPureRuleExtractors());
        com.cjbdi.core.extractcenter.sentence.endangerpa.InitExtractor initExtractor_endangerpa = new com.cjbdi.core.extractcenter.sentence.endangerpa.InitExtractor();
        basicPureRuleExtractors.put("妨害公务罪", initExtractor_endangerpa.getBasicPureRuleExtractors());
        com.cjbdi.core.extractcenter.sentence.falseimprison.InitExtractor initExtractor_falseimprison = new com.cjbdi.core.extractcenter.sentence.falseimprison.InitExtractor();
        basicPureRuleExtractors.put("非法拘禁罪",initExtractor_falseimprison.getBasicPureRuleExtractors());
        com.cjbdi.core.extractcenter.sentence.affray.InitExtractor initExtractor_affray = new com.cjbdi.core.extractcenter.sentence.affray.InitExtractor();
        basicPureRuleExtractors.put("聚众斗殴罪", initExtractor_affray.getBasicPureRuleExtractors());
        com.cjbdi.core.extractcenter.sentence.seizing.InitExtractor initExtractor_seizing = new com.cjbdi.core.extractcenter.sentence.seizing.InitExtractor();
        basicPureRuleExtractors.put("抢夺罪", initExtractor_seizing.getBasicPureRuleExtractors());
        com.cjbdi.core.extractcenter.sentence.dutyencroachment.InitExtractor initExtractor_dutyencroachment = new com.cjbdi.core.extractcenter.sentence.dutyencroachment.InitExtractor();
        basicPureRuleExtractors.put("职务侵占罪",initExtractor_dutyencroachment.getBasicPureRuleExtractors());
        com.cjbdi.core.extractcenter.sentence.rape.InitExtractor initExtractor_rape = new com.cjbdi.core.extractcenter.sentence.rape.InitExtractor();
        basicPureRuleExtractors.put("强奸罪",initExtractor_rape.getBasicPureRuleExtractors());
        com.cjbdi.core.extractcenter.sentence.concealci.InitExtractor initExtractor_concealci = new com.cjbdi.core.extractcenter.sentence.concealci.InitExtractor();
        basicPureRuleExtractors.put("掩饰、隐瞒犯罪所得、犯罪所得收益罪",initExtractor_concealci.getBasicPureRuleExtractors());
    }

    public static Label extract(DefendantModel defendantModel, CasecauseModel casecauseModel, String extractorType, String extractorFrom, String code) {
        DefendantModel defendantModel1 = defendantModel;
        CasecauseModel casecauseModel1 = casecauseModel;
        if (extractorType.equals("私有")){
            if(extractorFrom.equals("本院认为")){
                casecauseModel.setJustice("");
                casecauseModel.setOpinion( Clean(casecauseModel.getOpinion().split("判决如下")[0]));//去除判决如下，并且去除不予采纳的内容。
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
    public static String Clean(String text){
        String key = "(更正|不予)";
        Pattern pattern =  Pattern.compile(key);
        String res = "";
        if (StringUtils.isNotEmpty(text)){
            String[] paragraphArray = text.split("。|；");
            for (int i = 0;i < paragraphArray.length; i++){
                if (!pattern.matcher(paragraphArray[i]).find()){
                    res += paragraphArray[i] + "。";
                }
            }

        }
        return res;
    }

    public static Label extractbool(DefendantModel defendantModel, CasecauseModel casecauseModel, String extractorType, String extractorFrom, String code,String Para) {
        Set<String> casecauseSet = defendantModel.getCasecauseSet();
        if (extractorType.equals("私有")) {
            if (extractorFrom.equals("本院认为")) {
                casecauseModel.setJustice("");


            } else if (extractorFrom.equals("经审理查明")) {
                casecauseModel.setOpinion("");

            }

        } else if (extractorType.equals("共有纯正则")) {
            if (extractorFrom.equals("本院认为")) {
                casecauseModel.setJustice("");
                if (basicPureRuleExtractors.get(casecauseModel.getCasecause()) != null) {
                    if (basicPureRuleExtractors.get(casecauseModel.getCasecause()).get(code) != null) {
//                    for(BasicSentenceFeatureClass basicSentenceFeatureClass :basicPrivateExtractors.get(casecauseModel.getCasecause())){
                        Label label = basicPureRuleExtractors.get(casecauseModel.getCasecause()).get(code).doExtract(casecauseModel.getOpinion());
                        if (label != null && label.getFlag() == Long.valueOf(code)) {
                            return label;
                        }

                    }
                }
            } else if (extractorFrom.equals("经审理查明")) {
                casecauseModel.setOpinion("");
                if (basicPureRuleExtractors.get(casecauseModel.getCasecause()) != null) {
                    if (basicPureRuleExtractors.get(casecauseModel.getCasecause()).get(code) != null) {
//                    for(BasicSentenceFeatureClass basicSentenceFeatureClass :basicPrivateExtractors.get(casecauseModel.getCasecause())){
                        Label label = basicPureRuleExtractors.get(casecauseModel.getCasecause()).get(code).doExtract(casecauseModel.getJustice());
                        if (label != null && label.getFlag() == Long.valueOf(code)) {
                            return label;
                        }
                    }
                }
            }
        }
        return null;
   }
    public static Label extractshare(DefendantModel defendantModel, CasecauseModel casecauseModel, String extractorType, String extractorFrom, String code,String Para) {
        Set<String> casecauseSet = defendantModel.getCasecauseSet();
        if (extractorType.equals("私有")) {
            if(extractorFrom.equals("本院认为")){
                casecauseModel.setDefendant("");
               Label label=basicShareExtractors.get("总则量刑情节").doextract(defendantModel, casecauseModel);
                if (label != null && label.getFlag() == Long.valueOf(code)) {
                    return label;
                }
            }else if(extractorFrom.equals("被告人段")){
                casecauseModel.setOpinion("");
                Label label=basicShareExtractors.get("总则量刑情节").doextract(defendantModel, casecauseModel);
                if (label != null && label.getFlag() == Long.valueOf(code)) {
                    return label;
                }
            }

//            if (extractorFrom.equals("本院认为")) {
//                casecauseModel.setJustice("");
//
//
//            } else if (extractorFrom.equals("经审理查明")) {
//                casecauseModel.setOpinion("");
//
//            }

        } else if (extractorType.equals("共有纯正则")) {
            if (extractorFrom.equals("本院认为")) {
                casecauseModel.setJustice("");
                if (basicPureRuleExtractors.get(casecauseModel.getCasecause()) != null) {
                    if (basicPureRuleExtractors.get(casecauseModel.getCasecause()).get(code) != null) {
//                    for(BasicSentenceFeatureClass basicSentenceFeatureClass :basicPrivateExtractors.get(casecauseModel.getCasecause())){
                        Label label = basicPureRuleExtractors.get(casecauseModel.getCasecause()).get(code).doExtract(casecauseModel.getOpinion());
                        if (label != null && label.getFlag() == Long.valueOf(code)) {
                            return label;
                        }

                    }
                }
            } else if (extractorFrom.equals("经审理查明")) {
                casecauseModel.setOpinion("");
                if (basicPureRuleExtractors.get(casecauseModel.getCasecause()) != null) {
                    if (basicPureRuleExtractors.get(casecauseModel.getCasecause()).get(code) != null) {
//                    for(BasicSentenceFeatureClass basicSentenceFeatureClass :basicPrivateExtractors.get(casecauseModel.getCasecause())){
                        Label label = basicPureRuleExtractors.get(casecauseModel.getCasecause()).get(code).doExtract(casecauseModel.getJustice());
                        if (label != null && label.getFlag() == Long.valueOf(code)) {
                            return label;
                        }
                    }
                }
            }
        }
        return null;
    }

}
