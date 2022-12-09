package org.example;


import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PoolClient {
    public static AtomicInteger ost = new AtomicInteger();
    public static AtomicLong sum = new AtomicLong(0);

    public static void main(String[] args) throws Exception {
        final int numOfThreads = 10;
        final int maxNumOfTasks = 100;
        long startTime = System.currentTimeMillis();

        // Создание пула потоков
        PoolManager poolManager = new PoolManager(numOfThreads, maxNumOfTasks);
        for (int i = 0; i < maxNumOfTasks; ++i)
            poolManager.execute(new Task() {
                @Override
                public void onFinish() {
                    sum.addAndGet(System.currentTimeMillis() - startTime);
                    System.out.println(sum + " " + (System.currentTimeMillis() - startTime));
                }

                @Override
                public void run() {
                    double num = 0.1f;
                    for (int i = 0; i < 10000000; ++i) {
                        num = num + Math.pow(num, 0.5);
                    }

                      /*synchronized (PoolClient.ost) {
                        PoolClient.ost.getAndDecrement();
                        }*/
                }
            });
        poolManager.waitUntilAllTaskFinished();
        poolManager.shutdown();




        /* Создание переменных для мониторинга выделяемой памяти и времени выполнения */
        long usedBytes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long endTime = System.currentTimeMillis() - startTime;

        System.out.println("Num of tasks:" + maxNumOfTasks + "\nTotal execution time: " + endTime + " ms\n" +
                "Delay: " + sum.get() / maxNumOfTasks + "\nTotal memory used: " + usedBytes / 1048576 + "mb");


    }
}


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