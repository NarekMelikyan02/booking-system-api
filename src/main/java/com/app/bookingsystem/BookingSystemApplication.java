package com.app.bookingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BookingSystemApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(BookingSystemApplication.class, args);
    }
}
