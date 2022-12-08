package org.example;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PoolClient {
    static Integer ost;
    public static void main(String[] args) throws Exception {
        final int numOfThreads = 10;
        final int maxNumOfTasks = 1000;
        long startTime = System.currentTimeMillis();
        PoolManager poolManager = new PoolManager(numOfThreads, maxNumOfTasks);

        for (int i = 0; i < maxNumOfTasks; ++i)
            poolManager.execute(new Task(new Random().nextInt(5000-1000) + 1000));

        poolManager.waitUntilAllTaskFinished();
        poolManager.shutdown();

     /*   BlockingQueue taskQueue = new ArrayBlockingQueue(maxNumOfTasks);
        boolean isStopped = true;
        ost = maxNumOfTasks;
        for (int i = 0; i < maxNumOfTasks; ++i){
            taskQueue.add(new Task(new Random().nextInt(5000-1000) + 1000));
        }
        for (int i = 0; i < maxNumOfTasks; ++i){
            Thread thread = new Thread((Runnable) taskQueue.take());
            thread.start();
        }
        while(ost != 0) {
           Thread.sleep(1);
        }*/

        long endTime = System.currentTimeMillis() - startTime;
        System.out.println(endTime + "ms");

    }
}
