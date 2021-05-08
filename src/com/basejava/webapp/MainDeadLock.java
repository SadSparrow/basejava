package com.basejava.webapp;


public class MainDeadLock {
    private static final Integer Lock1 = 1;
    private static final Integer Lock2 = 2;

    public static void main(String[] args) throws InterruptedException {
        Thread threadFirst = new Thread(() -> {
            synchronized (Lock1) {
                System.out.println(Thread.currentThread().getName() + " has captured the object " + Lock1);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (Lock2) {
                    System.out.println(Thread.currentThread().getName() + " has captured the object " + Lock2);
                    System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
                }
            }
        });
        Thread threadSecond = new Thread(() -> {
            synchronized (Lock2) {
                System.out.println(Thread.currentThread().getName() + " has captured the object " + Lock2);
                synchronized (Lock1) {
                    System.out.println(Thread.currentThread().getName() + " has captured the object " + Lock1);
                    System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
                }
            }
        });

        threadFirst.start();
        threadSecond.start();
    }
}
