package com.cjbdi.core.extractcenter.sentence.utils;

import com.cjbdi.core.extractcenter.sentence.utils.CriminalRecord;
import com.cjbdi.core.extractcenter.sentence.utils.DateTimeExtractor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CriminalRecordExtractor {

   public List extract(String defendant, String defendantText) {
      ArrayList criminalRecordList = new ArrayList();
      defendantText = defendantText.replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5.，,、。？“”：*×\\n《》（）]+", "");
      defendantText = defendantText.replaceAll(" |\t", "");
      StringTokenizer t = new StringTokenizer(defendantText, "[\r\n]");

      while(t.hasMoreElements()) {
         String backgroundText = t.nextToken();
         backgroundText = backgroundText.replace("*", "");
         if(backgroundText.contains(defendant)) {
            String defendantRe = "(被告人|被不起诉).*?出生|(被告人|被不起诉).*?[。；因犯被]";
            Pattern defendantPattern = Pattern.compile(defendantRe);
            Matcher defendantMatcher = defendantPattern.matcher(backgroundText);
            String arrestRe;
            if(defendantMatcher.find()) {
               arrestRe = defendantMatcher.group();
               arrestRe = arrestRe.replaceAll("因|犯|。|；|被", "");
               backgroundText = backgroundText.replaceAll(arrestRe, "");
            }

            arrestRe = "(因本案|因涉嫌|因[^，。].*?嫌疑).*?。";
            Pattern arrestPattern = Pattern.compile(arrestRe);
            Matcher arrestMatcher = arrestPattern.matcher(backgroundText);

            while(arrestMatcher.find()) {
               backgroundText = backgroundText.replaceAll(arrestMatcher.group(), "");
               arrestPattern.matcher(backgroundText);
            }

            boolean recordFlag = true;

            while(recordFlag) {
               String recordRe = "([0-9]{1,4}年.*?月?日?因.*?[被由].*?[。；因于]|(因|于).*?年.*?月?日?[被由].*?[。；因于]|[0-9]{1,4}年.*?月?日?[被由].*?以.*?[。；因于])";
               Pattern recordPattern = Pattern.compile(recordRe);
               Matcher recordMatcher = recordPattern.matcher(backgroundText);
               if(recordMatcher.find()) {
                  String recordMatchText = recordMatcher.group();
                  CriminalRecord criminalRecordlocal = new CriminalRecord();
                  String tmpText = recordMatchText;
                  boolean timeFlag = true;

                  while(timeFlag) {
                     String keyPattern = "[0-9]{1,4}年[0-9]*月?[0-9]*日?";
                     Pattern keyMatcher = Pattern.compile(keyPattern);
                     Matcher timeMatcher = keyMatcher.matcher(tmpText);
                     Pattern timeEffectivePattern = Pattern.compile(recordRe);
                     Matcher timeEffectiveMatcher = timeEffectivePattern.matcher(tmpText);
                     if(timeMatcher.find() && timeEffectiveMatcher.find()) {
                        String timeMatchText = timeMatcher.group();
                        Optional timeOpt = DateTimeExtractor.extract(timeMatcher.group());
                        if(timeOpt.isPresent()) {
                           criminalRecordlocal.setTime(((LocalDateTime)timeOpt.get()).toLocalDate());
                           criminalRecordlocal.setText(recordMatcher.group());
                           criminalRecordlocal.setCause(recordMatcher.group());
                           tmpText = tmpText.replace(timeMatchText, "");
                           String releaseRe = "[0-9]{1,4}年[0-9]*月?[0-9]*日?.*?释放";
                           Pattern releasePattern = Pattern.compile(releaseRe);
                           Matcher releaseMatcher = releasePattern.matcher(tmpText);
                           if(releaseMatcher.find()) {
                              String releaseTime = releaseMatcher.group();
                              Optional releaseTimeOpt = DateTimeExtractor.extract(releaseTime);
                              if(releaseTimeOpt.isPresent()) {
                                 criminalRecordlocal.setReleaseTime(((LocalDateTime)releaseTimeOpt.get()).toLocalDate());
                              }
                           }

                           criminalRecordList.add(criminalRecordlocal);
                        }

                        tmpText = tmpText.replace(timeMatchText, "");
                     } else {
                        timeFlag = false;
                     }
                  }

                  Pattern keyPattern1 = Pattern.compile("(因|于)$");
                  Matcher keyMatcher1 = keyPattern1.matcher(recordMatchText);
                  if(keyMatcher1.find()) {
                     recordMatchText = recordMatchText.substring(0, recordMatchText.length() - 1);
                  }

                  if(backgroundText.contains(recordMatchText)) {
                     backgroundText = backgroundText.replaceAll(recordMatchText, "");
                  } else {
                     backgroundText = "";
                  }
               } else {
                  recordFlag = false;
               }
            }
         }
      }

      return criminalRecordList;
   }
}
