package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Setmeal;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SetmealService extends IService<Setmeal> {
    /**
     * 新曾套餐并且保存套餐和菜品的关系
     * @param setmealDto
     */
    public void setWithDish(SetmealDto setmealDto);
    public  void removeWithDish(List<Long> ids);
}
