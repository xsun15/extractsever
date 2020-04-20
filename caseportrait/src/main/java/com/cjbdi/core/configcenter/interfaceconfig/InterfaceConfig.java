package com.cjbdi.core.configcenter.interfaceconfig;

import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.configcenter.interfaceconfig.utils.InterfaceModel;

public class InterfaceConfig {

   private InterfaceModel interfaceModel;


   public InterfaceConfig() {
      String place = BeanConfigCenter.configPlace.getOthersConfigPlace().getInterfaceurl();
      String feature = "modelextract";
      this.interfaceModel = new InterfaceModel(feature, place);
   }

   public InterfaceModel getInterfaceModel() {
      return this.interfaceModel;
   }

   public void setInterfaceModel(InterfaceModel interfaceModel) {
      this.interfaceModel = interfaceModel;
   }
}
