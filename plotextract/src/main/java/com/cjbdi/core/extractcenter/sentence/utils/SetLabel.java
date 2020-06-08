package com.cjbdi.core.extractcenter.sentence.utils;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.extractcenter.sentence.common.utils.BoolConfig;
import com.cjbdi.core.extractcenter.sentence.common.utils.NumberConfig;
import com.cjbdi.core.extractcenter.sentence.utils.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SetLabel {

   public static Label run(Map<String, String> extractResult, String code) {
      Label label = new Label();
      label.setUsedRegx((String)extractResult.get("Pattern"));
      label.setFlag((long)Integer.parseInt(code));
      label.setText((String)extractResult.get("text"));
      label.setValue("true");
      return label;
   }

   public static Label run(NumberConfig numberConfig, String code) {
      Label label = new Label();
      label.setUsedRegx(numberConfig.rule);
      label.setFlag((long)Integer.parseInt(code));
      label.setText(numberConfig.colorTarget);
      label.setValue(String.valueOf(numberConfig.value));
      label.setParaName(numberConfig.paraName);
      return label;
   }

   public static Label run(BoolConfig boolConfig, String code) {
      Label label = new Label();
      label.setUsedRegx(boolConfig.rule);
      label.setFlag((long)Integer.parseInt(code));
      label.setText(boolConfig.colorTarget);
      label.setValue("true");
      return label;
   }

   public static Label runNumber(JSONObject extractResult, String code) {
      Label label = new Label();
      label.setUsedRegx(extractResult.getString("Pattern"));
      label.setFlag((long)Integer.parseInt(code));
      label.setText(extractResult.getString("text"));
      label.setValue(extractResult.getString("value"));
      return label;
   }

   public static Label runNumber(Map extractResult, String code) {
      Label label = new Label();
      label.setFlag((long)Integer.parseInt(code));
      label.setText((String)extractResult.get("有效金额"));
      label.setValue((String)extractResult.get("提取金额"));
      return label;
   }

   public static Label run(String value, String code) {
      Label label = new Label();
      label.setUsedRegx("");
      label.setFlag((long)Integer.parseInt(code));
      label.setText("");
      label.setValue(value);
      return label;
   }

   public static Label run(String value, String code, String chiname) {
      Label label = new Label();
      label.setUsedRegx("");
      label.setFlag((long)Integer.parseInt(code));
      label.setText("");
      label.setValue(value);
      label.setChiname(chiname);
      return label;
   }

   public static Label run(String code) {
      Label label = new Label();
      label.setUsedRegx("");
      label.setFlag((long)Integer.parseInt(code));
      label.setText("");
      label.setValue("true");
      return label;
   }
}
