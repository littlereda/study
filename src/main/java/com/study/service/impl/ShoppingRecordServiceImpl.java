package com.study.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.domain.ShoppingRecord;
import com.study.mapper.ShoppingRecordMapper;
import com.study.service.ShoppingRecordService;
import org.springframework.stereotype.Service;

/**
 * @author hbc
 * @description: TODO
 * @date 2023/7/12 9:45
 */
@Service
public class ShoppingRecordServiceImpl extends ServiceImpl<ShoppingRecordMapper, ShoppingRecord> implements ShoppingRecordService {
}
