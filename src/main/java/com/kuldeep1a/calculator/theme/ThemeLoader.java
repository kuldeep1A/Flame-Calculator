package com.kuldeep1a.calculator.theme;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.kuldeep1a.calculator.theme.properties.Theme;
import com.kuldeep1a.calculator.theme.properties.ThemeList;

import java.io.File;
import java.io.IOException;
import java.util.Map;


public class ThemeLoader {
    public ThemeLoader(){
        throw new AssertionError("Constructor nahi chalega!");
    }

    public static Map<String, Theme> loadThemes(){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        try {
            ThemeList themeList = mapper.readValue(new File("src/main/resources/application.yaml"), ThemeList.class);
            return themeList.getThemesAsMap();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
