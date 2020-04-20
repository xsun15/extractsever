package com.cjbdi.core.configcenter.utils;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.util.CommonTools;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.util.*;

public class YamlPropertySourceFactoryUser extends DefaultPropertySourceFactory {

   public static List loadConfig(String name, String sourceName) {
      if(!sourceName.endsWith(".yml") && !sourceName.endsWith(".yaml")) {
         return new ArrayList();
      } else {
         ClassPathResource res = new ClassPathResource(sourceName);
         EncodedResource resource = new EncodedResource(res, "UTF-8");
         Properties propertiesFromYaml = loadYml(resource);
         PropertiesPropertySource propertySource = new PropertiesPropertySource(sourceName, propertiesFromYaml);
         return getValue(name, JSONObject.toJSONString(propertySource.getSource()));
      }
   }

   private static Properties loadYml(EncodedResource resource) {
      try {
         YamlPropertiesFactoryBean e = new YamlPropertiesFactoryBean();
         e.setResources(new Resource[]{resource.getResource()});
         e.afterPropertiesSet();
         return e.getObject();
      } catch (Exception var2) {
         var2.printStackTrace();
         return new Properties();
      }
   }

   private static List getValue(String name, String content) {
      Object list = new ArrayList();
      HashMap keyValue = new HashMap();
      JSONObject jsonObject = JSONObject.parseObject(content);
      Iterator var5 = jsonObject.keySet().iterator();

      String key;
      while(var5.hasNext()) {
         key = (String)var5.next();
         String rule = "^" + name;
         if(CommonTools.ismatch(key.trim(), rule)) {
            if(key.trim().length() <= name.length()) {
               keyValue.put(key, jsonObject.getString(key).trim());
            } else if(key.trim().length() > name.length() && (key.trim().substring(name.length(), name.length() + 1).equals(".") || key.trim().substring(name.length(), name.length() + 1).equals("["))) {
               keyValue.put(key, jsonObject.getString(key).trim());
            }
         }
      }

      if(keyValue != null && keyValue.size() > 0) {
         if(keyValue.size() == 1) {
            var5 = keyValue.keySet().iterator();

            while(var5.hasNext()) {
               key = (String)var5.next();
               if(StringUtils.isNotEmpty((String)keyValue.get(key))) {
                  ((List)list).add(keyValue.get(key));
               }
            }
         } else {
            list = sort(keyValue);
         }
      }

      return (List)list;
   }

   public static List sort(Map map) {
      new ArrayList();
      HashMap map1 = new HashMap();
      Iterator var3 = map.keySet().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         String regx = "\\[(\\d+)\\]";
         String number = CommonTools.matchText(key, regx, 1);
         map1.put(Integer.valueOf(Integer.parseInt(number)), map.get(key));
      }

      List list = sortMapByKey(map1);
      return list;
   }

   public static List<String> sortMapByKey(Map<Integer, String> map) {
      if (map == null || map.isEmpty()) {
         return null;
      }
      Map<Integer, String> sortMap = new TreeMap<Integer, String>(new Comparator<Integer>() {
         @Override
         public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
         }});
      sortMap.putAll(map);
      List<String> list = new ArrayList<>();
      for (Integer key : sortMap.keySet()) {
         list.add(sortMap.get(key));
      }
      return list;
   }
}
