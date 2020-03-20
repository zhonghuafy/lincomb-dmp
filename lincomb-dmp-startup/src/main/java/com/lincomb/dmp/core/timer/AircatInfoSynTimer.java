package com.lincomb.dmp.core.timer;

import com.lincomb.dmp.service.aircat.AircatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 空气猫信息同步定时任务
 */
@Component
public class AircatInfoSynTimer {

    private final static Logger log = LoggerFactory.getLogger(AircatInfoSynTimer.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private AircatService aircatService;

    //@Scheduled(cron = "0/1 * * * * ?")
    private void reportCurrentTimeCron() throws Exception {
        long start = System.currentTimeMillis();
        log.info("==========空气猫信息同步定时任务开启：" + dateFormat.format(new Date()));
        aircatService.synchAircatInfo();
        log.info("==========空气猫信息同步定时任务结束：" + dateFormat.format(new Date()));
        long end = System.currentTimeMillis();
        log.info("==========空气猫信息同步定时任务执行时间：" + ((end - start)/1000) + "秒");
    }
}
