package com.cjbdi.core.extractcenter.split;


import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.configcenter.structurateConfig.utils.FirstTrialBasicConfig;
import com.cjbdi.core.extractcenter.model.FirstTrialModel;
import com.cjbdi.core.util.CommonTools;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstTrialSplit extends BasicSplit {

    private FirstTrialModel firstTrial;
    private FirstTrialBasicConfig firstTrialBasicConfig = BeanConfigCenter.structurateConfig.getFirstTrialConfig().getFirstTrial();

    public FirstTrialModel split(String content) {
        List<ParaInfo> paraInfoList = new ArrayList<>();
        List<Integer> partList = new ArrayList<>();
        firstTrial = new FirstTrialModel();
        List<String> contentList = docProc(content);
        List<String> fieldExc = new ArrayList<String>();
        Segment segment = HanLP.newSegment().enableNameRecognize(true);
        try{
            if(contentList.isEmpty() || contentList.size() <= 5) return firstTrial;
            firstTrial.setTitle(contentList.get(0));
            firstTrial.setCourtName(contentList.get(1));
            firstTrial.setDocType(contentList.get(2));
            firstTrial.setCourtCaseId(contentList.get(3));
            firstTrial.setProcuName(contentList.get(4));
            Class c = Class.forName("com.cjbdi.portrait.configcenter.structurateConfig.utils.FirstTrialConfig");
            Field[] fields = c.getDeclaredFields();
            boolean accessible = false;
            List<Integer> invalidIndex = new ArrayList<>();
            for(Field field : fields) {
                accessible = field.isAccessible();
                field.setAccessible(true);
                int index = -1;
                index = getParaIndex(contentList, (List<String>) field.get(firstTrialBasicConfig));
                if (index!=-1&&(field.getName().equalsIgnoreCase("sue")||field.getName().equalsIgnoreCase("accuse")||
                        field.getName().equalsIgnoreCase("justice"))) {
                    invalidIndex.add(index);
                }
                if (index == -1) fieldExc.add(field.getName());
                else {
                    ParaInfo paraInfo = new ParaInfo();
                    paraInfo.ename=field.getName();
                    paraInfo.index=index;
                    paraInfoList.add(paraInfo);
                }
                field.setAccessible(accessible);
            }
            // 分段调整(证据段)
            for (int i=0; i<paraInfoList.size(); i++) {
                ParaInfo paraInfo = paraInfoList.get(i);
                if (paraInfo.ename.equalsIgnoreCase("evidence")) {
                    paraInfo.index=getParaIndex(contentList, firstTrialBasicConfig.getEvidence(), invalidIndex);
                    paraInfoList.set(i, paraInfo);
                }
                partList.add(paraInfo.index);
            }
            Field[] fields1 = firstTrial.getClass().getDeclaredFields();
            int i = 0;
            for(Field field : fields1) {
                if(i >= partList.size() || !filedContains(fields, field)) continue;
                accessible = field.isAccessible();
                field.setAccessible(true);
                if(fieldExc.contains(field.getName())) continue;
                field.set(firstTrial, getParaByReport(partList.get(i), getNextIndex(partList.get(i), partList, contentList.size()), contentList));
                field.setAccessible(accessible);
                i++;
            }
            // 修正每个犯罪事实跟一段证据描述问题
            if (StringUtils.isNotEmpty(firstTrial.getEvidence())) {
                String evidence = firstTrial.getEvidence();
                List<String> evidenceList = Arrays.asList(evidence.split("\n"));
                int count = 0;
                for (String line : evidenceList) {
                    if (CommonTools.ismatch(line, firstTrialBasicConfig.getEvidence())) {
                        count++;
                    }
                }
                if (count>1) {
                    Map<String, String> result = CommonTools.reviseFactEvidence(firstTrial.getJustice(), firstTrial.getEvidence());
                    if (result!=null&&!result.isEmpty()) {
                        firstTrial.setJustice(result.get("factText"));
                        firstTrial.setEvidence(result.get("evidence"));
                    }
                }
            }
            // 修正公诉机关指控的辩护双方意见
            if (StringUtils.isNotEmpty(firstTrial.getAccuse())) {
                String accuse = firstTrial.getAccuse();
                List<String> accuseList = Arrays.asList(accuse.split("\n"));
                String negativeWords = "陈述,庭审,笔录,辩解,证人,证言,供述,供认,辩称,答辩,辩护人,";
                List<String> negativeWordList = Arrays.asList(negativeWords.split(","));
                String result = "";
                for (String line : accuseList) {
                    if (!CommonTools.isContain(line, negativeWordList)) {
                        result += line + "\n";
                    }
                }
                firstTrial.setAccuse(result);
            }
            // 抽取审判时间
            firstTrial.setJudgeDate(CommonTools.matchText(content, firstTrialBasicConfig.getJudgeDate()));
            // 抽取省份
            firstTrial.setProvince(CommonTools.extractProvince(firstTrial.getCourtName()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return firstTrial;
    }

    private boolean filedContains(Field[] fields, Field field){
        for(Field temp : fields){
            if(temp.getName().equalsIgnoreCase(field.getName())) return true;
        }
        return false;
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

    public int getNextIndex(int start, LinkedHashMap<String, Integer> partList, int contentSize){
        for(String fieldname : partList.keySet()) {
            if (start < partList.get(fieldname)) return partList.get(fieldname);
        }
        if(start == partList.get(partList.size()-1) && start < contentSize)
            return contentSize;
        return start;
    }

    class ParaInfo {
        public String ename;
        public int index;
    }
}
