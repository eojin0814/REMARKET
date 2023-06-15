package com.softwareapplication.remarket.controller;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@EnableScheduling
public class SchedulerController {

    @Scheduled(fixedDelay=600000)
    public void init() {
        Date date = new Date();
        System.out.println(date + "동작여부 확인");
    }

}
