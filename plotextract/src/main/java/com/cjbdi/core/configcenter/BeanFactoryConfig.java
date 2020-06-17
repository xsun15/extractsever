package com.cjbdi.core.configcenter;


import com.cjbdi.core.configcenter.casecause.CasecauseConfig;
import com.cjbdi.core.configcenter.checkfeature.CheckFeatureConfig;
import com.cjbdi.core.configcenter.configplace.ConfigPlace;
import com.cjbdi.core.configcenter.convertlabel.ConvertFeatureConfig;
import com.cjbdi.core.configcenter.extractfeature.ExtractFeatureConfig;
import com.cjbdi.core.configcenter.interfaceurl.InterfaceUrl;
import com.cjbdi.core.configcenter.splitdoc.SplitDocConfig;

public class BeanFactoryConfig {
   public static ConfigPlace configPlace;
   public static SplitDocConfig splitDocConfig;
   public static CasecauseConfig casecauseConfig;
   public static ExtractFeatureConfig extractFeatureConfig;
   public static ConvertFeatureConfig convertFeatureConfig;
   public static CheckFeatureConfig checkFeatureConfig;
   public static InterfaceUrl interfaceUrl;
}
