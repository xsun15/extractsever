package com.cjbdi.core.extractcenter.split;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.configcenter.extractconfig.cascause.CasecauseBasicConfig;
import com.cjbdi.core.util.CommonTools;
import com.cjbdi.core.util.HttpRequest;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasicSplit {

    public String extractProvince(String  content, String fullText) {
        if (StringUtils.isNotEmpty(content)) {
            // 先按照省份搜索
            for (String province: BeanConfigCenter.extractConfig.getProvinceConfig().getProvince().keySet()) {
               List<String> rule = BeanConfigCenter.extractConfig.getProvinceConfig().getProvince().get(province);
               if (CommonTools.ismatch(content, rule)) {
                   return province;
               }
            }
            // 再按照市搜索
            for (String province: BeanConfigCenter.extractConfig.getProvinceConfig().getCity().keySet()) {
                List<String> rule = BeanConfigCenter.extractConfig.getProvinceConfig().getCity().get(province);
                if (CommonTools.ismatch(content, rule)) {
                    return province;
                }
            }
            // 最后根据县区搜索
            for (String province: BeanConfigCenter.extractConfig.getProvinceConfig().getCounty().keySet()) {
                List<String> rule = BeanConfigCenter.extractConfig.getProvinceConfig().getCounty().get(province);
                if (CommonTools.ismatch(content, rule) && StringUtils.isNotEmpty(fullText) && fullText.contains(province)) {
                    return province;
                }
            }
        }
        return null;
    }

    public String extractCasetype(String  content) {
        if (StringUtils.isNotEmpty(content)) {
            if (content.contains("检察院") || content.contains("公诉机关")) {
                return "刑事";
            } else if (content.contains("民事起诉") || content.contains("民事诉讼法")) {
                return "民事";
            } else if (content.contains("行政起诉") || content.contains("行政诉讼法")) {
                return "行政";
            } else {
                return "其它";
            }
        }
        return null;
    }

    public int getParaIndex(List<String> contentList, List<String> expPatternList) {
        int count = 0;
        for (String line : contentList) {
            for (String exp : expPatternList) {
                Pattern pattern = Pattern.compile(exp);
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    return count;
                }
            }
            count = count + 1;
        }
        return -1;
    }

    public String getParaContent(List<String> contentList, List<String> expPatternList) {
        int count = 0;
        for (String line : contentList) {
            for (String exp : expPatternList) {
                Pattern pattern = Pattern.compile(exp);
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    return matcher.group();
                }
            }
        }
        return "";
    }

    public String getPara(int start, int end, List<String> contentList) {
        String content = "";
        if (start >= end || start == -1 ){
//            System.out.println("格式不规整");
            return content;
        }
        for (int i=start; i<end; i++) {
            content = content + contentList.get(i) + "\n";
        }
        return content;
    }

    public String getParaByReport(int start, int end, List<String> contentList) {
        String content = "";
        if (start == -1 ){
            return content;
        }
        if(start > 0 && (start == end || end == -1)){
            content = content + contentList.get(start) + "\n";
        }else {
            for (int i = start; i < end; i++) {
                content = content + contentList.get(i) + "\n";
            }
        }
        return content;
    }

    public static List<String> docProc(String content) {
        String[] contentArray = content.split("\n");
        List<String> contentList = new ArrayList<>();
        for (int i = 0; i < contentArray.length; i++) {
            if (! "".equals(contentArray[i].replaceAll("\\s+", ""))) {
                contentList.add(contentArray[i]);
            }
        }
        return contentList;
    }

    public List<String> findCasecause(String backgroundText, String conclusion, String factText) {
        //先从本院认为段提取案由
        Set<String> caseSet = new HashSet<>();
        LinkedHashMap<String, CasecauseBasicConfig> map = BeanConfigCenter.extractConfig.getCasecauseConfig().getCasecause();
        for (String casename : map.keySet()) {
            CasecauseBasicConfig casecauseBasicConfig = map.get(casename);
            List<String> ruleList = casecauseBasicConfig.getRule();
            if (CommonTools.ismatch(conclusion, ruleList)){
                if ( casecauseBasicConfig.getSubSituationRule().size() == 0 || CommonTools.ismatch(conclusion, casecauseBasicConfig.getSubSituationRule())){
                    caseSet.add(casecauseBasicConfig.getName());
                }
            }
        }
        // 从被告人段提取案由
        if (caseSet == null || caseSet.isEmpty()) {
            for (String casename : map.keySet()) {
                CasecauseBasicConfig casecauseBasicConfig = map.get(casename);
                List<String> ruleList = casecauseBasicConfig.getRule();
                if (CommonTools.ismatch(backgroundText, ruleList)){
                    caseSet.add(casecauseBasicConfig.getName());
                }
            }
        }
        // 根据经审理查明段，利用模型预测案由，目前仅支持单罪名预测
        if (caseSet == null || caseSet.isEmpty() ) {
            JSONObject reqPara = new JSONObject();
            reqPara.put("content", factText);
            String resultModelString = HttpRequest.sendPost(BeanConfigCenter.interfaceConfig.getInterfaceModel().getPredictcasecausermdl(), reqPara);
            caseSet.add(resultModelString);
        }
        return new ArrayList<>(caseSet);
    }

    public List<String> findCasecauseOpinion(String conclusion) {
        //先从本院认为段提取案由
        Set<String> caseSet = new HashSet<>();
        LinkedHashMap<String, CasecauseBasicConfig> map = BeanConfigCenter.extractConfig.getCasecauseConfig().getCasecause();
        for (String casename : map.keySet()) {
            CasecauseBasicConfig casecauseBasicConfig = map.get(casename);
            List<String> ruleList = casecauseBasicConfig.getRule();
            if (CommonTools.ismatch(conclusion, ruleList)){
                if ( casecauseBasicConfig.getSubSituationRule().size() == 0 || CommonTools.ismatch(conclusion, casecauseBasicConfig.getSubSituationRule())){
                    caseSet.add(casecauseBasicConfig.getName());
                }
            }
        }
        return new ArrayList<>(caseSet);
    }

    public List<String> findCasecauseInvestigate(String backgroundText) {
        Set<String> caseSet = new HashSet<>();
        LinkedHashMap<String, CasecauseBasicConfig> map = BeanConfigCenter.extractConfig.getCasecauseConfig().getCasecause();
        // 从被告人段提取案由
        if (caseSet == null || caseSet.isEmpty()) {
            for (String casename : map.keySet()) {
                CasecauseBasicConfig casecauseBasicConfig = map.get(casename);
                List<String> ruleList = casecauseBasicConfig.getRule();
                if (CommonTools.ismatch(backgroundText, ruleList)){
                    caseSet.add(casecauseBasicConfig.getName());
                }
            }
        }
        return new ArrayList<>(caseSet);
    }

    public List<String> findCasecauseJustice(String factText) {
        Set<String> caseSet = new HashSet<>();
        LinkedHashMap<String, CasecauseBasicConfig> map = BeanConfigCenter.extractConfig.getCasecauseConfig().getCasecause();
        if (caseSet == null || caseSet.isEmpty() ) {
            JSONObject reqPara = new JSONObject();
            reqPara.put("content", factText);
            String resultModelString = HttpRequest.sendPost(BeanConfigCenter.interfaceConfig.getInterfaceModel().getPredictcasecausermdl(), reqPara);
            caseSet.add(resultModelString);
        }
        return new ArrayList<>(caseSet);
    }

    public List<String> findCasecause(String url, String factText) {
        List<String> caseList = new ArrayList<>();
        MultivaluedMapImpl param = new MultivaluedMapImpl();
        param.add("content", factText);
        String casecause = HttpRequest.sendPost(url, param);
        caseList.add(casecause);
        return caseList;
    }

    public int getNextIndex(int start, List<Integer> partList, int contentSize){
        for(Integer index : partList) {
            if (start < index) return index;
        }
        if(start == partList.get(partList.size()-1) && start < contentSize)
            return contentSize;
        return start;
    }
}
