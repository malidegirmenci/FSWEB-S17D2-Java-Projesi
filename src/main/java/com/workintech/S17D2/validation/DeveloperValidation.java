package com.workintech.S17D2.validation;

import com.workintech.S17D2.model.Developer;

import java.util.Map;

public class DeveloperValidation {
    public static boolean isDeveloperExist(Map<Integer, Developer> developers, int id){
        return developers.containsKey(id);
    }
}
