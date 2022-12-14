package org.example;

import java.util.concurrent.BlockingQueue;


public class PoolThread implements Runnable {
    private Thread thread = null;
    public BlockingQueue<Task> taskQueue = null;
    private boolean isStopped = false;


    public PoolThread(BlockingQueue queue) {
        taskQueue = queue;
    }

    /* Выполнение и взятие из очереди задания не занятым потоком*/
    @Override
    public void run() {
        this.thread = Thread.currentThread();
        while (!isStopped) {
            try {
                Task runnable = taskQueue.take();
                runnable.run();
                runnable.onFinish();
                synchronized (PoolManager.taskCounter) {
                    PoolManager.taskCounter.getAndDecrement();
                }
            } catch (Exception e) {
            }
        }
    }

    /* Остановка потока */
    public synchronized void doStop() {
        isStopped = true;
        this.thread.interrupt();
    }

    public synchronized boolean isStopped() {
        return isStopped;
    }
}


