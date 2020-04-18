package com.cjbdi.core.extractcenter.sentence.utils;

import java.util.List;

public abstract class CriminalActExtractor {

   public abstract String extract(String var1);

   public abstract String extract(String var1, List var2);

   protected abstract String doExtract(String var1, List var2);
}
