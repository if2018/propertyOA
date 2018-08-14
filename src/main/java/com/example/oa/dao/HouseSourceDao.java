package com.example.oa.dao;

import com.example.oa.bean.HouseSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseSourceDao extends JpaRepository<HouseSource,Integer> {
    List<HouseSource> findByHouseIdIn(List<Integer> houseIdList);
}
