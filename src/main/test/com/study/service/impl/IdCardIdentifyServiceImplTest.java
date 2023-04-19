package com.study.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author hbc
 * @version 1.0
 * @description 描述
 * @date 2023/4/18 15:35
 */
@SpringBootTest
class IdCardIdentifyServiceImplTest {

    @Autowired
    private IdCardIdentifyServiceImpl idCardIdentifyService;

    @Test
    void test(){
        idCardIdentifyService.IdCardIdentify();
    }

}
