package com.cjbdi.core.configcenter.configplace;

import com.cjbdi.core.configcenter.configplace.utils.EnDecryptConfigPlace;
import com.cjbdi.core.configcenter.configplace.utils.ExtractionConfigPlace;
import com.cjbdi.core.configcenter.configplace.utils.OthersConfigPlace;
import com.cjbdi.core.configcenter.configplace.utils.StructurationConfigPlace;

public class ConfigPlace {

   private ExtractionConfigPlace extractionConfigPlace;
   private StructurationConfigPlace structurationConfigPlace;
   private OthersConfigPlace othersConfigPlace;
   private EnDecryptConfigPlace enDecryptConfigPlace;

   public ConfigPlace() {
      String place = "/configname/configname.yml";
      this.extractionConfigPlace = new ExtractionConfigPlace("extraction", place);
      this.structurationConfigPlace = new StructurationConfigPlace("structuration", place);
      this.othersConfigPlace = new OthersConfigPlace("others", place);
      this.enDecryptConfigPlace = new EnDecryptConfigPlace("endecrypt", place);
   }

   public ExtractionConfigPlace getExtractionConfigPlace() {
      return extractionConfigPlace;
   }

   public void setExtractionConfigPlace(ExtractionConfigPlace extractionConfigPlace) {
      this.extractionConfigPlace = extractionConfigPlace;
   }

   public StructurationConfigPlace getStructurationConfigPlace() {
      return structurationConfigPlace;
   }

   public void setStructurationConfigPlace(StructurationConfigPlace structurationConfigPlace) {
      this.structurationConfigPlace = structurationConfigPlace;
   }

   public OthersConfigPlace getOthersConfigPlace() {
      return othersConfigPlace;
   }

   public void setOthersConfigPlace(OthersConfigPlace othersConfigPlace) {
      this.othersConfigPlace = othersConfigPlace;
   }

   public EnDecryptConfigPlace getEnDecryptConfigPlace() {
      return this.enDecryptConfigPlace;
   }

   public void setEnDecryptConfigPlace(EnDecryptConfigPlace enDecryptConfigPlace) {
      this.enDecryptConfigPlace = enDecryptConfigPlace;
   }
}
