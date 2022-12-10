package org.example;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PoolClient {

    public static AtomicLong sum = new AtomicLong(0);
    public static AtomicInteger ost = new AtomicInteger(0);
    public static void main(String[] args) throws Exception {
        final int numOfThreads = 20;
        final int maxNumOfTasks = 2500;
        long startTime = System.currentTimeMillis();


        // Создание пула потоков
       /* PoolManager poolManager = new PoolManager(numOfThreads, maxNumOfTasks);
        for (int i = 0; i < maxNumOfTasks; ++i)
            poolManager.execute(new Task() {
                @Override
                public void onFinish() {
                    sum.addAndGet(System.currentTimeMillis() - startTime);
                }
                @Override
                public void run() {
                    double num = 0.1f;
                    for (int i = 0; i < 10000000; ++i) {
                        num += num;
                    }

                      synchronized (PoolClient.ost) {
                        PoolClient.ost.getAndDecrement();
                        }
                }
            });
        poolManager.waitUntilAllTaskFinished();
        poolManager.shutdown();*/


        // Тестирование обычных потоков
        BlockingQueue<Task> taskQueue = new ArrayBlockingQueue<Task>(maxNumOfTasks);

        ost.set(maxNumOfTasks);



        for (int i = 0; i < maxNumOfTasks; ++i){
            execute(new Task() {
                @Override
                public void onFinish() {
                    sum.addAndGet(System.currentTimeMillis() - startTime);
                }

                @Override
                public void run() {
                    double num = 0.1f;
                    for (int i = 0; i < 10000000; ++i) {
                        num += num;
                    }
                    PoolClient.ost.getAndDecrement();
                }
            });



        }


        while(ost.get() != 0) {
           Thread.sleep(1);
        }



        /* Создание переменных для мониторинга выделяемой памяти и времени выполнения */
        long usedBytes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long endTime = System.currentTimeMillis() - startTime;

        System.out.println("Num of tasks:" + maxNumOfTasks + "\nTotal execution time: " + endTime + " ms\n" +
                "Delay: " + sum.get() / maxNumOfTasks + "\nTotal memory used: " + usedBytes / 1048576 + "mb");


    }

    public static void execute(Task task) {
        new Thread((() -> {
            task.run();
            task.onFinish();
        })).start();
    }
}


