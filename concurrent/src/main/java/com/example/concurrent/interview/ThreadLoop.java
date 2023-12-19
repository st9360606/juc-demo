package com.example.concurrent.interview;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadLoop {
    //通过Lock锁来保证线程的访问的互斥
    private static Lock lock = new ReentrantLock();

    //通过state的值对线程数量（本例子用的是3个线程）取模跟线程编号对比，来确定是否是该线程进行打印
    private static int state = 0;

    //线程数量
    private static int threadCount = 3;

    public static void main(String[] args) {
        //创建 三个线程并start开启线程，分别传进去线程编号
        new MyThread(0).start();
        new MyThread(1).start();
        new MyThread(2).start();
    }

    static class MyThread extends Thread {
        //线程编号，很重要，用来确定该线程应该打印哪个字母
        private int threadNum;

        //空构造
        public MyThread() {

        }

        //构造函数
        public MyThread(int threadNum) {
            this.threadNum = threadNum;
        }

        @Override
        public void run() {
            //定义i=0,while循环进行循环10次操作
            int i = 0;
            while (i < 10) {
                //try finally進行unlock操作
                try {
                    //线程进来，锁空闲的话，获得锁
                    lock.lock();

                    //递增的state，state取模，判断是否该当前线程打印
                    while (state % threadCount == this.threadNum) {
                        //进行判断，0号线程打印A，1号线程打印B，2号线程打印C
                        if (threadNum == 0) {
                            System.out.println(this.threadNum + "==A");
                        } else if (threadNum == 1) {
                            System.out.println(this.threadNum + "==B");
                        } else if (threadNum == 2) {
                            System.out.println(this.threadNum + "==C");
                        }

                        //state值+1，每次线程进来打印一个字母后，state值+1,
                        state++;
                        //i++操作是为了打印10次后，跳出最外层while循环
                        i++;
                    }
                } finally {
                    // 释放锁，unlock()操作放在finally块中，保证最后肯定会释放锁
                    lock.unlock();
                }
            }
        }
    }

}
