package com.example.concurrent.test.completablefuture;

import java.util.concurrent.*;

public class FutureThreadPoolDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        m1(); 耗時較長

        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        long startTime = System.currentTimeMillis();
        FutureTask<String> futureTask1 = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "take1 over";
        });
        threadPool.submit(futureTask1);

        FutureTask<String> futureTask2 = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "take2 over";
        });
        threadPool.submit(futureTask2);

        //加了獲取結果還是比m1()快
        System.out.println(futureTask1.get());
        System.out.println(futureTask2.get());

        FutureTask<String> futureTask3 = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "take3 over";
        });
        threadPool.submit(futureTask3);

        long endTime = System.currentTimeMillis();
        System.out.println("---------costTime: "+(endTime-startTime)+" 毫秒" );
        System.out.println(Thread.currentThread().getName()+"\t -------end");
    }

    private static void m1(){
        long startTime = System.currentTimeMillis();
        try {TimeUnit.MILLISECONDS.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
        try {TimeUnit.MILLISECONDS.sleep(300);} catch (InterruptedException e) {e.printStackTrace();}
        try {TimeUnit.MILLISECONDS.sleep(300);} catch (InterruptedException e) {e.printStackTrace();}
        long endTime = System.currentTimeMillis();
        System.out.println("---------costTime: "+(endTime-startTime)+" 毫秒" );
        System.out.println(Thread.currentThread().getName()+"\t -------end");
    }
}
