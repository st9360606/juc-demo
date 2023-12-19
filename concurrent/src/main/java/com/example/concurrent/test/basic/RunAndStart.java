package com.example.concurrent.test.basic;

import com.example.concurrent.constant.Constants;
import com.example.concurrent.util.FileReader;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.RunAndStart")
public class RunAndStart {
    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("running...");
//                FileReader.read(Constants.MP4_FULL_PATH);
            }
        };
        System.out.println(t1.getState());
//        t1.run();    //15:33:42.705 c.TestRunAndStart [main] - running...
        t1.start();  //15:32:37.658 c.TestRunAndStart [t1] - running...
        System.out.println(t1.getState());
        log.debug("do other things...");
    }
}
