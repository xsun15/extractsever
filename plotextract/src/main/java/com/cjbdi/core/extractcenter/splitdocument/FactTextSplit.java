package com.cjbdi.core.extractcenter.splitdocument;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.extractcenter.splitdocument.FactTextConfig;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

public class FactTextSplit {

   public static FactTextConfig run(String text) {
      FactTextConfig factTextConfig = new FactTextConfig();
      List textList = Arrays.asList(text.split("\n"));
      if(StringUtils.isNotEmpty(text)) {
         if(textList.size() == 1) {
            factTextConfig.body = text;
            return factTextConfig;
         }

         int count = 0;

         Iterator isbodystart;
         String line;
         String line1;
         for(isbodystart = textList.iterator(); isbodystart.hasNext(); ++count) {
            line = (String)isbodystart.next();
            if(MatchRule.IsMatch(line, BeanFactoryConfig.factTextConfig.getFactTextConfig().getHeader()) && count < 2) {
               factTextConfig.header = factTextConfig.header + line + "\n";
            }

            if(MatchRule.IsMatch(line, BeanFactoryConfig.factTextConfig.getFactTextConfig().getTail())) {
               factTextConfig.tail = factTextConfig.tail + line + "\n";
            }

            if(MatchRule.IsMatch(line, BeanFactoryConfig.factTextConfig.getFactTextConfig().getCansize()) && count < 3) {
               line1 = "^另查明";
               Pattern pattern = Pattern.compile(line1);
               Matcher matcher = pattern.matcher(line);
               if(matcher.find()) {
                  break;
               }

               factTextConfig.isnormal = true;
            }
         }

         if(factTextConfig.isnormal) {
            if(factTextConfig.header.isEmpty()) {
               for(isbodystart = textList.iterator(); isbodystart.hasNext(); factTextConfig.header = factTextConfig.header + line + "\n") {
                  line = (String)isbodystart.next();
                  if(MatchRule.IsMatch(line, BeanFactoryConfig.factTextConfig.getFactTextConfig().getCansize())) {
                     break;
                  }
               }
            }

            factTextConfig.tail = "";
            boolean var9 = false;
            Iterator var10 = textList.iterator();

            while(var10.hasNext()) {
               line1 = (String)var10.next();
               if(MatchRule.IsMatch(line1, BeanFactoryConfig.factTextConfig.getFactTextConfig().getCansize())) {
                  var9 = true;
               }

               if(var9 && !MatchRule.IsMatch(line1, BeanFactoryConfig.factTextConfig.getFactTextConfig().getCansize())) {
                  factTextConfig.tail = factTextConfig.tail + line1;
               }
            }
         }

         isbodystart = textList.iterator();

         while(isbodystart.hasNext()) {
            line = (String)isbodystart.next();
            if(!factTextConfig.header.contains(line) && !factTextConfig.tail.contains(line)) {
               factTextConfig.body = factTextConfig.body + line + "\n";
            }
         }
      }

      return factTextConfig;
   }
}
