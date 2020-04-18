package com.cjbdi.core.configurecentent.converlabel.utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CalculateExpression {

   public static boolean run(String expression) {
      ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
      ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("nashorn");

      try {
         Boolean e = Boolean.valueOf(String.valueOf(scriptEngine.eval(expression)));
         return e.booleanValue();
      } catch (ScriptException var4) {
         var4.printStackTrace();
         return false;
      }
   }
}
