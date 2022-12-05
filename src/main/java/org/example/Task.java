package org.example;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Task implements Runnable{
   private final int num;
   int [][] matrix;
   public Task(int n) {
       num = n;
   }
    @Override
    public void run() {
        double a = 10.2f;
        for (int i = 0; i < num; ++i) {
            a = a + 2.2f ;
        }
        System.out.println("The result is " + a + " by " + Thread.currentThread().getName());
    }
}
