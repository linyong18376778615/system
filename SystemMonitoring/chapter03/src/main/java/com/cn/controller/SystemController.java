package com.cn.controller;

import com.cn.VPO.SystemDTO;
import com.cn.service.ISigarConfigService;
import com.cn.service.SystemScheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private ISigarConfigService iSigarConfigService;

    @Autowired
    private SystemScheduled systemScheduled;

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public SystemDTO systemInfo() throws Exception{
//        return systemScheduled.systemSch();
        return iSigarConfigService.qureySystemInfo();
    }
}
