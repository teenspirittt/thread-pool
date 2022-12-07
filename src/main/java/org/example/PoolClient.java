package org.example;

import java.util.Random;

public class PoolClient {
    public static void main(String[] args) throws Exception {
        final int numOfThreads = 10;
        final int maxNumOfTasks = 100;
        long startTime = System.currentTimeMillis();
        PoolManager poolManager = new PoolManager(numOfThreads, maxNumOfTasks);

        for (int i = 0; i < maxNumOfTasks; ++i)
            poolManager.execute(new Task(new Random().nextInt(100000000 - 90000000) + 90000000));

        poolManager.waitUntilAllTaskFinished();
        poolManager.shutdown();
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println(endTime + "ms");
    }
}
