package com.lincomb.dmp.service.system.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lincomb.dmp.common.exception.BizExceptionEnum;
import com.lincomb.dmp.common.exception.BussinessException;
import com.lincomb.dmp.persistence.mapper.system.DictMapper;
import com.lincomb.dmp.persistence.model.Dict;
import com.lincomb.dmp.service.system.IDictService;
import com.lincomb.dmp.warpper.DictWarpper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.lincomb.dmp.common.constant.factory.MutiStrFactory.*;


@Service
@Transactional
public class DictServiceImpl implements IDictService {

    @Resource
    DictMapper dictMapper;

    @Override
    public void addDict(String dictName, String dictValues, String account) {
        //判断有没有该字典
        List<Dict> dicts = dictMapper.selectList(new EntityWrapper<Dict>().eq("name", dictName).and().eq("pid", 0));
        if(dicts != null && dicts.size() > 0){
            throw new BussinessException(BizExceptionEnum.DICT_EXISTED);
        }

        //解析dictValues
        List<Map<String, String>> items = parseKeyValue(dictValues);

        //添加字典
        Dict dict = new Dict();
        dict.setName(dictName);
        dict.setNum(0);
        dict.setPid(0);
        this.dictMapper.insert(dict);

        //添加字典条目
        for (Map<String, String> item : items) {
            String num = item.get(MUTI_STR_KEY);
            String name = item.get(MUTI_STR_VALUE);
            Dict itemDict = new Dict();
            itemDict.setPid(dict.getId());
            itemDict.setName(name);
            itemDict.setCreateTime(new Date());
            itemDict.setUpdateTime(new Date());
            itemDict.setCreater(account);
            itemDict.setUpdater(account);
            try {
                itemDict.setNum(Integer.valueOf(num));
            }catch (NumberFormatException e){
                throw new BussinessException(BizExceptionEnum.DICT_MUST_BE_NUMBER);
            }
            this.dictMapper.insert(itemDict);
        }
    }

    @Override
    public void editDict(Integer dictId, String dictName, String dicts, String account) {
        //删除之前的字典
        this.delteDict(dictId);

        //重新添加新的字典
        this.addDict(dictName,dicts,account);
    }

    @Override
    public void delteDict(Integer dictId) {
        //删除这个字典的子词典
        Wrapper<Dict> dictEntityWrapper = new EntityWrapper<>();
        dictEntityWrapper = dictEntityWrapper.eq("pid", dictId);
        dictMapper.delete(dictEntityWrapper);

        //删除这个词典
        dictMapper.deleteById(dictId);
    }

    @Override
    public Page<Map<String, Object>> selectDitsPage(Page<Map<String, Object>> page) {
        EntityWrapper<Map<String, Object>> entityWrapper = new EntityWrapper<>();
        Map<String, Object> params = page.getCondition();
        if (null != params.get("name")) {
            entityWrapper.like("name", params.get("name").toString());
        }
        if (null != page.getOrderByField()) {
            entityWrapper.orderBy(page.getOrderByField(), page.isAsc());
        } else {
            entityWrapper.orderBy("id", false);
        }

        List<Map<String, Object>> dicts = dictMapper.selectDictsPage(page, entityWrapper);
        page.setRecords((List<Map<String,Object>>) new DictWarpper(dicts).warp());
        return page;
    }
}
