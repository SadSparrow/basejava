package com.basejava.webapp;


public class MainDeadLock {
    private static final Integer Lock1 = 1;
    private static final Integer Lock2 = 2;

    public static void main(String[] args) throws InterruptedException {
        Thread threadFirst = new Thread(() -> {
            synchronized (Lock1) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " stream is trying to capture " + Lock2);
                print(Lock2);
            }
        });
        Thread threadSecond = new Thread(() -> {
            synchronized (Lock2) {
                System.out.println(Thread.currentThread().getName() + " stream is trying to capture " + Lock1);
                print(Lock1);
            }
        });

        threadFirst.start();
        threadSecond.start();
    }

    private static void print(Integer i) {
        synchronized (i) {
            System.out.println(Thread.currentThread().getName() + " has captured the object " + i + " (method print)");
        }
    }
}
