package com.cjbdi.intelJudge.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;


public class ElasticSearchConnection {
    private static String ES_HOST1 = "192.168.10.104";
    private static int ES_PORT = 9301;

    @SuppressWarnings({ "resource", "unchecked" })
    public static void main(String[] args) throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", "my-application").build();

        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(ES_HOST1), ES_PORT));
        //创建索引
        createIndex(client);

        //获取文档
        getDoc(client);

        //更新文档
        updateDoc(client);

        //删除文档
        deleteDoc(client);
        client.close();
    }

    /**
     * 创建索引
     * @param client
     */
    public static void createIndex(TransportClient client) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "java编程思想");
        jsonObject.put("publishDate", "2018-10-24");
        jsonObject.put("price", "100");

        IndexResponse response = client.prepareIndex("book", "java","1")
                .setSource(jsonObject.toString(),XContentType.JSON).get();

        System.out.println("索引名称："+response.getIndex());
        System.out.println("文档类型："+response.getType());
        System.out.println("文档ID："+response.getId());
        System.out.println("当前实例状态："+response.status());
    }

    /**
     * 获取文档
     * @param client
     */
    public static void getDoc(TransportClient client) {
        GetResponse response = client.prepareGet("book", "java","1").get();
        System.out.println(response.getSourceAsString());
    }

    /**
     * 更新文档
     * @param client
     */
    public static void updateDoc(TransportClient client) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "java-编程思想-2");
        jsonObject.put("publishDate", "2018-10-25");
        jsonObject.put("price", "100");

        UpdateResponse response = client.prepareUpdate("book", "java","1").setDoc(jsonObject.toString(), XContentType.JSON).get();
        System.out.println("索引名称："+response.getIndex());
        System.out.println("文档类型："+response.getType());
        System.out.println("文档ID："+response.getId());
        System.out.println("当前实例状态："+response.status());
    }

    /**
     * 删除文档
     * @param client
     */
    public static void deleteDoc(TransportClient client) {
        DeleteResponse response = client.prepareDelete("book", "java","1").get();

        System.out.println("索引名称："+response.getIndex());
        System.out.println("文档类型："+response.getType());
        System.out.println("文档ID："+response.getId());
        System.out.println("当前实例状态："+response.status());
    }
}