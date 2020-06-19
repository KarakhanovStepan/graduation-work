package ru.home.main.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiGeneralController
{
    @GetMapping("/api/init")
    public int init()
    {
        return 0;
    }

}
