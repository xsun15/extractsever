package com.cjbdi.core.configurecentent.callinterface;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.callinterface.InterfaceModel;
import com.cjbdi.core.configurecentent.callinterface.InterfacePortrait;

public class InterfaceConfig {

   private InterfaceModel interfaceModel;
   private InterfacePortrait interfacePortrait;
   private InterfaceCivil interfaceCivil;


   public InterfaceConfig() {
      String place = BeanFactoryConfig.configPlace.getOthersConfigPlace().getInterfaceurl();
      String feature = "modelextract";
      this.interfaceModel = new InterfaceModel(feature, place);
      feature = "portraitextract";
      this.interfacePortrait = new InterfacePortrait(feature, place);
      feature = "civilextract";
      this.interfaceCivil = new InterfaceCivil(feature, place);
   }

   public InterfaceModel getInterfaceModel() {
      return this.interfaceModel;
   }

   public void setInterfaceModel(InterfaceModel interfaceModel) {
      this.interfaceModel = interfaceModel;
   }

   public InterfacePortrait getInterfacePortrait() {
      return this.interfacePortrait;
   }

   public void setInterfacePortrait(InterfacePortrait interfacePortrait) {
      this.interfacePortrait = interfacePortrait;
   }

   public InterfaceCivil getInterfaceCivil() {
      return interfaceCivil;
   }

   public void setInterfaceCivil(InterfaceCivil interfaceCivil) {
      this.interfaceCivil = interfaceCivil;
   }
}
