package com._360pai.crawler.commons;

public class TransactionDataThread implements Runnable{

    //状态标识
    private static int state = 0;

    private int id;

    private static Object lock = new Object();

    //构造方法
    public TransactionDataThread(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        synchronized (lock) {
            while (state < 30) {
                if (state % 3 == id) {
                    switch (id) {
                        case 0:
                            System.out.println("["
                                    + Thread.currentThread().getName() + "]" + "A");
                            break;

                        case 1:
                            System.out.println("["
                                    + Thread.currentThread().getName() + "]" + "B");
                            break;

                        case 2:
                            System.out.println("["
                                    + Thread.currentThread().getName() + "]" + "C");
                            break;

                        default:
                            break;
                    }
                    state++;
                    lock.notifyAll();
                } else {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        TransactionDataThread t1 = new TransactionDataThread(1);
        TransactionDataThread t0 = new TransactionDataThread(0);
        TransactionDataThread t2 = new TransactionDataThread(2);

        new Thread(t1, "t1").start();
        new Thread(t0, "t0").start();
        new Thread(t2, "t2").start();

        while (Thread.activeCount() > 1);
        System.out.println("Done!");
    }
}
