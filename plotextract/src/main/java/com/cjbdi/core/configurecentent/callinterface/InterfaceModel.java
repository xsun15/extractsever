package com.cjbdi.core.configurecentent.callinterface;

import com.cjbdi.core.configurecentent.utils.YamlPropertySourceFactoryUser;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class InterfaceModel {

   private List ip;
   private List predictcasecausermdl;
   private List predictcaselistrmdl;
   private List predictfeaturermdl;
   private List predictfeaturepb;
   private List predictfeaturepbnew;
   private List predictcrimecountpb;
   private List recognizemoney;
   private List recognizemoneypb;


   public InterfaceModel(String featureName, String sourceName) {
      this.ip = YamlPropertySourceFactoryUser.loadConfig(featureName + ".ip", sourceName);
      this.predictcasecausermdl = YamlPropertySourceFactoryUser.loadConfig(featureName + ".predictcasecausermdl", sourceName);
      this.predictcaselistrmdl = YamlPropertySourceFactoryUser.loadConfig(featureName + ".predictcaselistrmdl", sourceName);
      this.predictfeaturermdl = YamlPropertySourceFactoryUser.loadConfig(featureName + ".predictfeaturermdl", sourceName);
      this.predictfeaturepb = YamlPropertySourceFactoryUser.loadConfig(featureName + ".predictfeaturepb", sourceName);
      this.predictfeaturepbnew = YamlPropertySourceFactoryUser.loadConfig(featureName + ".predictfeaturepbnew", sourceName);
      this.predictcrimecountpb = YamlPropertySourceFactoryUser.loadConfig(featureName + ".predictcrimecountpb", sourceName);
      this.recognizemoney = YamlPropertySourceFactoryUser.loadConfig(featureName + ".recognizemoney", sourceName);
      this.recognizemoneypb = YamlPropertySourceFactoryUser.loadConfig(featureName + ".recognizemoneypb", sourceName);
   }

   public String getIp() {
      return StringUtils.strip(this.ip.toString(), "[]");
   }

   public void setIp(List ip) {
      this.ip = ip;
   }

   public String getPredictcasecausermdl() {
      return StringUtils.strip(this.ip.toString(), "[]") + StringUtils.strip(this.predictcasecausermdl.toString(), "[]");
   }

   public void setPredictcasecausermdl(List predictcasecausermdl) {
      this.predictcasecausermdl = predictcasecausermdl;
   }

   public String getPredictfeaturermdl() {
      return StringUtils.strip(this.ip.toString(), "[]") + StringUtils.strip(this.predictfeaturermdl.toString(), "[]");
   }

   public void setPredictfeaturermdl(List predictfeaturermdl) {
      this.predictfeaturermdl = predictfeaturermdl;
   }

   public String getPredictfeaturepb() {
      return StringUtils.strip(this.ip.toString(), "[]") + StringUtils.strip(this.predictfeaturepb.toString(), "[]");
   }

   public void setPredictfeaturepb(List predictfeaturepb) {
      this.predictfeaturepb = predictfeaturepb;
   }

   public String getPredictcrimecountpb() {
      return StringUtils.strip(this.ip.toString(), "[]") + StringUtils.strip(this.predictcrimecountpb.toString(), "[]");
   }

   public void setPredictcrimecountpb(List predictcrimecountpb) {
      this.predictcrimecountpb = predictcrimecountpb;
   }

   public String getPredictfeaturepbnew() {
      return StringUtils.strip(this.ip.toString(), "[]") + StringUtils.strip(this.predictfeaturepbnew.toString(), "[]");
   }

   public void setPredictfeaturepbnew(List predictfeaturepbnew) {
      this.predictfeaturepbnew = predictfeaturepbnew;
   }

   public String getPredictcaselistrmdl() {
      return StringUtils.strip(this.ip.toString(), "[]") + StringUtils.strip(this.predictcaselistrmdl.toString(), "[]");
   }

   public void setPredictcaselistrmdl(List predictcaselistrmdl) {
      this.predictcaselistrmdl = predictcaselistrmdl;
   }

   public String getRecognizemoney() {
      return StringUtils.strip(this.ip.toString(), "[]") + StringUtils.strip(this.recognizemoney.toString(), "[]");
   }

   public void setRecognizemoney(List recognizemoney) {
      this.recognizemoney = recognizemoney;
   }

   public String getRecognizemoneypb() {
      return StringUtils.strip(this.ip.toString(), "[]") + StringUtils.strip(this.recognizemoneypb.toString(), "[]");
   }

   public void setRecognizemoneypb(List recognizemoneypb) {
      this.recognizemoneypb = recognizemoneypb;
   }
}
