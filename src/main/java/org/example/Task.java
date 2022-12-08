package org.example;

import java.util.Random;

public class Task implements Runnable {
    private final int num;

    public Task(int n) {
        num = n;
    }

    public Task() {
        num = 0;
    }

    @Override
    public void run() {
        try {
            // Искуственная нагрузка в виде "усыпления" потока на 2 секунды
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        /*synchronized (PoolClient.ost) {
            PoolClient.ost.getAndDecrement();
        }*/

    }
}
