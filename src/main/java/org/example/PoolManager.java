package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class PoolManager {

    private BlockingQueue<Task> taskQueue = null;
    private final List<PoolThread> runnables = new ArrayList<>();
    private boolean isStopped = false;
    public static AtomicInteger taskCounter = new AtomicInteger();

    public PoolManager(int numOfThreads, int maxNumOfTasks) {
        taskQueue = new ArrayBlockingQueue(maxNumOfTasks);
        taskCounter.set(maxNumOfTasks);

       /* Добавление заданий в очередь из которой потоки будут забирать задания,
          размер очереди равен количеству созданных потоков */
        for (int i = 0; i < numOfThreads; ++i)
            runnables.add(new PoolThread(taskQueue));

        // Запуск выполнения доступных заданий
        for (PoolThread runnable : runnables) {
            new Thread(runnable).start();
        }
    }


    /* Runnable помещается в очередь ожидая исключения из очереди */
    public synchronized void execute(Task task) {
        if (this.isStopped) throw
                new IllegalStateException("Thread pool is stopped");
        this.taskQueue.offer(task);
    }

    /* Остановка всех потоков, вызывая для каждого потока метод PoolThread.doStop() */
    public synchronized void shutdown() {
        this.isStopped = true;
        for (PoolThread runnable : runnables) {
            runnable.doStop();
        }
    }


    /* Усыпляет главный поток(main) пока не выполнятся все задания в очереди */
    public synchronized void waitUntilAllTaskFinished() {
        while (!(taskCounter.get() == 0 && taskQueue.isEmpty())) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
