package com.example.concurrent.test.basic;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Sleep")
public class Sleep {

    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        t1.start();
        log.debug("t1 state: {}", t1.getState()); //因主線程在t1線程還沒睡眠時就先打印

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("t1 state: {}", t1.getState());
    }
}
