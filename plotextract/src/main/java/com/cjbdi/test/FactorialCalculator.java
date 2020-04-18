package com.cjbdi.test;


public class FactorialCalculator {

   private int number;


   public FactorialCalculator(int number) {
      this.number = number;
   }

   public Integer call() {
      Integer result = Integer.valueOf(1);

      try {
         if(this.number != 0 && this.number != 1) {
            for(int e = 2; e <= this.number; ++e) {
               result = Integer.valueOf(result.intValue() * e);
               Thread.sleep(20L);
            }
         } else {
            result = Integer.valueOf(1);
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      System.out.printf("线程:%s," + this.number + "!= %d\n", new Object[]{Thread.currentThread().getName(), result});
      return result;
   }
}
