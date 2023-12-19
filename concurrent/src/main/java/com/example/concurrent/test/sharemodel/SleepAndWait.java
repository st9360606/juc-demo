package com.example.concurrent.test.sharemodel;

import com.example.concurrent.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.SleepAndWait")
public class SleepAndWait {
    static final Object lock = new Object();
    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lock) {
                log.debug("获得锁");
                try {
//                    Thread.sleep(2000);    //等睡完才會釋放鎖
                    lock.wait(20000); //會釋放鎖
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        Sleeper.sleep(1);
        synchronized (lock) {
            log.debug("获得锁");
        }
    }
}
