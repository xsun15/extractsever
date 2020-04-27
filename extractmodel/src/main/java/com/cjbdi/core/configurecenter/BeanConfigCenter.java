package com.cjbdi.core.configurecenter;

import com.cjbdi.core.configurecenter.configplace.ConfigPlace;
import com.cjbdi.core.configurecenter.loadmodel.CasecauseLoadModel;
import com.cjbdi.core.configurecenter.loadmodel.CasecauseModel;

public class BeanConfigCenter {
	public static ConfigPlace configPlace;
	public static CasecauseModel casecauseModel;
	public static void init() {
		configPlace = new ConfigPlace();
		casecauseModel = new CasecauseModel();
	}
}
