package com.cjbdi.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;

public class HttpRequest {

   public static String sendPost(String url, MultivaluedMapImpl param) {
      try {
         Client e = Client.create();
         URI u2 = new URI(url);
         WebResource webResource = e.resource(u2);
         ClientResponse clientResponse = (ClientResponse)webResource.type("application/x-www-form-urlencoded").post(ClientResponse.class, param);
         String result = (String)clientResponse.getEntity(String.class);
         return result;
      } catch (Exception var7) {
         var7.printStackTrace();
         return "";
      }
   }

   public static String sendPost(String url, JSONObject param) {
      CloseableHttpClient httpClient = HttpClients.createDefault();
      RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000).setSocketTimeout(10000).build();
      CloseableHttpResponse response = null;
      String resultString = "";

      String entity;
      try {
         HttpPost e = new HttpPost(url);
         e.setConfig(requestConfig);
         StringEntity entity1 = new StringEntity(param.toJSONString(), ContentType.APPLICATION_JSON);
         e.setEntity(entity1);
         response = httpClient.execute(e);
         if(response.getStatusLine().getStatusCode() == 200) {
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
         }

         return resultString;
      } catch (Exception var17) {
         var17.printStackTrace();
         entity = "";
      } finally {
         try {
            response.close();
         } catch (IOException var16) {
            var16.printStackTrace();
            return "";
         }
      }

      return entity;
   }
}
