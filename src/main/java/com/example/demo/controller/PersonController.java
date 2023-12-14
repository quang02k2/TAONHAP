package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import com.google.gson.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @PostMapping(value = "/them", produces = MediaType.APPLICATION_JSON_VALUE)
    public String themTinhTrangHoc(@RequestBody String tinhTrang){
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) {
                return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
            }
        }).create();

        Person tinhTrangHocMoi = gson.fromJson(tinhTrang, Person.class);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(tinhTrangHocMoi);

        constraintViolations.forEach(x -> {
            System.out.println(x.getMessage());
        });

        if (constraintViolations.size() == 0){
            boolean isCheck = personService.themLoaiKhoaHoc(tinhTrangHocMoi);
            if(isCheck){
                return "Them thanh cong";
            }
        }

        return "Them that bai";
    }


    @DeleteMapping("/xoa")
    public String xoaTinhTrangHoc(int tinhTrangHocID){
        boolean isCheck = personService.xoaTinhTrang(tinhTrangHocID);
        if (isCheck){
            return "Xoa thanh cong";
        }

        return "Xoa that bai";
    }

    @GetMapping("/hienThi")
    public List<Person> getTinhTrangHoc(){
        return personService.hienThiTinhTrang();
    }

}
