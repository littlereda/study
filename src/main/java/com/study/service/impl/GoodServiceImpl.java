package com.study.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.domain.Good;
import com.study.domain.ShoppingRecord;
import com.study.mapper.GoodMapper;
import com.study.mapper.ShoppingRecordMapper;
import com.study.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author hbc
 * @description: TODO
 * @date 2023/7/11 15:00
 */
@Service
@RequiredArgsConstructor
public class GoodServiceImpl extends ServiceImpl<GoodMapper,Good> implements GoodService {

    private final GoodMapper goodMapper;

    private final ShoppingRecordMapper shoppingRecordMapper;

    @Override
    public void add(Good good) {
        goodMapper.insert(good);
    }

    @Override
    @Transactional
    public void update(Long id) {
        Good good = goodMapper.selectById(id);
        if(good.getNum() > 0){
            good.setNum(good.getNum() - 1);
            goodMapper.updateById(good);
            ShoppingRecord shoppingRecord = new ShoppingRecord();
            shoppingRecord.setGoodId(id);
            shoppingRecord.setNum(1);
            shoppingRecord.setTime(new Date());
            shoppingRecordMapper.insert(shoppingRecord);
        }
    }
}
