package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class PoolManager  {

    private BlockingQueue taskQueue = null;
    private final List<PoolThread> runnables = new ArrayList<>();
    private boolean isStopped = false;
    public static AtomicInteger taskCounter  = new AtomicInteger();

    public PoolManager(int numOfThreads, int maxNumOfTasks) {
        taskQueue = new ArrayBlockingQueue(maxNumOfTasks);
        taskCounter.set(maxNumOfTasks);

        for (int i = 0; i < numOfThreads; ++i)
            runnables.add(new PoolThread(taskQueue));

        for (PoolThread runnable : runnables)
            new Thread(runnable).start();


    }



    public synchronized void execute(Runnable task) {
        if (this.isStopped) throw
                new IllegalStateException("Thread pool is stopped");
        this.taskQueue.offer(task);
    }

    public synchronized void shutdown() {
        this.isStopped = true;
        for (PoolThread runnable : runnables) {
            runnable.doStop();
        }
    }

    public synchronized void waitUntilAllTaskFinished() {
        System.out.println(taskCounter + "SDGFSDF");
        while (!(taskCounter.get() == 0 && taskQueue.isEmpty())) {
            try {
                /*System.out.println(taskCounter + "SDGFSDF");
                System.out.println(taskQueue.size() + " size of TQ");
                System.out.println(runnables.size() + "run size");*/
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
