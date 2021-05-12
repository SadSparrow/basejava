package com.basejava.webapp;


public class MainDeadLock {
    private static final Integer Lock1 = 1;
    private static final Integer Lock2 = 2;

    public static void main(String[] args) throws InterruptedException {
        Thread threadFirst = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " stream is trying to capture " + Lock2);
            print(Lock2, Lock1);
        });
        Thread threadSecond = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " stream is trying to capture " + Lock1);
            print(Lock1, Lock2);
        });

        threadFirst.start();
        threadSecond.start();
    }

    private static void print(Integer a, Integer b) {
        synchronized (a) {
            System.out.println(Thread.currentThread().getName() + " has captured the object " + a + " (method print)");
            synchronized (b) {
                System.out.println(Thread.currentThread().getName() + " has captured the object " + b + " (method print)");
            }
        }
    }
}
