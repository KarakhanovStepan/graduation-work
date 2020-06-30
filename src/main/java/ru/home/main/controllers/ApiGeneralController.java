package ru.home.main.controllers;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiGeneralController
{
    @GetMapping("/api/init")
    public JSONObject init()
    {
        JSONObject object = new JSONObject();

        object.put("title", "DevPub");
        object.put("subtitle", "Рассказы разработчиков");
        object.put("phone", "8-800-555-35-35");
        object.put("email", "stepan.karakhanov@mail.ru");
        object.put("copyright", "Караханов Степан");
        object.put("copyrightFrom", "2020");

        return object;
    }
}
