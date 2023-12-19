package com.example.concurrent.test.basic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static com.example.concurrent.util.Sleeper.sleep;

@Slf4j(topic = "c.Park")
public class Park {

    private static void test4() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                log.debug("park...");
                LockSupport.park();
                log.debug("打断状态：{}", Thread.interrupted());
            }
        });
        t1.start();


        sleep(1);
        t1.interrupt();
    }

    private static void test3() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("park...");
            LockSupport.park();    //讓當前線程停下
            log.debug("unpark...");
//            log.debug("打断状态：{}", Thread.currentThread().isInterrupted()); //打斷標記為true
            log.debug("打断状态：{}", Thread.currentThread().interrupted()); //已經重製打斷標記為false

            LockSupport.park(); //打斷標記為TRUE，PARK就會失效
            log.debug("unpark...");

        }, "t1");
        t1.start();

        sleep(1);
        t1.interrupt(); //打斷正在park的線程

    }

    public static void main(String[] args) throws InterruptedException {
        test3();
//        test4();
    }
}
