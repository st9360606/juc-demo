package com.example.concurrent.test.completablefuture;

import java.util.concurrent.*;

public class CompletableFutureUseDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        try {
            CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName() + "----come in");
                int result = ThreadLocalRandom.current().nextInt(10);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("-----1秒鐘後出結果:" + result);

//                //測試異常
//                if (result > 1) {
//                    int i = 10 / 0;
//                }

                return result;
            }, threadPool).whenComplete((v, e) -> {
                if (e == null) {
                    System.out.println("-----計算完成，更新系統 UpdateValue: " + v);
                }
            }).exceptionally(e -> {
                e.printStackTrace();
                System.out.println("異常情況: " + e.getCause() + "\t" + e.getMessage());
                return null;
            });

            System.out.println(Thread.currentThread().getName() + "線程先去忙其他任務");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

        //主線程不要立刻結束，否則CompletableFuture默認使用的線程池會立刻關閉:暫停1秒鐘線程
//        try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}

    }

    private static void future1() throws InterruptedException, ExecutionException {
        //有返回值
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "----come in");
            int result = ThreadLocalRandom.current().nextInt(10);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("-----1秒鐘後出結果:" + result);
            return result;
        });
        System.out.println(Thread.currentThread().getName() + "線程先去忙其他任務");
        System.out.println(completableFuture.get());
    }
}
