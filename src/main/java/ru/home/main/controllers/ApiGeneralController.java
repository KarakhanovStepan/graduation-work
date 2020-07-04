package ru.home.main.controllers;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.home.main.model.GlobalSetting;
import ru.home.main.model.GlobalSettingRepository;

@RestController
public class ApiGeneralController
{
    @Autowired
    GlobalSettingRepository globalSettingRepository;

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

    @GetMapping("/api/settings")
    public JSONObject settings()
    {
        JSONObject settings = new JSONObject();

        Iterable<GlobalSetting> globalSettings = globalSettingRepository.findAll();

        for(GlobalSetting globalSetting: globalSettings)
        {
            if(globalSetting.getCode().equals("MULTIUSER_MODE"))
            {
                settings.put("MULTIUSER_MODE", globalSetting.getValue().equals("YES"));
            }
            else if(globalSetting.getCode().equals("POST_PREMODERATION"))
            {
                settings.put("POST_PREMODERATION", globalSetting.getValue().equals("YES"));
            }
            else if(globalSetting.getCode().equals("STATISTICS_IS_PUBLIC"))
            {
                settings.put("STATISTICS_IS_PUBLIC", globalSetting.getValue().equals("YES"));
            }
        }

        return settings;
    }
}