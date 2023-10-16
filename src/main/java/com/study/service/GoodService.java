package com.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.domain.Good;
import com.study.mapper.GoodMapper;
import org.springframework.stereotype.Service;

/**
 * @author hbc
 * @description: TODO
 * @date 2023/7/11 15:00
 */
public interface GoodService extends IService<Good> {
    /**
     * 添加单条数据
     */
    void add(Good good);

    void update(Long id);
}
