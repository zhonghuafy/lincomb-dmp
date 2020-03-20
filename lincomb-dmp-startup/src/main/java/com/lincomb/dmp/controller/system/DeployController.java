package com.lincomb.dmp.controller.system;

import com.baomidou.mybatisplus.plugins.Page;
import com.lincomb.dmp.base.controller.BaseController;
import com.lincomb.dmp.base.tips.Tip;
import com.lincomb.dmp.cache.CacheKit;
import com.lincomb.dmp.common.annotion.BussinessLog;
import com.lincomb.dmp.common.annotion.Permission;
import com.lincomb.dmp.common.constant.Const;
import com.lincomb.dmp.common.constant.cache.Cache;
import com.lincomb.dmp.common.constant.dictmap.RoleDict;
import com.lincomb.dmp.common.constant.factory.ConstantFactory;
import com.lincomb.dmp.common.constant.factory.PageFactory;
import com.lincomb.dmp.common.exception.BizExceptionEnum;
import com.lincomb.dmp.common.exception.BussinessException;
import com.lincomb.dmp.core.shiro.ShiroKit;
import com.lincomb.dmp.log.LogObjectHolder;
import com.lincomb.dmp.persistence.model.TMasterDeployInfo;
import com.lincomb.dmp.service.system.IMasterDeployInfoService;
import com.lincomb.dmp.util.ToolUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

/**
 * 信息部署控制器
 *
 * @author fengshuonan
 * @Date 2017年2月12日21:59:14
 */
@Controller
@RequestMapping("/deploy")
public class DeployController extends BaseController {
    private static String PREFIX = "/system/deploy";

    @Resource
    private IMasterDeployInfoService iMasterDeployInfoService;

    /**
     * 跳转到信息部署列表页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/deploy.html";
    }

    /**
     * 跳转到添加信息页面
     */
    @RequestMapping(value = "/deploy_add")
    public String roleAdd() {
        return PREFIX + "/deploy_add.html";
    }

    /**
     * 跳转到修改信息页面
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/deploy_edit/{id}")
    public String roleEdit(@PathVariable Integer id, Model model) {
        if (ToolUtil.isEmpty(id)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        TMasterDeployInfo tMasterDeployInfo = this.iMasterDeployInfoService.selectByPrimaryKey(id);
        model.addAttribute("tMasterDeployInfo",tMasterDeployInfo);
        LogObjectHolder.me().set(tMasterDeployInfo);
        return PREFIX + "/deploy_edit.html";
    }

    /**
     * 查询信息列表
     */
    @RequestMapping("/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(@RequestParam(required = false) String slaveName, @RequestParam(required = false) Integer slaveStatus, @RequestParam(required = false) String createTim) {
        Page page = new PageFactory<Map<String, Object>>().defaultPage();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("slaveName", slaveName);
        params.put("slaveStatus", slaveStatus);
        params.put("createTim", createTim);
        page.setCondition(params);

        page = iMasterDeployInfoService.selectDeploys(page);
        return super.packForBT(page);
    }

    /**
     * 新增信息
     */
    @RequestMapping(value = "/add")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip add(@Valid TMasterDeployInfo tMasterDeployInfo, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        //orders最大值
        int ordersMax = iMasterDeployInfoService.selectMax();
        //如果值为1则修改
        if (tMasterDeployInfo.getSlaveStatus().equals("1")){
            tMasterDeployInfo.setOrders(ordersMax+1);
        }

        tMasterDeployInfo.setCreateTime(new Date());
        this.iMasterDeployInfoService.insert(tMasterDeployInfo);
        return SUCCESS_TIP;
    }

    /**
     * 修改信息
     */
    @RequestMapping(value = "/edit")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip edit(@Valid TMasterDeployInfo tMasterDeployInfo, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        //不能删除orders为0的信息
        if(iMasterDeployInfoService.selectByPrimaryKey(tMasterDeployInfo.getId()).getOrders() != null && iMasterDeployInfoService.selectByPrimaryKey(tMasterDeployInfo.getId()).getOrders() == 0){
            throw new Exception("该信息不能被修改");
        }

        //orders最大值
        int ordersMax = iMasterDeployInfoService.selectMax();

        if (iMasterDeployInfoService.selectByPrimaryKey(tMasterDeployInfo.getId()).getSlaveStatus().equals("1") == tMasterDeployInfo.getSlaveStatus().equals("1")){
            
        }else{
            //开---关
            if (tMasterDeployInfo.getSlaveStatus().equals("0")){
                TMasterDeployInfo tMasterDeployInfo1 = new TMasterDeployInfo();
                tMasterDeployInfo1.setOrders(tMasterDeployInfo.getOrders());
                iMasterDeployInfoService.updateOrders(tMasterDeployInfo1);
                tMasterDeployInfo.setOrders(null);
            }

            //关---开
            if (tMasterDeployInfo.getSlaveStatus().equals("1")){
                iMasterDeployInfoService.updateOrdersById(ordersMax+1,tMasterDeployInfo.getId());
                tMasterDeployInfo.setOrders(ordersMax+1);
            }
        }

        tMasterDeployInfo.setUpdateTime(new Date());
        this.iMasterDeployInfoService.update(tMasterDeployInfo);

        //删除缓存
        CacheKit.removeAll(Cache.CONSTANT);
        return SUCCESS_TIP;
    }

    /**
     * 删除信息
     */
    @RequestMapping(value = "/remove")
    @BussinessLog(value = "删除角色", key = "roleId", dict = RoleDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip remove(@RequestParam Integer id) throws Exception {
        if (ToolUtil.isEmpty(id)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        //不能删除orders为0的信息
        TMasterDeployInfo tMasterDeployInfo = iMasterDeployInfoService.selectByPrimaryKey(id);
        if(tMasterDeployInfo.getOrders() != null && tMasterDeployInfo.getOrders() == 0){
            throw new Exception("该信息不能被删除");
        }

        this.iMasterDeployInfoService.delete(id);
        return SUCCESS_TIP;
    }
}
