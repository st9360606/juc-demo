package com.example.concurrent.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

import static com.example.concurrent.util.Sleeper.sleep;

@Slf4j(topic = "c.TestSemaphore")
public class TestSemaphore {
    public static void main(String[] args) {
        // 1. 创建 semaphore 对象
        Semaphore semaphore = new Semaphore(3);    //最多允許3個線程許可

        // 2. 10个线程同时运行
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();  //獲得許可
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    log.debug("running...");
                    sleep(1);
                    log.debug("end...");
                } finally {
                    semaphore.release(); //釋放許可
                }
            }).start();
        }
    }
}
