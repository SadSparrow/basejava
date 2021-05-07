package com.basejava.webapp;


public class LessonEleven {
    private static final Integer Lock1 = 1;
    private static final Integer Lock2 = 2;
    private static int inc;
    private static int dec;


    public static void main(String[] args) throws InterruptedException {
        ThreadFirst threadFirst = new ThreadFirst();
        ThreadSecond threadSecond = new ThreadSecond();

        threadFirst.start();
        threadSecond.start();
        threadFirst.join();
        threadSecond.join();

        System.out.println("inc: " + inc + ", dec: " + dec);
    }

    private static class ThreadFirst extends Thread {
        public void run() {
            synchronized (Lock1) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dec();
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }
        }
    }

    private static class ThreadSecond extends Thread {
        public void run() {
            synchronized (Lock2) {
                inc();
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }
        }
    }

    private static void inc() {
        synchronized (Lock1) {
            inc++;
        }
    }

    private static void dec() {
        synchronized (Lock2) {
            dec--;
        }
    }
}
