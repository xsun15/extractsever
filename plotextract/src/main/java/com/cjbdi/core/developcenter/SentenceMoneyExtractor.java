package com.cjbdi.core.developcenter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjbdi.ldet.configurecentent.BeanFactoryConfig;
import com.cjbdi.ldet.developcenter.money.MoneyExtractor;
import com.cjbdi.ldet.developcenter.money.MoneyExtractorModel;
import com.cjbdi.ldet.developcenter.utils.CommonTools;
import com.cjbdi.ldet.extractcenter.sentence.InitExtractor;
import com.cjbdi.ldet.extractcenter.sentence.utils.*;
import com.cjbdi.ldet.extractcenter.splitdocument.FactTextConfig;
import com.cjbdi.ldet.extractcenter.splitdocument.FactTextSplit;
import com.cjbdi.ldet.extractcenter.utils.CleanText;
import com.cjbdi.ldet.extractcenter.utils.HttpRequest;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.apache.commons.lang.StringUtils;

import java.time.LocalDate;
import java.util.*;

public class SentenceMoneyExtractor {
    private static Logger logger = LoggerFactory.getLogger(SentenceExtractor.class);

    public static JSONObject extract(String doc, List<String> cases, String purpose) {
        doc = CleanText.run(doc);
        JSONObject reqPara = new JSONObject();
        reqPara.put("docType", "刑事判决书");
        reqPara.put("fullText", doc);
        String paraSplitter = HttpRequest.sendPost(BeanFactoryConfig.portraitInterfaceConfig.getDocsplit(), reqPara);
        if (paraSplitter!=null&& StringUtils.isNotEmpty(paraSplitter)) {
            JSONObject paraSplitterJson = JSONObject.parseObject(paraSplitter);
            //从背景描述段落中抽取被告人
            String backgroundText = paraSplitterJson.getString("defendant");
            LinkedHashSet<String> defendantTable = ExtractDefendant.run(backgroundText);
            if (defendantTable == null || defendantTable.isEmpty()) {
                defendantTable = ExtractDefendant.run(doc);
            }
            if (defendantTable.size()>1) return null;
            // 对于一审判决书需要判断经审理查明段要不要添加公诉机关指控的内容
            String accuse = paraSplitterJson.getString("accuse");
            String justice = paraSplitterJson.getString("justice");
            justice = CommonTools.jointAccuseJustice(accuse, justice);
            //从犯罪记录描述中抽取犯罪记录
            for (String defendant : defendantTable) {
                for (String casecause : cases) {
                    Map<String, String> factOfPoeple = CasePortrait.run(justice, defendantTable, casecause, cases);
                    Optional<CaseEnum> caseOpt = CaseEnum.getCaseEnum(casecause);
                    if (!caseOpt.isPresent()) {
                        logger.error("No" + casecause + "Extractor found");
                        continue;
                    }
                    CaseEnum caseEnum = caseOpt.get();
                    //如果某个被告人没有参与某个案由，则不生成该被告人画像。
                    if (factOfPoeple.get(defendant) == null || factOfPoeple.get(defendant).length() == 0) {
                        continue;
                    }
                    //进行总分、分总、总分总特殊形式的处理。
                    CriminalActParaSplitter criminalActParaSplitter = new CriminalActParaSplitter();
                    CriminalActParaSplitter.CriminalActText criminalActText = criminalActParaSplitter.split(factOfPoeple.get(defendant));
                    //更加细化的处理（什么时间、什么地点、干了什么事）
                    List<CriminalAct> criminalActList = null;
                    TotalCriminalActionsExtractor totalCriminalActionsExtractor = new TotalCriminalActionsExtractor();
                    criminalActList = totalCriminalActionsExtractor.extract(defendant, criminalActText);
                    if (criminalActList == null)
                        continue;
                    PersonInfo personInfo = new PersonInfo(defendant);
                    FactTextConfig factTextConfig = FactTextSplit.run(factOfPoeple.get(defendant));
                    String header = factTextConfig.header;
                    String tail = factTextConfig.tail;
                    personInfo.factText = Optional.ofNullable(factTextConfig.body);
                    personInfo.factHeader = Optional.ofNullable(header);
                    personInfo.factHail = Optional.ofNullable(tail);
                    personInfo.casecause = casecause;
                    personInfo.allDenfendant = Optional.ofNullable(defendantTable);
                    /*抽取犯罪日期*/
                    String defendentConclusion = "";
                    if (defendantTable.size() == 1) {
                        personInfo.conclusionText = Optional.ofNullable(paraSplitterJson.getString("opinion"));
                    } else {
                        String[] strs = paraSplitterJson.getString("opinion").split("[；|。]");
                        for (String str : strs) {
                            if (str.contains(defendant)) {
                                defendentConclusion += str;
                            } else {
                                boolean shareCheck = false;
                                for (String def : defendantTable) {
                                    if (str.contains(def)) {
                                        shareCheck = true;
                                    }
                                }
                                if (!shareCheck) {
                                    defendentConclusion += str;
                                }
                            }
                        }
                        personInfo.conclusionText = Optional.ofNullable(defendentConclusion);
                    }
                    String factTextHasEvidence1 = justice;
                    String casecauseFactDeleteEvidence = CasePortrait.deleteEvidence(factTextHasEvidence1, casecause);
                    RawCrimeInfo rawCrimeInfo = new RawCrimeInfo(personInfo, caseEnum, casecauseFactDeleteEvidence);
                    if (purpose.equals("自动标注")) {
                        JSONObject result = MoneyExtractor.extractSummaryMoney(rawCrimeInfo);
                        return result;
                    }  else if (purpose.equals("排列组合总计")) {
                        JSONObject result = MoneyExtractor.combineValidInvalidAllPaper(rawCrimeInfo);
                        return result;
                    } else if (purpose.equals("排列组合深度")) {
                        JSONObject result = MoneyExtractor.combineValidInvalidAll(rawCrimeInfo);
                        return result;
                    }
                }
            }
                }
        return null;
    }
}
