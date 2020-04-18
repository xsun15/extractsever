package com.cjbdi.test;

import com.hankcs.hanlp.HanLP;
import java.util.List;

public class Summary {

   public static void main(String[] args) {
      String txt = "2018年8月6日，被告人蒋某某驾驶属云南红河交通运输集团有限公司绿春分公司所有的云******号大型普通客车，沿元绿二级公路往元阳方向行驶。11时20分，当车辆行驶至元绿二级公路K76+400M处时，因驾驶人蒋某某操作不当，其驾驶的云******号大型普通客车左侧车头与行人王某甲相撞，造成云******号大型普通客车受损、行人王某甲当场死亡，行人王某乙受伤的道路交通事故。 \n\n绿春县公安局交警大队认定，驾驶人蒋某某负此次交通事故的全部责任，王某甲、王某乙本次事故无责任。经鉴定，蒋某某驾驶的云******号大型普通客车发生事故时转向系功能有效，制动系功能有效；被害人王某甲死亡原因系交通事故致闭合性颅脑损伤死亡。 ";
      String summary = HanLP.getSummary(txt, 200);
      System.out.println(summary);
      List keywordList = HanLP.extractKeyword(txt, 25);
      System.out.println(keywordList);
   }
}
