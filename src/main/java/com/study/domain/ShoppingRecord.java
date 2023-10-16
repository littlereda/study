package com.study.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.io.Serializable;

/**
 *
 * @author hbc
 * @date 2023-07-12
 */
@Data
public class ShoppingRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private Long goodId;

    private Integer num;

    private Date time;


}


