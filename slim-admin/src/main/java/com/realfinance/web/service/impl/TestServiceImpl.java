package com.realfinance.web.service.impl;

import com.realfinance.core.base.web.impl.BaseService;
import com.realfinance.web.entity.Test;
import com.realfinance.web.mapper.TestMapper;
import com.realfinance.web.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl extends BaseService<TestMapper, Test> implements TestService {

}
