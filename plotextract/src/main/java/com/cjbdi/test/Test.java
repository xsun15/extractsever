package com.cjbdi.test;

import com.cjbdi.test.MyCallable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test {

   public static void main(String[] args) throws ExecutionException, InterruptedException {
      ExecutorService pool = Executors.newFixedThreadPool(2);
      long startTime1 = System.currentTimeMillis();
      ArrayList callableList = new ArrayList();
      callableList.add(new MyCallable("A"));
      callableList.add(new MyCallable("B"));
      ArrayList futureList = new ArrayList();
      Iterator endTime1 = callableList.iterator();

      while(endTime1.hasNext()) {
         Callable future = (Callable)endTime1.next();
         futureList.add(pool.submit(future));
      }

      endTime1 = futureList.iterator();

      while(endTime1.hasNext()) {
         Future future1 = (Future)endTime1.next();
         System.out.println(future1.get().toString());
      }

      System.out.println("Hello");
      long endTime11 = System.currentTimeMillis();
      System.out.println("代码运行时间：" + (endTime11 - startTime1) + "ms");
      pool.shutdown();
   }
}
