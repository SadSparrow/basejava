package com.basejava.webapp;


public class MainDeadLock {
    private static final Integer Lock1 = 1;
    private static final Integer Lock2 = 2;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> print(Lock2, Lock1)).start();
        new Thread(() -> print(Lock1, Lock2)).start();
    }
    
    private static void print(Integer a, Integer b) {
        System.out.println(Thread.currentThread().getName() + " thread is trying to capture " + a);
        synchronized (a) {
            System.out.println(Thread.currentThread().getName() + " has captured the object " + a + " (method print)");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (b) {
                System.out.println(Thread.currentThread().getName() + " has captured the object " + b + " (method print)");
            }
        }
    }
}
