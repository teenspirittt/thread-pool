package org.example;

public interface Queue {
    void put(Runnable runnable);
    Runnable take();
}
