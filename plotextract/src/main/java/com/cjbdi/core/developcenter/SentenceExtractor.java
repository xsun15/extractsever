package com.cjbdi.core.developcenter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjbdi.ldet.configurecentent.BeanFactoryConfig;
import com.cjbdi.ldet.developcenter.factcrime.FactCrime;
import com.cjbdi.ldet.developcenter.money.ExtractMoney;
import com.cjbdi.ldet.developcenter.money.MoneyExtractorModel;
import com.cjbdi.ldet.extractcenter.checklabel.CheckLabel;
import com.cjbdi.ldet.extractcenter.sentence.InitExtractor;
import com.cjbdi.ldet.extractcenter.sentence.common.money.MoneyConfig;
import com.cjbdi.ldet.extractcenter.sentence.common.money.MoneyExtractor;
import com.cjbdi.ldet.extractcenter.sentence.utils.*;
import com.cjbdi.ldet.extractcenter.splitdocument.FactTextConfig;
import com.cjbdi.ldet.extractcenter.splitdocument.FactTextSplit;
import com.cjbdi.ldet.extractcenter.utils.*;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.nullness.Opt;

import java.time.LocalDate;
import java.util.*;

public class SentenceExtractor {
    private static Logger logger = LoggerFactory.getLogger(SentenceExtractor.class);
    private static InitExtractor initExtractor = new InitExtractor();


    public static JSONObject extract(String docType, String doc, List<String> cases,String purpose) {
        doc = CleanText.run(doc);
        JSONObject reqPara = new JSONObject();
        reqPara.put("docType", docType);
        reqPara.put("fullText", doc);
        String casePortrait = HttpRequest.sendPost(BeanFactoryConfig.portraitInterfaceConfig.getDocportray(), reqPara);
        String dfkasjf = HttpRequest.sendPost(BeanFactoryConfig.portraitInterfaceConfig.getDocsplit(), reqPara);
        if (StringUtils.isNotEmpty(casePortrait)) {
            JSONArray casePortraitJson = JSONArray.parseArray(casePortrait);
            JSONArray caseDeepPortrait = new JSONArray();
            MoneyExtractorModel moneyExtractorModel = new MoneyExtractorModel();
            for (int i=0; i<casePortraitJson.size(); i++) {
                JSONObject defendantPortray = casePortraitJson.getJSONObject(i);
                DefendantModel defendantModel = new DefendantModel(defendantPortray);
                if (defendantModel.getDefendantNameSet()==null || defendantModel.getDefendantNameSet().size()!=1) return null;
                if (defendantModel.getCasecauseSet()==null || defendantModel.getCasecauseSet().size()!=1) return null;
                String defendantName = defendantModel.getName();
                Set<String> casecauseSet = defendantModel.getCasecauseSet();
                Map<String, CasecauseModel> casecauseModelMap = defendantModel.getCasecauseModelMap();
                JSONArray completeFetaures = new JSONArray();
                for (String casecause : casecauseSet) {
                    CasecauseModel casecauseModel = casecauseModelMap.get(casecause);
                    JSONObject money = moneyExtractorModel.run(defendantModel, casecauseModel,purpose);
                    if (money!=null&&!money.isEmpty()) return money;
                }
            }
        }
        return null;
    }

    public static List<MoneyConfig> addMoneyPositionOffset(List<MoneyConfig> money, int offset) {
        if (money!=null) {
            for(int i=0; i<money.size(); i++) {
                MoneyConfig moneyConfig = money.get(i);
                moneyConfig.startColor += offset;
                moneyConfig.endColor += offset;
                money.set(i, moneyConfig);
            }
        }
        return money;
    }

    public static MoneyConfig addMoneyPositionOffset(MoneyConfig money, int offset) {
        if (money!=null) {
            money.startColor += offset;
            money.endColor += offset;
        }
        return money;
    }
}
