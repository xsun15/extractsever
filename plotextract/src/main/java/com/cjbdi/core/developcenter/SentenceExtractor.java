package com.cjbdi.core.developcenter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.developcenter.money.MoneyExtractorModel;
import com.cjbdi.core.extractcenter.sentence.InitExtractor;
import com.cjbdi.core.extractcenter.sentence.common.money.MoneyConfig;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.CleanText;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.HttpRequest;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SentenceExtractor {
    private static Logger logger = LoggerFactory.getLogger(SentenceExtractor.class);
    private static InitExtractor initExtractor = new InitExtractor();


    public static JSONObject extract(String docType, String doc, List<String> cases,String purpose) {
        doc = CleanText.run(doc);
        JSONObject reqPara = new JSONObject();
        reqPara.put("docType", docType);
        reqPara.put("fullText", doc);
        String casePortrait = HttpRequest.sendPost(BeanFactoryConfig.interfaceConfig.getInterfacePortrait().getDocportray(), reqPara);
        String dfkasjf = HttpRequest.sendPost(BeanFactoryConfig.interfaceConfig.getInterfacePortrait().getDocsplit(), reqPara);
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
