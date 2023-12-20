package com.example.concurrent.test.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureCombine2Demo {
    public static void main(String[] args) {
        CompletableFuture<Integer> CombineResult = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "-----come in 1");
            return 10;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "-----come in 2");
            return 20;
        }), (x, y) -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "-----come in 3");
            return x + y;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "-----come in 4");
            return 30;
        }), (a, b) -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "-----come in 5");
            return a + b;
        });

        System.out.println("-------主程式結束: END-------");
        System.out.println(CombineResult.join());


        System.out.println();
        System.out.println("-----------簡化後---------------------");
        System.out.println();

        CompletableFuture<Integer> combineResult = CompletableFuture.supplyAsync(() -> 10)
                .thenCombine(CompletableFuture.supplyAsync(() -> 20), (x, y) -> x + y)
                .thenCombine(CompletableFuture.supplyAsync(() -> 30), (a, b) -> a + b);

        System.out.println("-------主程式結束: END-------");
        System.out.println(combineResult.join());


    }
}
