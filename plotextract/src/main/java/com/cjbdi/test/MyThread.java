package com.cjbdi.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyThread implements Runnable {

   private int ticket = 100;
   private Lock lock = new ReentrantLock();


   public void run() {
      for(int i = 0; i < 10; ++i) {
         try {
            synchronized(this) {
               if(this.ticket > 0) {
                  System.out.println(Thread.currentThread().getName() + "售出  还剩余" + --this.ticket + "张票");
                  Thread.sleep(200L);
               }
            }
         } catch (Exception var5) {
            var5.printStackTrace();
         }
      }

   }

   public static void main(String[] args) {
      ArrayList threadList = new ArrayList();
      MyThread my = new MyThread();
      threadList.add(new Thread(my, "A窗口"));
      threadList.add(new Thread(my, "C窗口"));
      threadList.add(new Thread(my, "窗口C"));
      Iterator e = threadList.iterator();

      Thread thread;
      while(e.hasNext()) {
         thread = (Thread)e.next();
         thread.start();
      }

      try {
         e = threadList.iterator();

         while(e.hasNext()) {
            thread = (Thread)e.next();
            thread.join();
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      System.out.println("Hello");
   }
}
