package com.example.concurrent.test.sharemodel;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Synchronize")
public class Synchronize {

    static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (lock){
                room.increment();
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (lock){
                room.decrement();
                }
            }
        }, "t2");

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (lock){
                    room.increment();
                }
            }
        }, "t3");

        t1.start();
        t2.start();
//        t3.start();
        t1.join();
        t2.join();
//        t3.join();
        log.debug("{}", room.getCounter());

        //當T1執行完synchronized內的代碼，這時obj從房間走出解開門鎖，喚醒T2線程把鑰使給他，這時t2才有鑰使可以進入房間執行代碼
    }
}

class Room {
    private int counter = 0;

    public  void increment() {
        counter++;
    }
//    public  synchronized void increment() {
//        counter++;
//    }

    public  void decrement() {
        counter--;
    }
//    public  synchronized void decrement() {
//        counter--;
//    }

    public synchronized int getCounter() {
        return counter;
    }
}
