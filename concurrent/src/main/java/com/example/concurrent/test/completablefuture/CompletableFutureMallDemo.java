package com.example.concurrent.test.completablefuture;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

//需求: 找到Mysql_入門基礎這本書在各大NetMall的價格各是多少
public class CompletableFutureMallDemo {
    static List<NetMall> list = Arrays.asList(
            new NetMall("jd"),
            new NetMall("taobao"),
            new NetMall("Taipei"),
            new NetMall("Japan"),
            new NetMall("shoppe")
    );

    public static List<String> getPrice(List<NetMall> list, String productName) {
        return list.stream()
                .map(netMall ->
                        String.format(productName + " in %s price is %.2f",
                                netMall.getNetMallName(),
                                netMall.calcPrice(productName)))
                .collect(Collectors.toList());
    }

    public static List<String> getPriceByCompletableFuture(List<NetMall> list, String productName) {
        return list.stream().map(netMall -> CompletableFuture.supplyAsync(() ->
                        String.format(productName + " in %s price is %.2f",
                                netMall.getNetMallName(),
                                netMall.calcPrice(productName)))).collect(Collectors.toList())  //List<CompletableFuture> 要再轉成 List<String>
                .stream().map(CompletableFuture::join).collect(Collectors.toList()); //List<String>
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<String> list = getPrice(CompletableFutureMallDemo.list, "Mysql_入門基礎");
        for (String element : list) {
            System.out.println(element);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("---------costTime: " + (endTime - startTime) + " 毫秒"); //需要5秒.....

        System.out.println("\n-----------以下是用CompletableFuture進行-----------\n");

        long startTime2 = System.currentTimeMillis();
        List<String> list2 = getPriceByCompletableFuture(CompletableFutureMallDemo.list, "Mysql_入門基礎");
        for (String element : list2) {
            System.out.println(element);
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println("---------costTime: " + (endTime2 - startTime2) + " 毫秒"); //需要1秒即可.....

    }

}


class NetMall {
    @Getter
    private String netMallName;

    public NetMall(String netMallName) {
        this.netMallName = netMallName;
    }

    public double calcPrice(String productName) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }
}