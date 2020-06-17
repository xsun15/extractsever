package com.cjbdi.core.extractcenter.utils.splitdocument;

import com.cjbdi.core.configcenter.BeanFactoryConfig;
import com.cjbdi.core.utils.CommonTools;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FactTextSplit {

   public static FactTextConfig run(String text) {
      FactTextConfig factTextConfig = new FactTextConfig();
      if(StringUtils.isNotEmpty(text)) {
         List textList = Arrays.asList(text.split("\n"));
         // 如果只有一段，则直接返回
         if(textList.size() == 1) {

            factTextConfig.body = text;
            factTextConfig.isNormal = true;
            return factTextConfig;
         }
         // 判断头段、body和尾段
         int count = 0;
         for(Iterator isBodyStart = textList.iterator(); isBodyStart.hasNext(); ++count) {
            String line = (String)isBodyStart.next();
            if(CommonTools.isMatchPattern(line, BeanFactoryConfig.splitDocConfig.getFactModel().getHeader().getRule()) && count < 2) {
               factTextConfig.header = factTextConfig.header + line + "\n";
            }
            if(CommonTools.isMatchPattern(line, BeanFactoryConfig.splitDocConfig.getFactModel().getTail().getRule())) {
               factTextConfig.tail = factTextConfig.tail + line + "\n";
            }
            if(CommonTools.isMatchPattern(line, BeanFactoryConfig.splitDocConfig.getFactModel().getBody().getRule()) && count < 3) {
               String rule = "^另查明";
               Pattern pattern = Pattern.compile(rule);
               Matcher matcher = pattern.matcher(line);
               if(matcher.find()) {
                  break;
               }
               factTextConfig.isNormal = true;
            }
         }
         // 根据起始位置，获取相应段落内容
         if(factTextConfig.isNormal) {
            if(factTextConfig.header.isEmpty()) {
               for(Iterator isBodyStart = textList.iterator(); isBodyStart.hasNext(); ) {
                  String line = (String)isBodyStart.next();
                  if(CommonTools.isMatchPattern(line, BeanFactoryConfig.splitDocConfig.getFactModel().getBody().getRule())) {
                     break;
                  }
                  factTextConfig.header = factTextConfig.header + line + "\n";
               }
            }
            factTextConfig.tail = "";
            boolean flag = false;
            while(textList.iterator().hasNext()) {
               String line = (String)textList.iterator().next();
               if(CommonTools.isMatchPattern(line, BeanFactoryConfig.splitDocConfig.getFactModel().getBody().getRule())) {
                  flag = true;
               }
               if(flag && !CommonTools.isMatchPattern(line, BeanFactoryConfig.splitDocConfig.getFactModel().getBody().getRule())) {
                  factTextConfig.tail = factTextConfig.tail + line;
               }
            }
         }
         while(textList.iterator().hasNext()) {
            String line = (String)textList.iterator().next();
            if(!factTextConfig.header.contains(line) && !factTextConfig.tail.contains(line)) {
               factTextConfig.body = factTextConfig.body + line + "\n";
            }
         }
      }
      return factTextConfig;
   }
}
