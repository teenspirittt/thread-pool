package org.example;

import java.util.concurrent.BlockingQueue;


public class PoolThread implements Runnable {
    private Thread thread = null;
    private BlockingQueue taskQueue = null;
    private boolean isStopped = false;


    public PoolThread(BlockingQueue queue) {
        taskQueue = queue;

    }

    @Override
    public void run() {
        this.thread = Thread.currentThread();
        while (!isStopped) {
            try {
                Runnable runnable = (Runnable) taskQueue.take();
                runnable.run();
                synchronized (PoolManager.taskCounter) {
                    System.out.println(PoolManager.taskCounter);
                    PoolManager.taskCounter--;
                }
            } catch (Exception e) {

            }
        }
    }

    public synchronized void doStop() {
        isStopped = true;
        System.out.println("potok " + this.thread.getName() + "umer");
        this.thread.interrupt();
    }

    public synchronized boolean isStopped() {
        return isStopped;
    }
}


