package org.example;

public class Task implements Runnable{
   private final int num;
   public Task(int n) {
       num = n;
   }
    @Override
    public void run() {
      /*  double a = 10.2f;
        for (int i = 0; i < num; ++i) {
            a = a + 2.2f ;
        }*/
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("The result is " + " by " + Thread.currentThread().getName());
    }
}
