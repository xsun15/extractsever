package com.cjbdi.intelJudge.configure;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ESClient {
	private TransportClient client = null;

	@SuppressWarnings({ "resource", "unchecked" })
	public  ESClient () {
		try {
			String hostname = BeanConfigCenter.bagwords.getEshost();
			int port = Integer.parseInt(BeanConfigCenter.bagwords.getEsport());
			String cluster = BeanConfigCenter.bagwords.getEscluster();
			Settings settings = Settings.builder()
					.put("cluster.name", cluster).build();
			this.client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new TransportAddress(InetAddress.getByName(hostname), port));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TransportClient getClient() {
		return client;
	}

	public void setClient(TransportClient client) {
		this.client = client;
	}
}
