package com.lincomb.dmp.controller.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lincomb.dmp.base.controller.BaseController;
import com.lincomb.dmp.common.annotion.BussinessLog;
import com.lincomb.dmp.common.annotion.Permission;
import com.lincomb.dmp.common.constant.Const;
import com.lincomb.dmp.common.constant.dictmap.DictMap;
import com.lincomb.dmp.common.constant.factory.ConstantFactory;
import com.lincomb.dmp.common.constant.factory.PageFactory;
import com.lincomb.dmp.common.exception.BizExceptionEnum;
import com.lincomb.dmp.common.exception.BussinessException;
import com.lincomb.dmp.core.shiro.ShiroKit;
import com.lincomb.dmp.log.LogObjectHolder;
import com.lincomb.dmp.persistence.mapper.system.DictMapper;
import com.lincomb.dmp.persistence.model.Dict;
import com.lincomb.dmp.service.system.IDictService;
import com.lincomb.dmp.util.ToolUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典控制器
 *
 * @author fengshuonan
 * @Date 2017年4月26日 12:55:31
 */
@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {

    private String PREFIX = "/system/dict/";

    @Resource
    DictMapper dictMapper;

    @Resource
    IDictService dictService;

    /**
     * 跳转到字典管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dict.html";
    }

    /**
     * 跳转到添加字典
     */
    @RequestMapping("/dict_add")
    public String deptAdd() {
        return PREFIX + "dict_add.html";
    }

    /**
     * 跳转到修改字典
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping("/dict_edit/{dictId}")
    public String deptUpdate(@PathVariable Integer dictId, Model model) {
        Dict dict = dictMapper.selectById(dictId);
        model.addAttribute("dict", dict);
        List<Dict> subDicts = dictMapper.selectList(new EntityWrapper<Dict>().eq("pid", dictId));
        model.addAttribute("subDicts", subDicts);
        LogObjectHolder.me().set(dict);
        return PREFIX + "dict_edit.html";
    }

    /**
     * 新增字典
     *
     * @param dictValues 格式例如   "1:启用;2:禁用;3:冻结"
     */
    @BussinessLog(value = "添加字典记录", key = "dictName,dictValues", dict = DictMap.class)
    @RequestMapping(value = "/add")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object add(String dictName, String dictValues) {
        if (ToolUtil.isOneEmpty(dictName, dictValues)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        //获取用户当前登录名
        String account = ShiroKit.getUser().getAccount();

        this.dictService.addDict(dictName, dictValues, account);
        return SUCCESS_TIP;
    }

    /**
     * 获取所有字典列表
     */
    @RequestMapping(value = "/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(@RequestParam(required = false) String condition) {
        Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();

        Map<String, Object> params = new HashMap<>();
        params.put("name", condition);
        page.setCondition(params);

        page = dictService.selectDitsPage(page);
        return super.packForBT(page);
    }

    /**
     * 字典详情
     */
    @RequestMapping(value = "/detail/{dictId}")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object detail(@PathVariable("dictId") Integer dictId) {
        return dictMapper.selectById(dictId);
    }

    /**
     * 修改字典
     */
    @BussinessLog(value = "修改字典", key = "dictName,dictValues", dict = DictMap.class)
    @RequestMapping(value = "/update")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object update(Integer dictId, String dictName, String dictValues) {
        if (ToolUtil.isOneEmpty(dictId, dictName, dictValues)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        //获取用户当前登录名
        String account = ShiroKit.getUser().getAccount();

        dictService.editDict(dictId, dictName, dictValues, account);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除字典记录
     */
    @BussinessLog(value = "删除字典记录", key = "dictId", dict = DictMap.class)
    @RequestMapping(value = "/delete")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object delete(@RequestParam Integer dictId) {

        //缓存被删除的名称
        LogObjectHolder.me().set(ConstantFactory.me().getDictName(dictId));

        this.dictService.delteDict(dictId);
        return SUCCESS_TIP;
    }

}
