package com.cjbdi.core.developcenter.utils;

import java.util.HashMap;
import java.util.Map;

public class MoneyBasic {
    public String factText;
    public String standeffectmoney;
    public String standinvalidmoney;
    public String totalmoney;
   /* public String extracteffectmoney;
    public String extractinvalidmoney;*/
    public String filename;
    public Map<String, String> map;

    public MoneyBasic() {
        factText = "";
        standeffectmoney = "";
        standinvalidmoney = "";
        totalmoney = "";
        /*extracteffectmoney  = "";
        extractinvalidmoney = "";*/
        filename = "";
        map=new HashMap<>();
    }
}
