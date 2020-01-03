package com.cn.service;

import com.alibaba.fastjson.JSON;
import com.cn.VPO.SystemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SystemScheduled {

    @Autowired
    private ISigarConfigService iSigarConfigService;

    private JqWebSocket socket = new JqWebSocket();

    @Scheduled(cron = "*/3 * * * * ?")
    public void systemSch() throws Exception{
        SystemDTO systemDTO = iSigarConfigService.qureySystemInfo();
        String s = JSON.toJSONString(systemDTO);
        socket.sendMessage(s);
    }
}
