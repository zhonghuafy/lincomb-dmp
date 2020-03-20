package com.lincomb.dmp.service.aircat.impl;

import com.lincomb.dmp.service.aircat.AircatService;
import com.lincomb.dmp.util.FTPUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AircatServiceImpl implements AircatService {

    private Logger log = LoggerFactory.getLogger(AircatServiceImpl.class);

    @Override
    public void synchAircatInfo() throws Exception {
        FTPUtil ftpUtil = new FTPUtil("114.141.173.147", 21, "aircat", "Rya2yocnvI$zcMEbQfKqGa");
        FTPClient ftpClient = ftpUtil.getFTPClient();
        ftpClient.getStatus();
    }
}
