package com.codecrafthub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CodeCraftHubApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodeCraftHubApplication.class, args);
        System.out.println("🚀 CodeCraftHub API Berhasil Berjalan!");
    }
}