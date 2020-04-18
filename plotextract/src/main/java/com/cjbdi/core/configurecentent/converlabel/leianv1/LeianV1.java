package com.cjbdi.core.configurecentent.converlabel.leianv1;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.casecause.BasicConfig;
import com.cjbdi.core.configurecentent.converlabel.Robbery;
import com.cjbdi.core.configurecentent.converlabel.leianv1.Affary;
import com.cjbdi.core.configurecentent.converlabel.leianv1.Concealci;
import com.cjbdi.core.configurecentent.converlabel.leianv1.Dutyencroachment;
import com.cjbdi.core.configurecentent.converlabel.leianv1.Endangerpa;
import com.cjbdi.core.configurecentent.converlabel.leianv1.Extortion;
import com.cjbdi.core.configurecentent.converlabel.leianv1.FindTrouble;
import com.cjbdi.core.configurecentent.converlabel.leianv1.Fraud;
import com.cjbdi.core.configurecentent.converlabel.leianv1.IllegalDetension;
import com.cjbdi.core.configurecentent.converlabel.leianv1.Injury;
import com.cjbdi.core.configurecentent.converlabel.leianv1.Rape;
import com.cjbdi.core.configurecentent.converlabel.leianv1.Seizing;
import com.cjbdi.core.configurecentent.converlabel.leianv1.Share;
import com.cjbdi.core.configurecentent.converlabel.leianv1.Steal;
import com.cjbdi.core.configurecentent.converlabel.leianv1.Traffic;

public class LeianV1 {

   private Traffic traffic = new Traffic();
   private Share share = new Share();
   private Injury injury = new Injury();
   private Rape rape = new Rape();
   private IllegalDetension illegalDetension = new IllegalDetension();
   private Steal steal = new Steal();
   private Robbery robbery = new Robbery();
   private Fraud fraud = new Fraud();
   private Seizing seizing = new Seizing();
   private Dutyencroachment dutyencroachment = new Dutyencroachment();
   private Extortion extortion = new Extortion();
   private Endangerpa endangerpa = new Endangerpa();
   private Affary affary = new Affary();
   private FindTrouble findtrouble = new FindTrouble();
   private Concealci concealci = new Concealci();


   public JSONArray toJson() {
      JSONArray jsonArray = new JSONArray();
      JSONObject jsonObject = new JSONObject();
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("trafficextract")).getName(), this.traffic);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("injuryextract")).getName(), this.injury);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("rapeextract")).getName(), this.rape);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("falseimprisonextract")).getName(), this.illegalDetension);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("stealextract")).getName(), this.steal);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("robberyextract")).getName(), this.robbery);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("fraudextract")).getName(), this.fraud);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("seizingextract")).getName(), this.seizing);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("dutyencroachmentextract")).getName(), this.dutyencroachment);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("extortionextract")).getName(), this.extortion);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("endangerpaextract")).getName(), this.endangerpa);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("affaryextract")).getName(), this.affary);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("findtroubleextract")).getName(), this.findtrouble);
      jsonObject.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("concealextract")).getName(), this.concealci);
      jsonObject.put("总则", this.share);
      jsonArray.add(jsonObject);
      return jsonArray;
   }
}
