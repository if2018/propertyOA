package com.example.oa.service.impl;

import com.example.oa.bean.HouseSource;
import com.example.oa.dao.HouseSourceDao;
import com.example.oa.service.HouseSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseSourceImpl implements HouseSourceService {

    @Autowired
    private HouseSourceDao houseSourceDao;

    @Override
    public Page<HouseSource> findAll(Pageable pageable) {
        return houseSourceDao.findAll(pageable);
    }

    @Override
    public List<HouseSource> findAll() {
        return houseSourceDao.findAll();
    }

    @Override
    public HouseSource findOne(Integer houseId) {
        return houseSourceDao.findById(houseId).get();
    }

    @Override
    public List<HouseSource> findByHouseIdIn(List<Integer> houseIdList) {
        return houseSourceDao.findByHouseIdIn(houseIdList);
    }

    @Override
    public HouseSource save(HouseSource houseSource) {
        return houseSourceDao.save(houseSource);
    }
}
