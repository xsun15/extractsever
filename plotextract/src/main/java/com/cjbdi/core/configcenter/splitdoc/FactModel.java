package com.cjbdi.core.configcenter.splitdoc;

import com.cjbdi.core.configcenter.BeanFactoryConfig;
import com.cjbdi.core.utils.CommonTools;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.util.HashMap;

public class FactModel {
	private BasicModel header;
	private BasicModel body;
	private BasicModel tail;

	public FactModel(String place) {
		Yaml yaml = new Yaml();
		try {
			HashMap<String, HashMap<String, Object>> hashMap = yaml.load(new FileInputStream(place));
			for (String id : hashMap.keySet()) {
				BasicModel basicModel = new BasicModel();
				if (hashMap.get(id).containsKey("name") && hashMap.get(id).get("name") != null)
					basicModel.setName(hashMap.get(id).get("name").toString());
				boolean enDecrypt = BeanFactoryConfig.configPlace.getEnDecryptConfigPlace().getIsEncrypt();
				if (hashMap.get(id).containsKey("rule") && hashMap.get(id).get("rule")!=null )
					basicModel.setRule(CommonTools.object2LP(hashMap.get(id).get("rule"), enDecrypt));
				if (id.equalsIgnoreCase("header")) header = basicModel;
				else if(id.equalsIgnoreCase("body")) body = basicModel;
				else if(id.equalsIgnoreCase("tail")) tail = basicModel;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BasicModel getHeader() {
		return header;
	}

	public void setHeader(BasicModel header) {
		this.header = header;
	}

	public BasicModel getBody() {
		return body;
	}

	public void setBody(BasicModel body) {
		this.body = body;
	}

	public BasicModel getTail() {
		return tail;
	}

	public void setTail(BasicModel tail) {
		this.tail = tail;
	}
}
