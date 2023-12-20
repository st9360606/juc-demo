package com.example.concurrent.test.completablefuture;

import java.util.concurrent.*;

public class CompletableFutureAPIDemo {
    public static void main(String[] args) //throws ExecutionException, InterruptedException, TimeoutException
    {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello Java";
        });
//        System.out.println(completableFuture.get());
//        System.out.println(completableFuture.get(2L,TimeUnit.SECONDS));  //最多等2秒
//        System.out.println(completableFuture.join());
        try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}
//        System.out.println(completableFuture.getNow("timeout:就給備胎值{xxx}")); //如果沒取到就給xxx

        System.out.println(completableFuture.complete("completeValue") + "\t" + completableFuture.join());
    }
}
