package com.cjbdi.core.extractcenter.sentence.common.utils;


import com.cjbdi.core.servercenter.utils.TraceSourceModel;

import java.util.HashMap;
import java.util.Map;

public class BoolConfig {

   public String rule;
   public String target = "";
   public int startcolor;
   public int endcolor;
   public String colorTarget;

   // 提取溯源
   public Map<String, TraceSourceModel> traceSourceMap = new HashMap<>();
}
