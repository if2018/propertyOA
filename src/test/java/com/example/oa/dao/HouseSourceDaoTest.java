package com.example.oa.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HouseSourceDaoTest {

    @Autowired
    private HouseSourceDao houseSourceDao;

    @Test
    public void test1(){
        System.out.println(houseSourceDao.findAll());
    }




}