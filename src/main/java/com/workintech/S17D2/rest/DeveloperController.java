package com.workintech.S17D2.rest;


import com.workintech.S17D2.dto.DeveloperResponse;
import com.workintech.S17D2.model.Developer;
import com.workintech.S17D2.model.DeveloperFactory;
import com.workintech.S17D2.tax.DeveloperTax;
import com.workintech.S17D2.tax.Taxable;
import com.workintech.S17D2.utils.Constants;
import com.workintech.S17D2.validation.DeveloperValidation;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/developers")
public class DeveloperController {
    Map<Integer, Developer> developers;
    private Taxable taxable;

    @Autowired
    public DeveloperController(DeveloperTax taxable) {
        this.taxable = taxable;
    }

    @PostConstruct
    public void init() {
        developers = new HashMap<>();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeveloperResponse save(@RequestBody Developer developer) {
        Developer createdDeveloper = DeveloperFactory.createDeveloper(developer, taxable);
        developers.put(createdDeveloper.getId(), createdDeveloper);
        return new DeveloperResponse(createdDeveloper, Constants.SUCCEED, HttpStatus.CREATED.value());
    }

    @GetMapping
    public List<Developer> getAll() {
        return developers.values().stream().toList();
    }

    @GetMapping("/{id}")
    public DeveloperResponse getById(@PathVariable("id") Integer id) {
        if (DeveloperValidation.isDeveloperExist(this.developers, id))
            return new DeveloperResponse(this.developers.get(id), Constants.SUCCEED, HttpStatus.OK.value());
        return new DeveloperResponse(null, Constants.NOT_EXIST, HttpStatus.NOT_FOUND.value());
    }

    @PutMapping("/{id}")
    public DeveloperResponse update(@PathVariable("id") Integer id, @RequestBody Developer developer) {
        if (!DeveloperValidation.isDeveloperExist(this.developers, id))
            return new DeveloperResponse(null, Constants.NOT_EXIST, HttpStatus.NOT_FOUND.value());
        developer.setId(id);
        Developer updatedDeveloper = DeveloperFactory.createDeveloper(developer, taxable);
        this.developers.put(updatedDeveloper.getId(), updatedDeveloper);
        return new DeveloperResponse(updatedDeveloper, Constants.SUCCEED, HttpStatus.OK.value());
    }

    @DeleteMapping("/{id}")
    public DeveloperResponse delete(@PathVariable("id") Integer id) {
        Developer removedDeveloper;
        if (!DeveloperValidation.isDeveloperExist(this.developers, id)) {
            return new DeveloperResponse(null, Constants.NOT_EXIST, HttpStatus.NOT_FOUND.value());
        } else {
            removedDeveloper = this.developers.get(id);
            this.developers.remove(id);
        }
        return new DeveloperResponse(removedDeveloper, Constants.SUCCEED, HttpStatus.OK.value());
    }
}
