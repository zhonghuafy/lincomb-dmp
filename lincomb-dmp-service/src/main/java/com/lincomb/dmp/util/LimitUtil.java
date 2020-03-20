package com.lincomb.dmp.util;

import com.lincomb.dmp.dto.LimitDto;

/**
 * Created by shiyu.cao on 2018/1/26.
 */
public class LimitUtil {

    public static LimitDto getLimit (LimitDto limitDto) {

        //当从服务器为0台时
        if (limitDto.getSlaveNum() == 0 ) {
            limitDto.setLimitStart(0);
            limitDto.setLimitSize(limitDto.getMacSize());
            return limitDto;
        }

        //当从服务器大于0台时
        if (limitDto.getSlaveNum() > 0) {
            //当进入主服务器时
            if (0 == limitDto.getSlaveOrder()) {
                limitDto.setLimitStart(0);
                //limitDto.getSlaveNum()+1  因为SlaveNum为从服务器数量  所有总数要加上 master主机台数1
                //limitDto.getMacSize() / (limitDto.getSlaveNum()+1) 为平均每台服务器跑批数量
                limitDto.setLimitSize(limitDto.getMacSize() / (limitDto.getSlaveNum()+1));
                return limitDto;
            }
            for (int i = 1 ; i <= limitDto.getSlaveNum(); i++) {
                if (limitDto.getSlaveOrder() == limitDto.getSlaveNum()) {
                    limitDto.setLimitStart((limitDto.getMacSize() / (limitDto.getSlaveNum() + 1)) * limitDto.getSlaveOrder());
                    limitDto.setLimitSize(limitDto.getMacSize() - (limitDto.getMacSize() / (limitDto.getSlaveNum() + 1)) * limitDto.getSlaveOrder());
                    return  limitDto;
                }
                if (limitDto.getSlaveOrder() == i) {
                    limitDto.setLimitStart((limitDto.getMacSize() / (limitDto.getSlaveNum() + 1)) * i);
                    limitDto.setLimitSize((limitDto.getMacSize() / (limitDto.getSlaveNum() + 1)));
                    return  limitDto;
                }
            }
        }
        return limitDto;
    }


    public  static void  main(String[] args){
        LimitDto limitDto = new LimitDto();
        limitDto.setMacSize(10);
        limitDto.setSlaveNum(0);
        limitDto.setSlaveOrder(0);

        limitDto = getLimit(limitDto);
        System.out.print(limitDto.getLimitStart()+"/"+limitDto.getLimitSize());
    }
}
