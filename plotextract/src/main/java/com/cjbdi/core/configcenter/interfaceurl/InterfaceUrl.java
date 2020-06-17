package com.cjbdi.core.configcenter.interfaceurl;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configcenter.BeanFactoryConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.util.HashMap;

public class InterfaceUrl {
	private JSONObject serviceUrl = new JSONObject();

	public InterfaceUrl() {
		String place = BeanFactoryConfig.configPlace.getOthersConfigPlace().getOthers().getString("interfaceUrl");
		try {
			Yaml yaml = new Yaml();
			HashMap<String, HashMap<String, Object>> hashMap = yaml.load(new FileInputStream(place));
			for (String service : hashMap.keySet()) {
				String ip = hashMap.get(service).get("ip").toString();
				JSONObject oneService = new JSONObject();
				for (String url : hashMap.get(service).keySet()) {
					if (!url.equalsIgnoreCase("ip")) {
						oneService.put(url, ip+hashMap.get(service).get(url).toString());
					}
				}
				serviceUrl.put(service, oneService);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JSONObject getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(JSONObject serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
}
