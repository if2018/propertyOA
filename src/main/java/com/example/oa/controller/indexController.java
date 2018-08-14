package com.example.oa.controller;


import com.example.oa.VO.AccessRecordVO;
import com.example.oa.VO.HouseSourceVO;
import com.example.oa.VO.RequestVO;
import com.example.oa.bean.AccessRecord;
import com.example.oa.bean.HouseSource;
import com.example.oa.bean.User;
import com.example.oa.dao.HouseSourceDao;
import com.example.oa.exception.BusinessException;
import com.example.oa.myEnum.ErrorCode;
import com.example.oa.service.AccessRecordService;
import com.example.oa.service.HouseSourceService;
import com.example.oa.util.RequestVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/operating")
public class indexController {

    private User auth;

    @Autowired
    private HouseSourceService houseSourceService;

    @Autowired
    private AccessRecordService accessRecordService;


    //根据房产id查询，房产详情页
    @RequestMapping(value = "/houseSourceHouseId")
    public RequestVO houseSourceHouseId(Integer houseId){
        if(houseId == null || houseId == 0){
            throw new BusinessException(ErrorCode.ID_NULL.getCode() + "," + ErrorCode.ID_NULL.getMsg());
        }else {



        //查询所有访问信息
        //List<AccessRecord> accessRecordList=accessRecordService.findAll();

        //查询所有访问信息对应房产信息的id并加入list集合
        //List<Integer> categoryList=accessRecordList.stream().map(accessRecord -> accessRecord.getHouseId()).collect(Collectors.toList());
        //根据房产的list集合查询所有房产信息
        //List<HouseSource> houseSourcesList=houseSourceService.findByHouseIdIn(categoryList);

        //拼装数据
//        List<HouseSourceVO> houseSourceVOList=new ArrayList<>();
//        for ( HouseSource houseSource: houseSourcesList
//             ) {
//            HouseSourceVO houseSourceVO=new HouseSourceVO();
//
//            houseSourceVO.setHouseId(houseSource.getHouseId());
//            houseSourceVO.setHouseEstate(houseSource.getHouseEstate());
//            houseSourceVO.setHouseNumber(houseSource.getHouseNumber());
//            houseSourceVO.setHouseSize(houseSource.getHouseSize());
//            houseSourceVO.setHouseRemark(houseSource.getHouseRemark());
//            houseSourceVO.setLandlordName(houseSource.getLandlordName());
//            houseSourceVO.setLandlordPhone(houseSource.getLandlordPhone());
//            houseSourceVO.setBroker(houseSource.getBroker());
//
//            List<AccessRecordVO> accessRecordVOList=new ArrayList<>();
//            for (AccessRecord accessRecord : accessRecordList
//                 ) {
//                if(accessRecord.getHouseId().equals(houseSource.getHouseId())){
//                    AccessRecordVO accessRecordVO=new AccessRecordVO();
//                    BeanUtils.copyProperties(accessRecord , accessRecordVO);
//                    accessRecordVOList.add(accessRecordVO);
//                }
//            }
//            houseSourceVO.setAccessRecords(accessRecordVOList);
//
//            houseSourceVO.setSetupTime(houseSource.getSetupTime());
//            houseSourceVO.setUpdateTime(houseSource.getUpdateTime());
//
//            houseSourceVOList.add(houseSourceVO);
//
//        }

        //Page<HouseSource> page1= houseSourceService.findAll(new PageRequest(page,20));

        //根据ID查询访问信息
        List<AccessRecord> accessRecordList=accessRecordService.findByHouseId(houseId);
        List<AccessRecordVO> accessRecordVOList=new ArrayList<>();
        HouseSource houseSource=houseSourceService.findOne(houseId);
        HouseSourceVO houseSourceVO=new HouseSourceVO();

//        houseSourceVO.setHouseId(houseSource.getHouseId());
//        houseSourceVO.setHouseEstate(houseSource.getHouseEstate());
//        houseSourceVO.setHouseNumber(houseSource.getHouseNumber());
//        houseSourceVO.setHouseSize(houseSource.getHouseSize());
//        houseSourceVO.setHouseRemark(houseSource.getHouseRemark());
//        houseSourceVO.setLandlordName(houseSource.getLandlordName());
//        houseSourceVO.setLandlordPhone(houseSource.getLandlordPhone());
//        houseSourceVO.setBroker(houseSource.getBroker());
//        houseSourceVO.setSetupTime(houseSource.getSetupTime());
//        houseSourceVO.setUpdateTime(houseSource.getUpdateTime());

        //spring的beanUtils的copy方法可以省去上面的一大段代码
        BeanUtils.copyProperties(houseSource,houseSourceVO);

        //数据拼装
        for (AccessRecord accessRecord : accessRecordList
                 ) {
                if(accessRecord.getHouseId().equals(houseSource.getHouseId())){
                    AccessRecordVO accessRecordVO=new AccessRecordVO();
                    BeanUtils.copyProperties(accessRecord , accessRecordVO);
                    accessRecordVOList.add(accessRecordVO);
                }
            }
        houseSourceVO.setAccessRecords(accessRecordVOList);


        return RequestVOUtil.success(houseSourceVO);
        }
    }
    //查询全部房产信息,每页20条信息显示
    @RequestMapping(value = "/houseSourceList")
    public RequestVO houseSourceList(@RequestParam(value = "page",defaultValue = "0") Integer page){
        Page<HouseSource> page1= houseSourceService.findAll(new PageRequest(page,20));

        return RequestVOUtil.success(page1);
    }
    //添加房源信息
    @PostMapping(value = "/setupData")
    public String setupDate(HouseSource houseSource){
//        System.out.println(houseEstate+" "+houseNumber +" " + houseSize +"  "+ totalPrice+" " +houseRemark
//        +" " +landlordName +" "+landlordPhone +" "+accessRecord);

        //System.out.println(houseSource.getHouseEstate());

        //HouseSource houseSource=new HouseSource();
        houseSource.setBroker(auth.getName());
        System.out.println(auth.getName());
        houseSourceService.save(houseSource);

        return "添加成功！";
    }
    //查询当前用户所有信息
    @RequestMapping(value = "/current_user")
    public User CurrentUser(){
        auth= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //System.out.println(auth.getUserId());
        return auth;
    }
    //根据id查询房产信息，房产详情页
//    @RequestMapping(value = "/getHouseDetails")
//    public HouseSource GetHouseDetails(Integer userId) throws BusinessException {
//
//        HouseSource houseSource =houseSourceService.findOne(userId);
//
//        return houseSource;
//    }

}
