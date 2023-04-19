package com.study.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author hbc
 * @version 1.0
 * @description 描述
 * @date 2023/4/13 10:40
 */
@SpringBootTest
class ResultControllerTest {

    @Autowired
    private ResultController resultController;

    @Test
    void getStr() {
        resultController.getStr();
    }

    @Test
    void getObject() {
        resultController.getObject();
    }
}
