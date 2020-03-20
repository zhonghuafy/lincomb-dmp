package com.lincomb.dmp.service.aircat.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lincomb.dmp.common.constant.aircat.AirConstant;
import com.lincomb.dmp.common.constant.aircat.UrlConstant;
import com.lincomb.dmp.dto.LimitDto;
import com.lincomb.dmp.persistence.mapper.aircat.TPhicommAircatDeviceMapper;
import com.lincomb.dmp.persistence.mapper.aircat.TPhicommAircatInfoLogMapper;
import com.lincomb.dmp.persistence.mapper.system.TMasterDeployInfoMapper;
import com.lincomb.dmp.persistence.model.TMasterDeployInfo;
import com.lincomb.dmp.persistence.model.aircat.TPhicommAircatDevice;
import com.lincomb.dmp.persistence.model.aircat.TPhicommAircatInfoLog;
import com.lincomb.dmp.service.aircat.IPhicommAircatInfoLogService;
import com.lincomb.dmp.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TPhicommAircatInfoLogServiceImpl implements IPhicommAircatInfoLogService {

    @Resource
    public TPhicommAircatInfoLogMapper tPhicommAircatInfoLogMapper;

    @Resource
    public TPhicommAircatDeviceMapper tPhicommAircatDeviceMapper;

    @Resource
    public TMasterDeployInfoMapper tMasterDeployInfoMapper;

    private static Logger log = LoggerFactory.getLogger(TPhicommAircatInfoLogServiceImpl.class);

    /**
     * 批量插入操作
     *
     */
    public boolean batchInserList() throws IOException {

        String salveName = PropertiesUtil.getValue(AirConstant.SERVER_NAME);
        EntityWrapper<TMasterDeployInfo> entityWrapper = new EntityWrapper<TMasterDeployInfo>();
        entityWrapper.eq("slave_status","1");
        List<TMasterDeployInfo> tMasterDeployInfoList = tMasterDeployInfoMapper.selectList(entityWrapper);

        LimitDto limitDto = new LimitDto();
        //从服务器数量
        limitDto.setSlaveNum(tMasterDeployInfoList.size() - 1);
        //服务器名称
        limitDto.setSlaveName(salveName);

        //当前主机排序
        for (TMasterDeployInfo tMasterDeployInfo : tMasterDeployInfoList) {
            if (salveName.equals(tMasterDeployInfo.getSlaveName())) {
                limitDto.setSlaveOrder(tMasterDeployInfo.getOrders());
            }
        }

        //获取所有设备名称
        EntityWrapper<TPhicommAircatDevice> wrapper = new EntityWrapper<>();
        int tPhicommAircatDeviceSize  = tPhicommAircatDeviceMapper.selectCount(wrapper);
        //设备总数量
        limitDto.setMacSize(tPhicommAircatDeviceSize);

        limitDto = LimitUtil.getLimit(limitDto);
        log.info("======>>>limitDto.startSize()={},======>>>limitDto.LimitSize={}",limitDto.getLimitStart(),limitDto.getLimitSize());

        List<TPhicommAircatDevice> tPhicommAircatDeviceList = tPhicommAircatDeviceMapper.listLimit(limitDto.getLimitStart(),limitDto.getLimitSize());

        //如果集合为null  直接返回  不做任何操作
        if (null == tPhicommAircatDeviceList || tPhicommAircatDeviceList.size() == 0) {
            return  true;
        }

        boolean bool = false;
        for (TPhicommAircatDevice m : tPhicommAircatDeviceList) {
            List<TPhicommAircatInfoLog> tPhicommAircatInfoLogList = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("mac", m.getMac());
            map.put("startTime", getStartTime());
            map.put("endTime", DateUtil.getDay() + " 00:00:00");
            String url = UrlConstant.COUNT_DATA_URL;
            //获取数据
            String sjson = (String) HttpUtils.URLPost(url, map).toString();
            try {
                //解析JSON字符串
                JSONObject jsonObj = JSONObject.fromObject(sjson);
                //接收JSON对象里的数组
                JSONArray jsonArr = jsonObj.getJSONArray("data");
                //获取数组长度
                int jsonSize_school = jsonArr.size();

                //通过循环取出数组里的值
                for (int j = 0; j < jsonSize_school; j++){
                    TPhicommAircatInfoLog tPhicommAircatInfoLog = new TPhicommAircatInfoLog();
                    JSONObject jsonTemp = (JSONObject) jsonArr.getJSONObject(j);
                    tPhicommAircatInfoLog.setHcho(jsonTemp.getString("hcho"));
                    tPhicommAircatInfoLog.setPmValue(jsonTemp.getString("pm25"));
                    tPhicommAircatInfoLog.setTemperature(jsonTemp.getString("temperature"));
                    tPhicommAircatInfoLog.setHumidity(jsonTemp.getString("humidity"));
                    tPhicommAircatInfoLog.setCatTime(jsonTemp.getString("time"));
                    //计算得出体感温度
                    String kinect = KinectUtil.kinect(Double.parseDouble(jsonTemp.getString("temperature")), jsonTemp.getString("humidity"));
                    tPhicommAircatInfoLog.setKinect(kinect);
                    tPhicommAircatInfoLog.setPosition(jsonObj.getString("ip"));
                    tPhicommAircatInfoLog.setMac(m.getMac());
                    tPhicommAircatInfoLogList.add(tPhicommAircatInfoLog);
                }
                bool = tPhicommAircatInfoLogMapper.batchInserList(tPhicommAircatInfoLogList);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        if (bool == true) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 前一天凌晨的时间
     */
    public String getStartTime(){
        //取时间
        Date date=new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        //把日期往前减少一天，若想把日期向后推一天则将负数改为正数
        calendar.add(calendar.DATE,-1);
        date=calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String dateString = formatter.format(date);
        return dateString;
    }
}
