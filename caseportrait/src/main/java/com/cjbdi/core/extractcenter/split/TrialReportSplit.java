package com.cjbdi.core.extractcenter.split;

import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.configcenter.structurateConfig.utils.TrialReportBasicConfig;
import com.cjbdi.core.extractcenter.model.TrialReportModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TrialReportSplit extends BasicSplit {

    private TrialReportModel trialReport = null;
    private TrialReportBasicConfig trialReportBasicConfig = BeanConfigCenter.structurateConfig.getTrialReportConfig().getTrialReport();
    public static Field[] getDeclaredField(Object object){
        Field[] field = null ;

        Class<?> clazz = object.getClass() ;

        for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredFields() ;
                return field ;
            } catch (Exception e) {
            }
        }

        return null;
    }

    public TrialReportModel split(String content)  {
        List<Integer> partList = new ArrayList<>();
        trialReport = new TrialReportModel();
        List<String> contentList = docProc(content);
        List<String> fieldExc = new ArrayList<String>();
        try{
            Class c = Class.forName("com.cjbdi.portrait.configcenter.TrialReportConfig");
            Field[] fields = c.getDeclaredFields();
            boolean accessible = false;
            for(Field field : fields){
                accessible = field.isAccessible();
                field.setAccessible(true);
                int index = -1;
                if("extract/casecause".equalsIgnoreCase(field.getName())){
                    fieldExc.add(field.getName());
                    trialReport.setCasecause(getParaContent(contentList, (List<String>) field.get(trialReportBasicConfig)));
                }else if("caseno".equalsIgnoreCase(field.getName())){
                    fieldExc.add(field.getName());
                    trialReport.setCaseno(getParaContent(contentList, (List<String>) field.get(trialReportBasicConfig)));
                }else {
                    index = getParaIndex(contentList, (List<String>) field.get(trialReportBasicConfig));
                    if (index == -1) fieldExc.add(field.getName());
                    else partList.add(index);
                }
                field.setAccessible(accessible);
            }
            Field[] fields1 = trialReport.getClass().getDeclaredFields();
            int i = 0;
            for(Field field : fields1){
                if(i >= partList.size()) continue;
                accessible = field.isAccessible();
                field.setAccessible(true);
                if(fieldExc.contains(field.getName())) continue;
                field.set(trialReport, getParaByReport(partList.get(i), getNextIndex(partList.get(i), partList, contentList.size()), contentList));
                field.setAccessible(accessible);
                i++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return trialReport;
    }

}
