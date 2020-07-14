package com.cjbdi.core.extractcenter.split;

import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.configcenter.structurateConfig.utils.IndicitmentBasicConfig;
import com.cjbdi.core.extractcenter.model.IndicitmentModel;
import com.cjbdi.core.util.CommonTools;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IndicitmentSplit extends BasicSplit {
    private IndicitmentModel indicitmentModel;
    private IndicitmentBasicConfig indicitmentBasicConfig = BeanConfigCenter.structurateConfig.getIndicitmentConfig().getIndicitment();

    public IndicitmentModel split(String content) {
        List<Integer> partList = new ArrayList<>();
        List<String> contentList = docProc(content);
        List<String> fieldExc = new ArrayList<String>();
        List<ParaInfo> paraInfoList = new ArrayList<>();
        indicitmentModel = new IndicitmentModel();
        indicitmentModel.setProcuName(contentList.get(0));
        // 设置文书类型
        String docType = CommonTools.extractDocType(content);
        indicitmentModel.setDocType(docType);
        String rule = ".*?[0-9]{4}.*?号$";
        String matchText = CommonTools.matchText(content, rule);
        if (StringUtils.isNotEmpty(matchText))
            indicitmentModel.setProcuCaseId(matchText);
        try {
            Class c = Class.forName("com.cjbdi.core.configcenter.structurateConfig.utils.IndicitmentBasicConfig");
            Field[] fields = c.getDeclaredFields();
            boolean accessible = false;
            List<Integer> invalidIndex = new ArrayList<>();
            for (Field field : fields) {
                accessible = field.isAccessible();
                field.setAccessible(true);
                int index = -1;
                index = getParaIndex(contentList, (List<String>) field.get(indicitmentBasicConfig));
                if (index != -1 && (field.getName().equalsIgnoreCase("sue") || field.getName().equalsIgnoreCase("accuse") ||
                        field.getName().equalsIgnoreCase("justice"))) {
                    invalidIndex.add(index);
                }
                if (index == -1) fieldExc.add(field.getName());
                else {
                    IndicitmentSplit.ParaInfo paraInfo = new IndicitmentSplit.ParaInfo();
                    paraInfo.ename = field.getName();
                    paraInfo.index = index;
                    paraInfoList.add(paraInfo);
                }
                field.setAccessible(accessible);
            }
            // 分段调整(证据段)
            for (int i=0; i<paraInfoList.size(); i++) {
                IndicitmentSplit.ParaInfo paraInfo = paraInfoList.get(i);
                if (paraInfo.ename.equalsIgnoreCase("evidence")) {
                    paraInfo.index=getParaIndex(contentList, indicitmentBasicConfig.getEvidence(), invalidIndex);
                    paraInfoList.set(i, paraInfo);
                }
                partList.add(paraInfo.index);
            }
            Field[] fields1 = indicitmentModel.getClass().getDeclaredFields();
            int i = 0;
            for(Field field : fields1) {
                if(i >= partList.size() || !filedContains(fields, field)) continue;
                accessible = field.isAccessible();
                field.setAccessible(true);
                if(fieldExc.contains(field.getName())) continue;
                field.set(indicitmentModel, getParaByReport(partList.get(i), getNextIndex(partList.get(i), partList, contentList.size()), contentList));
                field.setAccessible(accessible);
                i++;
            }
            // 修正每个犯罪事实跟一段证据描述问题
            if (StringUtils.isNotEmpty(indicitmentModel.getEvidence())) {
                String evidence = indicitmentModel.getEvidence();
                List<String> evidenceList = Arrays.asList(evidence.split("\n"));
                int count = 0;
                for (String line : evidenceList) {
                    if (CommonTools.ismatch(line, indicitmentBasicConfig.getEvidence())) {
                        count++;
                    }
                }
                if (count>1) {
                    Map<String, String> result = CommonTools.reviseFactEvidence(indicitmentModel.getJustice(), indicitmentModel.getEvidence());
                    if (result!=null&&!result.isEmpty()) {
                        indicitmentModel.setJustice(result.get("factText"));
                        indicitmentModel.setEvidence(result.get("evidence"));
                    }
                }
            }
            if (StringUtils.isNotEmpty(indicitmentModel.getJustice())) indicitmentModel.setJustice(indicitmentModel.getJustice().replaceAll("null", ""));
            // 提取省份
            indicitmentModel.setProvince(extractProvince(indicitmentModel.getProcuName(), content));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return indicitmentModel;
    }


    public int getParaIndex(List<String> contentList, List<String> expPatternList, List<Integer> invalidIndex) {
        int count = 0;
        for (String line : contentList) {
            for (String exp : expPatternList) {
                Pattern pattern = Pattern.compile(exp);
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    boolean flag = true;
                    for (Integer index : invalidIndex){
                        if (count<=index) {
                            flag = false;
                        }
                    }
                    if (flag) return count;
                }
            }
            count = count + 1;
        }
        return -1;
    }

    private boolean filedContains(Field[] fields, Field field){
        for(Field temp : fields){
            if(temp.getName().equalsIgnoreCase(field.getName())) return true;
        }
        return false;
    }

    class ParaInfo {
        public String ename;
        public int index;
    }
}
