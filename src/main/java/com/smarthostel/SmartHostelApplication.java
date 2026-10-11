package com.smarthostel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartHostelApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartHostelApplication.class, args);
        System.out.println("=================================================");
        System.out.println("  Smart Hostel Management System Backend Started ");
        System.out.println("  REST API Base URL: http://localhost:8080/api   ");
        System.out.println("=================================================");
    }
}
