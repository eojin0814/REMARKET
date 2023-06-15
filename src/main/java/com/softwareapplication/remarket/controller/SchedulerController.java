package com.softwareapplication.remarket.controller;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SchedulerController {

    @Scheduled(fixedDelay=10000)
    public void init() {
        System.out.println("동작여부 확인");
    }

}
