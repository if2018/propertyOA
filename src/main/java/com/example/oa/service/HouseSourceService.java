package com.example.oa.service;

import com.example.oa.bean.HouseSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface HouseSourceService {
    //分页查询所有
    Page<HouseSource> findAll(Pageable pageable);
    //不分页查询所有
    List<HouseSource> findAll();
    //根据id查询
    HouseSource findOne(Integer houseId);
    //根据idList查询
    List<HouseSource> findByHouseIdIn(List<Integer> houseIdList);


    //添加数据
    HouseSource save(HouseSource houseSource);

}
