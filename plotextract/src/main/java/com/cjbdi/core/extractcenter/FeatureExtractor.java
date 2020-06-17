package com.cjbdi.core.extractcenter;

import com.cjbdi.core.extractcenter.utils.caseportrait.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.tracesource.Label;
import com.cjbdi.core.extractcenter.utils.PublicFeatureExtract;

import java.util.List;

public class FeatureExtractor {
   private String modelUrl;

   public FeatureExtractor(String modelUrl) {
     this. modelUrl = modelUrl;
   }
   public List<Label> run(CasecauseModel casecauseModel) {
      List<Label> labelList = PublicFeatureExtract.extractPublic(casecauseModel);
      return labelList;
   }




}
