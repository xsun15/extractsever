package com.cjbdi.core.extractcenter.sentence.utils;

import com.cjbdi.core.extractcenter.sentence.utils.TrialdocSplitter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.yaml.snakeyaml.Yaml;

public class TrialdocSplitterConfig {

   public List backgroundExpList;
   public List caseExpList;
   public List publicProsecutionExpList;
   public List ascertainExpList;
   public List evidenceExpList;
   public List conclusionExpList;
   public List appendExpList;


   public static void init() {
      TrialdocSplitterConfig config = null;
      Yaml yaml = new Yaml();

      try {
         InputStream e = TrialdocSplitterConfig.class.getResourceAsStream("/extractor/commons/trial_doc_splitter.yml");
         config = (TrialdocSplitterConfig)yaml.loadAs(new InputStreamReader(e, "UTF8"), TrialdocSplitterConfig.class);
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      if(config != null) {
         TrialdocSplitter.setBackgroundExpList(config.backgroundExpList);
         TrialdocSplitter.setCaseExpList(config.caseExpList);
         TrialdocSplitter.setPublicProsecutionExpList(config.publicProsecutionExpList);
         TrialdocSplitter.setAscertainExpList(config.ascertainExpList);
         TrialdocSplitter.setEvidencenExpList(config.evidenceExpList);
         TrialdocSplitter.setConclusionExpList(config.conclusionExpList);
         TrialdocSplitter.setAppendExpList(config.appendExpList);
      }
   }
}
