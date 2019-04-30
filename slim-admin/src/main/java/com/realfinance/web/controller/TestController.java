package com.realfinance.web.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.realfinance.core.base.web.impl.BaseController;
import com.realfinance.web.entity.Test;
import com.realfinance.web.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.SQLException;

@Controller
@RequestMapping("test")
public class TestController extends BaseController {
    @Autowired
    DataSource dataSource;
    @GetMapping("test")
    public @ResponseBody String test(){
        try {
            int insert = Db.use(dataSource).insert(Entity.create("t_test").set("id", 1).set("value", "123"));
            return Convert.toStr(insert);
        } catch (SQLException e) {
            return "fail";
        }
    }

    @Autowired
    TestService testService;

    @GetMapping("testTk")
    public @ResponseBody String testTk(String id,String value){
        Test test = new Test();
        test.setId(Convert.toInt(id));
        test.setValue(value);
        testService.insertSelective(test);
        return "success";
    }

}
