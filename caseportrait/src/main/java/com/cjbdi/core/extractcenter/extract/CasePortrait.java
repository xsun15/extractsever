package com.cjbdi.core.extractcenter.extract;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.extractcenter.model.*;
import com.cjbdi.core.util.CommonTools;
import com.cjbdi.core.util.HttpRequest;
import org.apache.commons.lang.StringUtils;

import java.util.*;


public class CasePortrait {
    public static JudgmentModel shallowRun(List<DefendantModel> defendantModelList, IndicitmentModel indicitmentModel) {
        JudgmentModel judgmentModel = new JudgmentModel();
        judgmentModel.setDocType(indicitmentModel.getDocType());
        judgmentModel.setCaseID(indicitmentModel.getProcuCaseId());
        judgmentModel.setCaseTitle("");
        judgmentModel.setProsecutionOrgan(indicitmentModel.getProcuName());
        List<JudgmentPaperModel> judgmentPaperModelList = new ArrayList<>();
        for (DefendantModel defendantModel : defendantModelList) {
            Map<String, CasecauseModel> casecauseModelMap = defendantModel.getCasecauseModelMap();
            JudgmentPaperModel judgmentPaperModel = new JudgmentPaperModel();
            judgmentPaperModel.setAccusedName(defendantModel.getName());
            List<JudgmentPaperContentModel> contentList = new ArrayList<>();
            for (String casecause : casecauseModelMap.keySet()) {
                JudgmentPaperContentModel judgmentPaperContentModel = new JudgmentPaperContentModel();
                judgmentPaperContentModel.setCaseCause(casecause);
                judgmentPaperContentModel.setBasicContent(indicitmentModel.getJustice());
                judgmentPaperContentModel.setEvidence(indicitmentModel.getEvidence());
                judgmentPaperContentModel.setAdvice("");
                judgmentPaperContentModel.setCounselOfDefendant("");
                judgmentPaperContentModel.setCounselOfVictim("");
                judgmentPaperContentModel.setAppraiser("");
                judgmentPaperContentModel.setTranslator("");
                judgmentPaperContentModel.setVictim("");
                judgmentPaperContentModel.setWitness("");
                judgmentPaperContentModel.setLegalOfDefendant("");
                judgmentPaperContentModel.setLegalOfVictim("");
                contentList.add(judgmentPaperContentModel);
            }
            judgmentPaperModel.setContentList(contentList);
            judgmentPaperModelList.add(judgmentPaperModel);
        }
        judgmentModel.setPaper(judgmentPaperModelList);
        return judgmentModel;
    }

    public static JudgmentModel shallowRun(List<DefendantModel> defendantModelList, FirstTrialModel firstTrialModel) {
        JudgmentModel judgmentModel = new JudgmentModel();
        judgmentModel.setDocType(firstTrialModel.getDocType());
        judgmentModel.setCaseID(firstTrialModel.getCourtCaseId());
        judgmentModel.setCaseTitle("");
        judgmentModel.setProsecutionOrgan(firstTrialModel.getProcuName());
        List<JudgmentPaperModel> judgmentPaperModelList = new ArrayList<>();
        for (DefendantModel defendantModel : defendantModelList) {
            Map<String, CasecauseModel> casecauseModelMap = defendantModel.getCasecauseModelMap();
            JudgmentPaperModel judgmentPaperModel = new JudgmentPaperModel();
            judgmentPaperModel.setAccusedName(defendantModel.getName());
            List<JudgmentPaperContentModel> contentList = new ArrayList<>();
            for (String casecause : casecauseModelMap.keySet()) {
                JudgmentPaperContentModel judgmentPaperContentModel = new JudgmentPaperContentModel();
                judgmentPaperContentModel.setCaseCause(casecause);
                judgmentPaperContentModel.setBasicContent(firstTrialModel.getJustice());
                judgmentPaperContentModel.setEvidence(firstTrialModel.getEvidence());
                judgmentPaperContentModel.setAdvice("");
                judgmentPaperContentModel.setCounselOfDefendant("");
                judgmentPaperContentModel.setCounselOfVictim("");
                judgmentPaperContentModel.setAppraiser("");
                judgmentPaperContentModel.setTranslator("");
                judgmentPaperContentModel.setVictim("");
                judgmentPaperContentModel.setWitness("");
                judgmentPaperContentModel.setLegalOfDefendant("");
                judgmentPaperContentModel.setLegalOfVictim("");
                contentList.add(judgmentPaperContentModel);
            }
            judgmentPaperModel.setContentList(contentList);
            judgmentPaperModelList.add(judgmentPaperModel);
        }
        judgmentModel.setPaper(judgmentPaperModelList);
        return judgmentModel;
    }

    public static List<DefendantModel> run(String conclusion, String factText, String accuse, String transfer, String background, Set<String> defendantSet, List<String> caseList) {
        List<DefendantModel> defendantModelList = defendantCasecause(conclusion, transfer, background, defendantSet, caseList);
        if (CommonTools.isJoinJusticeAccuse(factText, accuse)) factText = accuse + factText;
        Map<String, CasecausePortray> casecauseJusticeFactMap = casecauseCrimeFact(factText, caseList);
        Map<String, CasecausePortray> casecauseAccuseFactMap = casecauseCrimeFact(accuse, caseList);
        defendantModelList = defendantCasecauseJusticeFact(defendantModelList, casecauseJusticeFactMap, defendantSet);
        defendantModelList = defendantCasecauseAccuseFact(defendantModelList, casecauseAccuseFactMap, defendantSet);
        defendantModelList = defendantPortray(defendantModelList, background,transfer, conclusion);
        return defendantModelList;
    }

    // 被告人－罪名－案件其他段落组合
    public static List<DefendantModel> defendantPortray(List<DefendantModel> defendantModelList, String background, String transfer, String conclusion) {
        if (StringUtils.isNotEmpty(background) && defendantModelList!=null) {
            List<String> backgroundList = Arrays.asList(background.split("\n"));
            for (DefendantModel defendantModel : defendantModelList) {
                String defendant = defendantModel.getName();
                for (String casecause : defendantModel.getCasecauseModelMap().keySet()) {
                    String defendantBackground = "";
                    for (String line : backgroundList) {
                        if (line.contains(defendant)) {
                            defendantBackground += line + "\n";
                        }
                    }
                    defendantModel.getCasecauseModelMap().get(casecause).setDefendant(defendantBackground);
                    defendantModel.getCasecauseModelMap().get(casecause).setTransfer(transfer);
                    defendantModel.getCasecauseModelMap().get(casecause).setOpinion(conclusion);
                }
            }
        }
        return defendantModelList;
    }

    // 被告人－罪名－事实组合
    public static List<DefendantModel> defendantCasecauseJusticeFact(List<DefendantModel> defendantModelList, Map<String, CasecausePortray> casecausePortrayMap, Set<String> defendantSet) {
        if (casecausePortrayMap==null || defendantModelList==null|| casecausePortrayMap.isEmpty()||defendantModelList.isEmpty()) {
            return defendantModelList;
        } else {
            for (DefendantModel defendantModel : defendantModelList) {
                String defendant = defendantModel.getName();
                for (String casecause : casecausePortrayMap.keySet()) {
                    if (defendantModel.getCasecauseSet().contains(casecause)) {
                        CasecausePortray casecausePortray = casecausePortrayMap.get(casecause);
                        String factText = "";
                        List<String> factbodyList = Arrays.asList(casecausePortray.getFactbody().split("\n"));
                        for (String line : factbodyList) {
                            if (line.contains(defendant))
                                factText += line + "\n";
                            else if (!CommonTools.isContain(line, defendantSet))
                                factText += line + "\n";
                        }
                        if (StringUtils.isNotEmpty(factText)) {
                            CasecauseModel casecauseModel = defendantModel.getCasecauseModelMap().get(casecause);
                            casecauseModel.setJustice(factText);
                        }
                    }
                }
            }
            return defendantModelList;
        }
    }

    // 被告人－罪名－事实组合
    public static List<DefendantModel> defendantCasecauseAccuseFact(List<DefendantModel> defendantModelList, Map<String, CasecausePortray> casecausePortrayMap, Set<String> defendantSet) {
        if (casecausePortrayMap.isEmpty()||defendantModelList.isEmpty()) {
            return defendantModelList;
        } else {
            for (DefendantModel defendantModel : defendantModelList) {
                String defendant = defendantModel.getName();
                for (String casecause : casecausePortrayMap.keySet()) {
                    if (defendantModel.getCasecauseSet().contains(casecause)) {
                        CasecausePortray casecausePortray = casecausePortrayMap.get(casecause);
                        String factText = "";
                        List<String> factbodyList = Arrays.asList(casecausePortray.getFactbody().split("\n"));
                        for (String line : factbodyList) {
                            if (line.contains(defendant))
                                factText += line + "\n";
                            else if (!CommonTools.isContain(line, defendantSet))
                                factText += line + "\n";
                        }
                        if (StringUtils.isNotEmpty(factText)) {
                            CasecauseModel casecauseModel = defendantModel.getCasecauseModelMap().get(casecause);
                            casecauseModel.setAccuse(factText);
                        }
                    }
                }
            }
            return defendantModelList;
        }
    }

    // 被告人所犯罪名挂靠
    public static List<DefendantModel> defendantCasecause(String conclusion, String tansfer, String background, Set<String> defendantSet, List<String> caseList) {
        if (!caseList.isEmpty()&&!defendantSet.isEmpty()) {
            // 从本院认为段进行被告人－案由挂靠
            if (StringUtils.isNotEmpty(conclusion)) {
                List<DefendantModel> defendantModelList = defendantPortray(conclusion, defendantSet, caseList);
                if (!defendantModelList.isEmpty()) return defendantModelList;
            }
            // 从移交段进行被告人－案由挂靠
            if (StringUtils.isNotEmpty(tansfer)) {
                List<DefendantModel> defendantModelList = defendantPortray(tansfer, defendantSet, caseList);
                if (!defendantModelList.isEmpty()) return defendantModelList;
            }
            // 从被告人段进行被告人－案由挂靠
            if (StringUtils.isNotEmpty(background)) {
                List<DefendantModel> defendantModelList = new ArrayList<>();
                List<String> backgroundList = Arrays.asList(background.split("\n"));
                for (String line : backgroundList) {
                    if (line.contains("涉嫌")) {
                        line = line.substring(0, line.indexOf("涉嫌"));
                        List<DefendantModel> defendantModels = defendantPortray(line, defendantSet, caseList);
                        if (!defendantModels.isEmpty()) defendantModelList.addAll(defendantModels);
                    }
                }
                if (!defendantModelList.isEmpty()) return defendantModelList;
            }
            // 如果都没有则将所有罪名挂到所有被告人头上
            List<DefendantModel> defendantModelList = new ArrayList<>();
            for (String defendant : defendantSet) {
                Map<String, CasecauseModel> casecauseModelMap = new HashMap<>();
                for (String casecause : caseList) {
                    CasecauseModel casecauseModel = new CasecauseModel();
                    casecauseModel.setCasecause(casecause);
                    casecauseModelMap.put(casecause, casecauseModel);
                }
                DefendantModel defendantModel = new DefendantModel();
                defendantModel.setName(defendant);
                defendantModel.setCasecauseSet(new HashSet<>(caseList));
                defendantModel.setNameSet(defendantSet);
                defendantModel.setCasecauseModelMap(casecauseModelMap);
                defendantModelList.add(defendantModel);
            }
            return defendantModelList;
        }
        return null;
    }

    // 罪名－犯罪事实挂靠
    public static Map<String, CasecausePortray> casecauseCrimeFact (String factText, List<String> caseList) {
        Map<String, CasecausePortray> casecausePortrayMap= new HashMap<>();
        if (StringUtils.isNotEmpty(factText) && !caseList.isEmpty()) {
            List<String> factList = Arrays.asList(factText.split("\n"));
            if (caseList.size() == 1) {
                // 单案由文书案由－事实挂靠
                CasecausePortray casecausePortray = new CasecausePortray();
                casecausePortray.set(caseList.get(0), factText);
                casecausePortrayMap.put(caseList.get(0), casecausePortray);
                return casecausePortrayMap;
            } else {
                List<String> headerrule = BeanConfigCenter.extractConfig.getPortrayConfig().getPortrayBasicConfig().getHeader();
                List<String> casecauserule = BeanConfigCenter.extractConfig.getPortrayConfig().getPortrayBasicConfig().getCasecauserule();
                // 规整多案由文书案由－事实挂靠
                for (String casecause : caseList) {
                    String placeholder = casecause.replaceAll("罪","");
                    if (casecause.contains("掩饰")) {
                        placeholder = "(掩饰|隐瞒)";
                    } else if (casecause.contains("毒品")) {
                        placeholder = "(走私|贩卖|运输|制造)";
                    }
                    casecauserule = CommonTools.replaceHolder(casecauserule, placeholder);
                    String casecauseFactText = "";
                    boolean flag=false;
                    for (String line : factList) {
                        if (CommonTools.ismatch(line, headerrule)) casecauseFactText += line + "\n";
                        else if (CommonTools.ismatch(line, casecauserule)) flag = true;
                        else if (flag) flag = false;
                        if (flag) {
                            casecauseFactText = casecauseFactText + line + "\n";
                        }
                    }
                    CasecausePortray casecausePortray = new CasecausePortray();
                    casecausePortray.set(casecause, casecauseFactText);
                    casecausePortrayMap.put(casecause, casecausePortray);
                }
                // 不规整多案由文书案由－事实挂靠
                if (!casecausePortrayMap.isEmpty()) {
                    LinkedHashMap<String, String> map = new LinkedHashMap<>();
                    for (String line : factList) {
                        if (CommonTools.ismatch(line, headerrule)) {
                            for (String casecause : caseList) {
                                if (map.containsKey(casecause)) {
                                    map.put(casecause, map.get(casecause) + line + "\n");
                                } else map.put(casecause, line + "\n");
                            }
                        } else {
                            JSONObject reqParm = new JSONObject();
                            reqParm.put("content", line);
                            String result = HttpRequest.sendPost(BeanConfigCenter.interfaceConfig.getInterfaceModel().getPredictcaselistrmdl(), reqParm);
                            int start = 0;
                            String predcase = "";
                            for (String value : caseList) {
                                if (result.indexOf(value) > 0 && result.indexOf(value) > start) {
                                    start = result.indexOf(value);
                                    predcase = value;
                                }
                            }
                            if (map.containsKey(predcase)) {
                                map.put(predcase, map.get(predcase) + line + "\n");
                            } else map.put(predcase, line + "\n");
                        }
                    }
                    for (String casecause : map.keySet()) {
                        CasecausePortray casecausePortray = new CasecausePortray();
                        String casecauseFactText = map.get(casecause);
                        casecausePortray.set(casecause, casecauseFactText);
                        casecausePortrayMap.put(casecause, casecausePortray);
                    }
                }
                return casecausePortrayMap;
            }
        } else {
            return casecausePortrayMap;
        }
    }

    // 根据被告人－罪名关联，创建案件－被告人画像对象列表
    public static List<DefendantModel> defendantPortray(String content, Set<String> defendantSet, List<String> caseList) {
        LinkedHashMap<String, Integer> contenSplitList = CommonTools.splitByCasecause(content, caseList);
        if (!defendantSet.isEmpty()) {
            List<DefendantModel> defendantModelList = new ArrayList<>();
            for (String defendant : defendantSet) {
                Set<String> defendantCasecause = new HashSet<>();
                List<Integer> indexList = new ArrayList<>();
                for (String casecause : contenSplitList.keySet()) {
                    indexList.add(contenSplitList.get(casecause));
                }
                for (String casecause : contenSplitList.keySet()) {
                    int index = contenSplitList.get(casecause);
                    if (CommonTools.findDefendant(content,index,defendant,indexList,defendantSet)) {
                        defendantCasecause.add(casecause);
                    }
                }
                if (!defendantCasecause.isEmpty()) {
                    DefendantModel defendantModel = new DefendantModel();
                    defendantModel.setName(defendant);
                    defendantModel.setCasecauseSet(defendantCasecause);
                    Map<String, CasecauseModel> casecauseModelMap = new HashMap<>();
                    for (String casecause : defendantCasecause) {
                        CasecauseModel casecauseModel = new CasecauseModel();
                        casecauseModel.setCasecause(casecause);
                        casecauseModelMap.put(casecause, casecauseModel);
                    }
                    defendantModel.setCasecauseModelMap(casecauseModelMap);
                    defendantModel.setNameSet(defendantSet);
                    defendantModelList.add(defendantModel);
                }
            }
            return defendantModelList;
        }
        return null;
    }
}
