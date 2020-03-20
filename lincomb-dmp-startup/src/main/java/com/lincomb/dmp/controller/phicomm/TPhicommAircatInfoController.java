package com.lincomb.dmp.controller.phicomm;

import com.baomidou.mybatisplus.plugins.Page;
import com.lincomb.dmp.base.controller.BaseController;
import com.lincomb.dmp.common.annotion.Permission;
import com.lincomb.dmp.common.constant.Const;
import com.lincomb.dmp.common.constant.factory.PageFactory;
import com.lincomb.dmp.persistence.model.aircat.TPhicommAircatInfo;
import com.lincomb.dmp.persistence.model.aircat.TPhicommAircatInfoLog;
import com.lincomb.dmp.service.aircat.IPhicommAircatInfoLogService;
import com.lincomb.dmp.service.aircat.IPhicommAircatInfoService;
import com.lincomb.dmp.service.aircat.ISchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 空气猫控制器
 * Created by shiyu.cao on 2017/12/28.
 */

@Controller
@RequestMapping("/aircat")
public class TPhicommAircatInfoController extends BaseController{

    private static Logger log = LoggerFactory.getLogger(TPhicommAircatInfoController.class);

    private String PREFIX = "/aircat/";

    @Resource
    private ISchedulerService iSchedulerService;

    @Resource
    IPhicommAircatInfoService iPhicommAircatInfoService;

    @Resource
    IPhicommAircatInfoLogService iPhicommAircatInfoLogService;

    /**
     * 跳转到字典管理首页
     */
    @RequestMapping("")
    public String index() {
        log.info("CONTROLLER LOG INFO(shiyu.cao)>>className:" + this.getClass().getName()+">>MethodName: index()");
        return PREFIX + "aircat.html";
    }


    /**
     * 获取所有字典列表
     */
    @RequestMapping(value = "/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(HttpServletRequest request) {
        log.info("CONTROLLER LOG INFO(shiyu.cao)>>className:" + this.getClass().getName()+">>MethodName: list()");

        Page<TPhicommAircatInfo> page = new PageFactory<TPhicommAircatInfo>().defaultPage();

        Map<String, Object> params = new HashMap<>();

        String condition = request.getParameter("condition");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");

        if (null != condition) {
            params.put("mac", condition);
        }

        if (null != startTime) {
            params.put("startTime", startTime);
        }

        if (null != endTime) {
            params.put("endTime", endTime);
        }

        page.setCondition(params);
        page = iPhicommAircatInfoService.selectAircatListPage(page);
        return super.packForBT(page);
    }


    /**
     * 跳转空气猫详情页面
     */
    @RequestMapping(value = "/detail/{mac}")
    @Permission(Const.ADMIN_NAME)
    public Object detail(@PathVariable("mac") String mac,Model model) {
        log.info("CONTROLLER LOG INFO(shiyu.cao)>>className:" + this.getClass().getName()+">>MethodName: detail()");
        model.addAttribute("mac",mac);
        return PREFIX + "aircat_log.html";
    }


    /**
     * 获取空气猫数据历史
     */
    @RequestMapping(value = "/listLog/{mac}")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object listLog(@PathVariable("mac") String mac) {
        log.info("CONTROLLER LOG INFO(shiyu.cao)>>className:" + this.getClass().getName()+">>MethodName: listLog()");
        Page<TPhicommAircatInfoLog> page = new PageFactory<TPhicommAircatInfoLog>().defaultPage();
        Map<String, Object> params = new HashMap<>();

        if (null != mac) {
            params.put("mac", mac);
        }

        page.setCondition(params);
        page = iPhicommAircatInfoService.selectAircatLogListPage(page);
        return super.packForBT(page);
    }
}
