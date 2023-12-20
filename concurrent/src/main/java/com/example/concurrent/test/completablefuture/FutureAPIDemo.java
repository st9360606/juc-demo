package com.example.concurrent.test.completablefuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureAPIDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName()+"\t ------come in");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "take over";
        });
        new Thread(futureTask,"t1").start();

        System.out.println(Thread.currentThread().getName()+"\t------忙其他任務了");


        //以下兩種容易因為程序還沒執行完一直等待，導致阻塞
//        System.out.println(futureTask.get());
//        System.out.println(futureTask.get(3,TimeUnit.SECONDS));

        while (true){
            if(futureTask.isDone()){ //輪詢，耗費資源 建議使用CompletableFuture
                System.out.println(futureTask.get());
                break;
            }else {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("正在處理中......");
            }
        }


    }
}
