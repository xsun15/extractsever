package com.cjbdi.core.configurecentent.converlabel.zhengan;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.casecause.BasicConfig;

public class Zhengan {

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
      jsonObject.put("总则", this.share);
      jsonArray.add(jsonObject);
      return jsonArray;
   }
}
