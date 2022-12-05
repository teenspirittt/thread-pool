package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PoolManager {
    private BlockingQueue taskQueue = null;
    private List<PoolThread> runnables = new ArrayList<>();
    private boolean isStopped = false;
    public static Integer taskCounter;

    public PoolManager(int numOfThreads, int maxNumOfTasks) {
        taskQueue = new ArrayBlockingQueue(maxNumOfTasks);
        taskCounter = maxNumOfTasks;
        for (int i = 0; i < numOfThreads; ++i) {
            PoolThread poolThread = new PoolThread(taskQueue);
            runnables.add(new PoolThread(taskQueue));
        }
        for (PoolThread runnable : runnables) {
            new Thread(runnable).start();
        }
    }

    public synchronized void execute(Runnable task) {
        if (this.isStopped) throw
                new IllegalStateException("Thread pool is stopped");
        this.taskQueue.offer(task);
    }

    public synchronized void shutdown(){
        this.isStopped = true;
        for(PoolThread runnable : runnables){
            runnable.doStop();
        }
    }

    public synchronized void waitUntilAllTaskFinished() {
        while(!(taskCounter == 0 && taskQueue.isEmpty())) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    

}
