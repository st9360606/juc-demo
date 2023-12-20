package com.example.concurrent.test.completablefuture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureJoinAndGetDemo {
    public static void main(String[] args) {
        Student student = new Student();
        student.setId(12).setStudentName("kurt").setMajor("english");

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            return "hello";
        });
//        System.out.println(completableFuture.get());
//        System.out.println(completableFuture.join());  //不須拋出異常處理，在編譯時才會拋出

    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
class Student{
    private Integer id;
    private String studentName;
    private String major;
}
