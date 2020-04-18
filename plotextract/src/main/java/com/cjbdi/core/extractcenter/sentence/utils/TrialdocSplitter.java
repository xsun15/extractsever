package com.cjbdi.core.extractcenter.sentence.utils;

import com.cjbdi.core.extractcenter.sentence.utils.ParaSplitter;
import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrialdocSplitter implements ParaSplitter {

   static List backgroundExpList;
   static List caseExpList;
   static List publicProsecutionExpList;
   static List ascertainExpList;
   static List evidencenExpList;
   static List conclusionExpList;
   static List appendExpList;
   Map paras;


   public static void setBackgroundExpList(List backgroundExpList) {
      backgroundExpList = backgroundExpList;
   }

   public static void setCaseExpList(List caseExpList) {
      caseExpList = caseExpList;
   }

   public static void setPublicProsecutionExpList(List publicProsecutionExpList) {
      publicProsecutionExpList = publicProsecutionExpList;
   }

   public static void setAscertainExpList(List ascertainExpList) {
      ascertainExpList = ascertainExpList;
   }

   public static void setEvidencenExpList(List evidencenExpList) {
      evidencenExpList = evidencenExpList;
   }

   public static void setConclusionExpList(List conclusionExpList) {
      conclusionExpList = conclusionExpList;
   }

   public static void setAppendExpList(List appendExpList) {
      appendExpList = appendExpList;
   }

   public static List getEvidencenExpList() {
      return evidencenExpList;
   }

   public Map split(String text) {
      this.paras = new HashMap();
      this.paras.put(Integer.valueOf(0), text);
      StringTokenizer t = new StringTokenizer(text, "[\r\n]");
      ArrayList paragraphList = new ArrayList();

      while(t.hasMoreElements()) {
         paragraphList.add(t.nextToken());
      }

      String[] paragraphs = (String[])paragraphList.toArray(new String[paragraphList.size()]);
      byte searchStartIndex = 0;
      int backgroundStartIndex = this.getParaIndex(paragraphs, searchStartIndex, backgroundExpList);
      int searchStartIndex1 = backgroundStartIndex + 1;
      int caseStartIndex = this.getParaIndex(paragraphs, searchStartIndex1, caseExpList);
      searchStartIndex1 = caseStartIndex < 0?caseStartIndex + 1:caseStartIndex;
      int publicProsecutionStartIndex = this.getParaIndex(paragraphs, searchStartIndex1, publicProsecutionExpList);
      searchStartIndex1 = publicProsecutionStartIndex + 1;
      int ascertainStartIndex = this.getParaIndex(paragraphs, searchStartIndex1, ascertainExpList);
      searchStartIndex1 = ascertainStartIndex + 1;
      int conclusionStartIndex = this.getParaIndex(paragraphs, searchStartIndex1, conclusionExpList);
      if(conclusionStartIndex < 0) {
         conclusionStartIndex = paragraphs.length;
      }

      searchStartIndex1 = conclusionStartIndex + 1;
      int appendStartIndex = this.getParaIndex(paragraphs, searchStartIndex1, appendExpList);
      if(appendStartIndex < 0) {
         appendStartIndex = paragraphs.length;
      }

      int titleEndIndex = backgroundStartIndex;
      if(backgroundStartIndex < 0) {
         if(caseStartIndex > 0) {
            titleEndIndex = caseStartIndex;
         } else if(publicProsecutionStartIndex > 0) {
            titleEndIndex = publicProsecutionStartIndex;
         } else if(ascertainStartIndex > 0) {
            titleEndIndex = ascertainStartIndex;
         } else if(conclusionStartIndex > 0) {
            titleEndIndex = conclusionStartIndex;
         } else if(appendStartIndex > 0) {
            titleEndIndex = appendStartIndex;
         }
      }

      int backgroundEndIndex = caseStartIndex;
      if(caseStartIndex < 0) {
         if(publicProsecutionStartIndex > 0) {
            backgroundEndIndex = publicProsecutionStartIndex;
         } else if(ascertainStartIndex > 0) {
            backgroundEndIndex = ascertainStartIndex;
         } else if(conclusionStartIndex > 0) {
            backgroundEndIndex = conclusionStartIndex;
         } else if(appendStartIndex > 0) {
            backgroundEndIndex = appendStartIndex;
         }
      }

      int caseEndIndex = publicProsecutionStartIndex;
      if(publicProsecutionStartIndex < 0) {
         if(ascertainStartIndex > 0) {
            caseEndIndex = ascertainStartIndex;
         } else if(conclusionStartIndex > 0) {
            caseEndIndex = conclusionStartIndex;
         } else if(appendStartIndex > 0) {
            caseEndIndex = appendStartIndex;
         }
      }

      int publicProsecutionEndIndex = ascertainStartIndex;
      if(ascertainStartIndex < 0) {
         if(conclusionStartIndex > 0) {
            publicProsecutionEndIndex = conclusionStartIndex;
         } else if(appendStartIndex > 0) {
            publicProsecutionEndIndex = appendStartIndex;
         }
      }

      int ascertainEndIndex = conclusionStartIndex;
      if(conclusionStartIndex < 0) {
         if(conclusionStartIndex > 0) {
            ascertainEndIndex = conclusionStartIndex;
         } else if(appendStartIndex > 0) {
            ascertainEndIndex = appendStartIndex;
         }
      }

      this.paras.put(Integer.valueOf(1), this.getText(paragraphs, 0, titleEndIndex));
      if(backgroundStartIndex < 0) {
         backgroundStartIndex = 0;
      }

      String sectionText = this.getText(paragraphs, backgroundStartIndex, backgroundEndIndex);
      if(!Strings.isNullOrEmpty(sectionText)) {
         this.paras.put(Integer.valueOf(2), sectionText);
      }

      sectionText = this.getText(paragraphs, caseStartIndex, caseEndIndex);
      if(!Strings.isNullOrEmpty(sectionText)) {
         this.paras.put(Integer.valueOf(3), sectionText);
      }

      sectionText = this.getText(paragraphs, publicProsecutionStartIndex, publicProsecutionEndIndex);
      if(!Strings.isNullOrEmpty(sectionText)) {
         this.paras.put(Integer.valueOf(4), sectionText);
      }

      sectionText = this.getText(paragraphs, ascertainStartIndex, ascertainEndIndex);
      if(!Strings.isNullOrEmpty(sectionText)) {
         this.paras.put(Integer.valueOf(6), sectionText);
      }

      sectionText = this.getText(paragraphs, conclusionStartIndex, appendStartIndex);
      if(!Strings.isNullOrEmpty(sectionText)) {
         this.paras.put(Integer.valueOf(8), sectionText);
      }

      sectionText = this.getText(paragraphs, appendStartIndex, paragraphs.length);
      if(!Strings.isNullOrEmpty(sectionText)) {
         this.paras.put(Integer.valueOf(9), sectionText);
      }

      return this.paras;
   }

   public String getParaText(int paraEnum) {
      return this.paras == null?null:(this.paras.containsKey(Integer.valueOf(paraEnum))?(String)this.paras.get(Integer.valueOf(paraEnum)):(paraEnum == 6 && this.paras.containsKey(Integer.valueOf(4))?(String)this.paras.get(Integer.valueOf(4)):(String)this.paras.get(Integer.valueOf(0))));
   }

   int getParaIndex(String[] paragraphs, int starParaIndex, List expList) {
      int i = starParaIndex;

      label21:
      while(i < paragraphs.length) {
         String para = paragraphs[i];
         Iterator var6 = expList.iterator();

         Matcher matcher;
         do {
            if(!var6.hasNext()) {
               ++i;
               continue label21;
            }

            String exp = (String)var6.next();
            Pattern pattern = Pattern.compile(exp);
            matcher = pattern.matcher(para);
         } while(!matcher.find());

         return i;
      }

      return -1;
   }

   String getText(String[] paragraphs, int starParaIndex, int endParaIndex) {
      if(starParaIndex < 0) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder();
         if(endParaIndex == 0) {
            endParaIndex = paragraphs.length;
         }

         for(int i = starParaIndex; i < endParaIndex && i < paragraphs.length; ++i) {
            sb.append(paragraphs[i]).append("\n");
         }

         if(sb.length() > 0) {
            sb.delete(sb.length() - 1, sb.length());
         } else if(starParaIndex == endParaIndex && starParaIndex != 0 && starParaIndex != paragraphs.length) {
            sb.append(paragraphs[starParaIndex]);
         }

         return sb.toString();
      }
   }
}
