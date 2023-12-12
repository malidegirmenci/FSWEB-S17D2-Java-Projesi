package com.workintech.S17D2.model;


import com.workintech.S17D2.tax.Taxable;

public class DeveloperFactory {
    public static Developer createDeveloper(Developer developer, Taxable taxable){
        Developer createdDeveloper = null;
        if(developer.getExperience().name().equalsIgnoreCase(Experience.JUNIOR.name())){
            createdDeveloper = new JuniorDeveloper(developer.getId(), developer.getName(),
                    (developer.getSalary() - (developer.getSalary() * taxable.getSimpleTaxRate())));
        } else if (developer.getExperience().name().equalsIgnoreCase(Experience.MID.name())) {
            createdDeveloper = new MidDeveloper(developer.getId(), developer.getName(),
                    (developer.getSalary() - (developer.getSalary()* taxable.getMiddleTaxRate())));
        }else if (developer.getExperience().name().equalsIgnoreCase(Experience.SENIOR.name())){
            createdDeveloper = new MidDeveloper(developer.getId(), developer.getName(),
                    (developer.getSalary() - (developer.getSalary()* taxable.getUpperTaxRate())));
        }
        return createdDeveloper;
    }
}
