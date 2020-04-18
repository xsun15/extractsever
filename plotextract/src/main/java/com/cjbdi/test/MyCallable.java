package com.cjbdi.test;

import java.util.concurrent.Callable;

class MyCallable implements Callable {

   private String oid;


   MyCallable(String oid) {
      this.oid = oid;
   }

   public Object call() throws Exception {
      Thread.sleep(1000L);
      return this.oid + "任务返回的内容";
   }
}
