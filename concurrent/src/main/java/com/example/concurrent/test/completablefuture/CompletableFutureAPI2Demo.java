package com.example.concurrent.test.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletableFutureAPI2Demo {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

//        CompletableFuture.supplyAsync(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("111");
//            return 1;
//        }, threadPool).thenApply(f -> {
//            //測試失敗
//            int i = 10 / 0;
//
//            System.out.println("222");
//            return f + 2;
//        }).thenApply(f -> {
//            System.out.println("333");
//            return f + 3;
//        }).whenComplete((v, e) -> {
//            if (e == null) {
//                System.out.println("----計算結果: " + v);
//            }
//        }).exceptionally(e -> {
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//            return null;
//        });


        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("111");
            return 1;
        }, threadPool).handle((f,e) -> { //就算有異常也可以繼續往下走!!!!!!!!
            //測試失敗
            int i = 10 / 0;
            System.out.println("222");
            return f + 2;
        }).handle((f,e) -> {
            System.out.println("333");
            return f + 3;
        }).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println("----計算結果: " + v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        });

        System.out.println(Thread.currentThread().getName() + "-----主線程先去忙其他任務");

        threadPool.shutdown();
    }
}
