package com.lincomb.dmp.common.constant.aircat;

/**
 * Created by shiyu.cao on 2018/1/25.
 */
public interface UrlConstant {
    //斐讯接口  mac增量接口调用地址
    public String ACTIVE_MAC_URL = "https://airmonitor.phicomm.com/device/getActiveCatMacs";

    //斐讯接口  根据MAC查询信息接口调用地址
    public String COUNT_DATA_URL = "https://airmonitor.phicomm.com/connectserverv1/countData_day";
}
