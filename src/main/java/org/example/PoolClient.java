package org.example;

import java.util.Random;

public class PoolClient {
    public static void main(String[] args) throws Exception{
        final int numOfThreads = 3;
        final int maxNamOfTasks = 20;
        long startTime = System.currentTimeMillis();
        PoolManager poolManager = new PoolManager(numOfThreads, maxNamOfTasks);

        for(int i = 0; i < maxNamOfTasks; ++i) {
            int taskNo = i;

            poolManager.execute(new Task(new Random().nextInt(100000000 - 90000000) + 90000000));
        }

        poolManager.waitUntilAllTaskFinished();
        poolManager.shutdown();
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println(endTime + "ms");
    }
}
