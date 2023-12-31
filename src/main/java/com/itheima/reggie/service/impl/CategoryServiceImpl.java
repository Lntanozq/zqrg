package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishservice;
    @Autowired
    private SetmealService setmealService;
    /**
     * 根据id删除分类，删除之前判断是否菜单关联
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper=new LambdaQueryWrapper<>();

        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishservice.count(dishLambdaQueryWrapper);
        if (count1>0){
            //已经关联菜品，抛出异常
            throw new CustomException("当前分类项关联菜品,不能删除");

        }
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if (count2>0){
            //已经关联套餐，抛出异常
            throw new CustomException("当前分类项关联套餐,不能删除");
        }

        //正常删除
        super.removeById(id);


    }
}
