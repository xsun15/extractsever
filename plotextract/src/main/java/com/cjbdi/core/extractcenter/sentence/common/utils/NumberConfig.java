package com.cjbdi.core.extractcenter.sentence.common.utils;


import com.cjbdi.core.convertlabelcenter.utils.TraceSource;
import com.cjbdi.core.servercenter.utils.TraceSourceModel;

import java.util.HashMap;
import java.util.Map;

public class NumberConfig {

   public int value = 0;
   public String rule = "";
   public String target = "";
   public int startcolor = 0;
   public int endcolor = 0;
   public String colorTarget = "";
   public String paraName;

   // 提取溯源
   public Map<String, TraceSourceModel> traceSourceMap = new HashMap<>();
}
