package org.example;

public class Task implements Runnable{
   private final int num;
   public Task(int n) {
       num = n;
   }
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("The result is " + " by " + Thread.currentThread().getName());
    }
}
