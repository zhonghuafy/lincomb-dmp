package com.lincomb.dmp.controller.phicomm;

import com.baomidou.mybatisplus.plugins.Page;
import com.lincomb.dmp.base.controller.BaseController;
import com.lincomb.dmp.common.annotion.Permission;
import com.lincomb.dmp.common.constant.Const;
import com.lincomb.dmp.common.constant.factory.PageFactory;
import com.lincomb.dmp.persistence.model.aircat.TPhicommAircatDevice;
import com.lincomb.dmp.service.aircat.IPhicommAircatDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 空气猫设备控制器
 * Created by kaiyi.chen on 2018/1/26.
 */

@Controller
@RequestMapping("/aircatDevice")
public class TPhicommAircatDeviceController extends BaseController{

    private static Logger log = LoggerFactory.getLogger(TPhicommAircatDeviceController.class);

    private String PREFIX = "/aircat/";

    @Resource
    IPhicommAircatDeviceService iPhicommAircatDeviceService;

    /**
     * 跳转到字典管理首页
     */
    @RequestMapping("")
    public String index() {
        log.info("CONTROLLER LOG INFO(kaiyi.chen)>>className:" + this.getClass().getName()+">>MethodName: index()");
        return PREFIX + "aircatDevice.html";
    }


    /**
     * 获取所有字典列表
     */
    @RequestMapping(value = "/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(HttpServletRequest request) {
        log.info("CONTROLLER LOG INFO(kaiyi.chen)>>className:" + this.getClass().getName()+">>MethodName: list()");

        Page<TPhicommAircatDevice> page = new PageFactory<TPhicommAircatDevice>().defaultPage();

        Map<String, Object> params = new HashMap<>();

        String condition = request.getParameter("condition");

        if (null != condition) {
            params.put("mac", condition);
        }

        page.setCondition(params);
        page = iPhicommAircatDeviceService.selectAircatDevice(page);
        return super.packForBT(page);
    }

}
