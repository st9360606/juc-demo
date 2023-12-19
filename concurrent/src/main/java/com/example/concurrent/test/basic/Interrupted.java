package com.example.concurrent.test.basic;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Interrupted")
public class Interrupted {
    public static void main(String[] args) throws InterruptedException {
//        Interrupted();
//        test1();
        test2();
    }

    public static void Interrupted() throws InterruptedException {
        Thread t1 = new Thread("t1"){
            @Override
            public void run() {
                log.debug("enter sleep...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    log.debug("wake up...");
                    e.printStackTrace();
                }
            }
        };
        t1.start();

        Thread.sleep(1000);  //讓主線程睡眠一秒
        log.debug("interrupt...");
        t1.interrupt();
    }

    //打斷阻塞狀態
    public static void test1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("sleep...");
            try {
                Thread.sleep(5000); // wait, join
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1");

        t1.start();
        Thread.sleep(1000);
        log.debug("interrupt");
        t1.interrupt();
        log.debug("打断标记:{}", t1.isInterrupted()); //如果是sleep wait join 中被打斷，標記則為false
    }

    //打斷正常狀態
    public static void test2() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while(true) {
                boolean interrupted = Thread.currentThread().isInterrupted();
                if(interrupted) {
                    log.debug("被打断了, 退出循环");
                    break;
                }
            }
        }, "t1");
        t1.start();

        Thread.sleep(1000);
        log.debug("interrupt");
        t1.interrupt();
    }
}
