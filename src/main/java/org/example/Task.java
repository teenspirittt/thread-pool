package org.example;

//todo код завершения
// запоминать время старта
//
public abstract class Task implements Runnable {
    public long time = 0;
    public Task() {
        time = System.currentTimeMillis();
    }

    public  void onFinish() {

    }



    @Override
    public void run() {

    }
}
