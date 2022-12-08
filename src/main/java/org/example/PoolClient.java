package org.example;


import java.util.concurrent.atomic.AtomicInteger;

public class PoolClient {
    static AtomicInteger ost = new AtomicInteger();

    public static void main(String[] args) throws Exception {
        final int numOfThreads = 10;
        final int maxNumOfTasks = 100;
        long startTime = System.currentTimeMillis();

        // Создание пула потоков
        PoolManager poolManager = new PoolManager(numOfThreads, maxNumOfTasks);
        for (int i = 0; i < maxNumOfTasks; ++i)
            poolManager.execute(new Task());
        poolManager.waitUntilAllTaskFinished();
        poolManager.shutdown();

        // Тестирование обычных потоков
       /* BlockingQueue taskQueue = new ArrayBlockingQueue(maxNumOfTasks);

        ost.set(maxNumOfTasks);
        for (int i = 0; i < maxNumOfTasks; ++i){
            taskQueue.add(new Task(new Random().nextInt(5000-1000) + 1000));
        }
        long threadCreateTime = System.currentTimeMillis();
        for (int i = 0; i < maxNumOfTasks; ++i){
            Thread thread = new Thread((Runnable) taskQueue.take());
            thread.start();
        }
        long threadEndTime = System.currentTimeMillis() - threadCreateTime;
        while(ost.get() != 0) {
           Thread.sleep(1);
        }
        System.out.println("Create threads: "  + threadEndTime);
        */

        /* Создание переменных для мониторинга выделяемой памяти и времени выполнения */
        long usedBytes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long endTime = System.currentTimeMillis() - startTime;
        printInfo(usedBytes, endTime);
    }

    public static void printInfo(long bytes, long time) {
        System.out.println("Execution time: " + time + " ms\n" +
                "Total memory used: " + bytes / 1048576 + "mb");

    }
}
