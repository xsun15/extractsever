package com.cjbdi.core.configurecentent.converlabel.sentence;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.casecause.BasicConfig;
import com.cjbdi.core.configurecentent.converlabel.sentence.Affray;
import com.cjbdi.core.configurecentent.converlabel.sentence.Concealci;
import com.cjbdi.core.configurecentent.converlabel.sentence.Drug;
import com.cjbdi.core.configurecentent.converlabel.sentence.Dutyencroachment;
import com.cjbdi.core.configurecentent.converlabel.sentence.Endangerpa;
import com.cjbdi.core.configurecentent.converlabel.sentence.Extortion;
import com.cjbdi.core.configurecentent.converlabel.sentence.Falseimprison;
import com.cjbdi.core.configurecentent.converlabel.sentence.Findtrouble;
import com.cjbdi.core.configurecentent.converlabel.sentence.Fraud;
import com.cjbdi.core.configurecentent.converlabel.sentence.Injury;
import com.cjbdi.core.configurecentent.converlabel.sentence.Rape;
import com.cjbdi.core.configurecentent.converlabel.sentence.Robbery;
import com.cjbdi.core.configurecentent.converlabel.sentence.Seizing;
import com.cjbdi.core.configurecentent.converlabel.sentence.Share;
import com.cjbdi.core.configurecentent.converlabel.sentence.Steal;
import com.cjbdi.core.configurecentent.converlabel.sentence.Traffic;

public class SelfSentence {

   private Share share = new Share();
   private Affray affray = new Affray();
   private Concealci concealci = new Concealci();
   private Drug drug = new Drug();
   private Dutyencroachment dutyencroachment = new Dutyencroachment();
   private Endangerpa endangerpa = new Endangerpa();
   private Extortion extortion = new Extortion();
   private Falseimprison falseimprison = new Falseimprison();
   private Findtrouble findtrouble = new Findtrouble();
   private Fraud fraud = new Fraud();
   private Injury injury = new Injury();
   private Rape rape = new Rape();
   private Robbery robbery = new Robbery();
   private Seizing seizing = new Seizing();
   private Steal steal = new Steal();
   private Traffic traffic = new Traffic();
   private DrunkDriving drunkDriving = new DrunkDriving();


   public JSONArray toJson() {
      JSONArray jsonArray = new JSONArray();
      JSONObject jsonObject = new JSONObject();
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("affaryextract")).getName(), this.affray);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("concealextract")).getName(), this.concealci);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("drugextract")).getName(), this.drug);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("dutyencroachmentextract")).getName(), this.dutyencroachment);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("endangerpaextract")).getName(), this.endangerpa);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("extortionextract")).getName(), this.extortion);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("falseimprisonextract")).getName(), this.falseimprison);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("findtroubleextract")).getName(), this.findtrouble);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("fraudextract")).getName(), this.fraud);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("injuryextract")).getName(), this.injury);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("rapeextract")).getName(), this.rape);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("robberyextract")).getName(), this.robbery);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("seizingextract")).getName(), this.seizing);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("stealextract")).getName(), this.steal);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("trafficextract")).getName(), this.traffic);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("drunkDrivingextract")).getName(), this.drunkDriving);
      jsonObject.put("总则", this.share);
      jsonArray.add(jsonObject);
      return jsonArray;
   }
}
