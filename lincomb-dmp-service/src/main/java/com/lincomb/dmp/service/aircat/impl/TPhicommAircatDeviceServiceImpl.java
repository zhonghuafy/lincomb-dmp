package com.lincomb.dmp.service.aircat.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lincomb.dmp.common.constant.aircat.UrlConstant;
import com.lincomb.dmp.persistence.mapper.aircat.TPhicommAircatDeviceMapper;
import com.lincomb.dmp.persistence.mapper.aircat.TPhicommAircatInfoMapper;
import com.lincomb.dmp.persistence.model.aircat.TPhicommAircatDevice;
import com.lincomb.dmp.persistence.model.aircat.TPhicommAircatInfo;
import com.lincomb.dmp.service.aircat.IPhicommAircatDeviceService;
import com.lincomb.dmp.util.HttpUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TPhicommAircatDeviceServiceImpl extends ServiceImpl<TPhicommAircatDeviceMapper, TPhicommAircatDevice> implements IPhicommAircatDeviceService{
	@Resource
	public TPhicommAircatDeviceMapper tPhicommAircatDeviceMapper;

	@Resource
	public TPhicommAircatInfoMapper tPhicommAircatInfoMapper;

	private static Logger log = LoggerFactory.getLogger(TPhicommAircatDeviceServiceImpl.class);

	@SuppressWarnings("static-access")
	@Override
	public boolean batchInsertService(String time) throws IOException {
		HttpUtils httpUtils = new HttpUtils();
		String url = UrlConstant.ACTIVE_MAC_URL;

		Map<String, Object> map = new HashMap<>();
		map.put("time", time);

		StringBuffer stringBuffer = httpUtils.URLGet(url, map);
		//用来接收解析的JSON字符串
		JSONObject  jsonObj_school;
		//用来接收JSON对象里的数组
		JSONArray  jsonArr_school;

		try{
			//解析JSON字符串
			jsonObj_school = JSONObject.fromObject(stringBuffer.toString());
			JSONObject jsonobj = jsonObj_school.getJSONObject("data");
			jsonArr_school = JSONObject.fromObject(jsonobj).getJSONArray("macs");
			String[] arr =  (String[]) JSONArray.toArray(jsonArr_school,String.class);

			List<TPhicommAircatDevice> list = new ArrayList<>();
			List<TPhicommAircatInfo> tPhicommAircatInfoList = new ArrayList<TPhicommAircatInfo>();

			if (arr.length > 0) {
				for (int i = 0; i < arr.length; i++) {
					TPhicommAircatDevice tPhicommAircatDevice =  new TPhicommAircatDevice();
					TPhicommAircatInfo tPhicommAircatInfo = new TPhicommAircatInfo();
					tPhicommAircatDevice.setMac(arr[i]);
					tPhicommAircatInfo.setMac(arr[i]);
					list.add(tPhicommAircatDevice);
					tPhicommAircatInfoList.add(tPhicommAircatInfo);
				}

				boolean b = tPhicommAircatDeviceMapper.batchInsert(list);
				boolean bool = tPhicommAircatInfoMapper.batchInserList(tPhicommAircatInfoList);

				if (true != bool) {
					log.info("TPhicommAircatInfo插入失败，详细信息请查看日志。");
				}
				return b;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	@Override
	public Page<TPhicommAircatDevice> selectAircatDevice(Page<TPhicommAircatDevice> page){
		Map<String, Object> params = page.getCondition();
        EntityWrapper<TPhicommAircatDevice> entityWrapper = new EntityWrapper<TPhicommAircatDevice>();
        if (null != params.get("mac")) {
            entityWrapper.like("mac", params.get("mac").toString());
        }
        if (null != page.getOrderByField()) {
            entityWrapper.orderBy(page.getOrderByField(), page.isAsc());
        } else {
            entityWrapper.orderBy("create_time", false);
        }
        List<TPhicommAircatDevice> tPAircatDevicesList = tPhicommAircatDeviceMapper.listDevice(page,entityWrapper);
        page.setRecords(tPAircatDevicesList);
        return page;
	}
	
}
