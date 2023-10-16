package com.study.controller;

import com.study.domain.Good;
import com.study.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hbc
 * @description: TODO
 * @date 2023/7/11 15:01
 */
@RestController
@RequestMapping("/good")
public class GoodController {

    @Autowired
    private GoodService goodService;
    @PostMapping("/add")
    public void add(@RequestBody Good good) {
        goodService.add(good);
    }

    @PutMapping("/update")
    public void update(@RequestParam Long id) {
        goodService.update(id);
    }
}
