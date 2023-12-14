package com.example.demo.service;

import com.example.demo.model.Person;
import com.example.demo.reponsitory.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;


    public boolean themLoaiKhoaHoc(Person loaiKhoaHoc) {
        Optional<Person> optionalLoaiKhoaHoc = personRepository.findById(loaiKhoaHoc.getID());

        if(optionalLoaiKhoaHoc.isEmpty()){
            personRepository.save(loaiKhoaHoc);
            return true;
        }
        return false;
    }

    public List<Person> hienThiTinhTrang() {
        return personRepository.findAll();
    }

    public boolean xoaTinhTrang(int tinhTrangHocID) {
        Optional<Person> tinhTrangHoc = personRepository.findById(tinhTrangHocID);
        if (tinhTrangHoc.isPresent()){
            personRepository.deleteById(tinhTrangHocID);
            return true;
        }

        return false;
    }

}
