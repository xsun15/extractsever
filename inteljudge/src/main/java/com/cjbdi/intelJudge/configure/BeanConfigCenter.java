package com.cjbdi.intelJudge.configure;

import org.elasticsearch.client.transport.TransportClient;

public class BeanConfigCenter {
	public static ConfigPlace configPlace;
	public static Bagwords bagwords;
	public static ESClient esclient;

	public static void init() {
		configPlace = new ConfigPlace();
		bagwords = new Bagwords();
		esclient = new ESClient();
	}
}
