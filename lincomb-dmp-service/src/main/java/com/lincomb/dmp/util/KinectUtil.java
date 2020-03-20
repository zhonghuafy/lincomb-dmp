package com.lincomb.dmp.util;

/**
 * Created by shiyu.cao on 2018/1/22.
 */
public class KinectUtil {

    /**
     * @creat by shiyu.cao
     * @reamrk  计算体感工具类
     * @ T:温度 RH:湿度
     */
    public static double calcHeat(String T,String RH) {
        double temperature = 0.00;
        double humidity = 0.00;
        double HI = 0.00;
        double ADJUSTMENT = 0.00;

        if (null != T) {
            temperature = Double.parseDouble(T);
        }

        if (null != RH) {
            humidity = Double.parseDouble(RH);
        }

        if (humidity < 1) {
            humidity = humidity * 100;
            temperature = 1.8 * temperature + 32;
            HI = 0.5 * (temperature + 61 + (temperature - 68) * 1.2 + humidity * 0.094);

            if (HI >= 80) {
                HI = -42.379 + 2.04901523 * temperature + 10.14333127 * humidity - 0.22475541 * temperature * humidity
                        -0.00683783 * temperature * temperature - 0.5481717 * humidity * humidity + 0.00122874 * temperature * temperature * humidity
                        +0.00085282 * temperature * humidity * humidity - 0.00000199 * temperature * temperature * humidity * humidity ;

                if (humidity < 13 && (temperature > 80 && temperature < 112)) {
                    ADJUSTMENT = (13 - humidity) / 4 * Math.sqrt((17 - Math.abs(temperature - 95)) / 17);
                    HI -= ADJUSTMENT;
                }else if (humidity > 85 && (temperature > 80 && temperature < 87)) {
                    ADJUSTMENT = (humidity - 85 ) * (87 - temperature) / 50;
                    HI += ADJUSTMENT;
                }
            }
        }
        return Math.round((HI - 32)/ 1.8);
    }


    /**
     * @remark 返回体感
     * 体感分为 九个等级
     * R  < 4               寒冷
     * 4≤R<13 && RH≤35%     干冷
     * 4≤R<13 && 35%<RH≤55% 微寒
     * 4≤R<13 && RH>55%     湿冷
     * 13≤R<20              凉爽
     * 20≤R<28              舒适
     * 28≤R<35              微热
     * R≥35 && RH≤55%       炎热
     * R≥35 && RH>55%       闷热
     *
     * @return
     */
    public  static String kinect(double R , String RH) {
        double  humidity = 0.00;

        if (null != RH) {
            humidity = Double.parseDouble(RH);
        }

        if ( R < 4) {
            return "寒冷";
        }

        if ((R >= 4 && R < 13) && (humidity <= 0.35) ) {
            return  "干冷";
        }

        if ((R >= 4 && R < 13) && (humidity > 0.35 && humidity <= 0.55) ) {
            return  "微寒";
        }

        if ((R >= 4 && R < 13) && (humidity > 0.55) ) {
            return  "湿冷";
        }

        if (R >= 13 && R < 20) {
            return  "凉爽";
        }

        if (R >= 20 && R < 28) {
            return  "舒适";
        }

        if (R >= 28 && R < 35) {
            return  "微热";
        }

        if ((R >= 35) && (humidity <= 0.55) ) {
            return  "炎热";
        }

        if ((R >= 35) && (humidity > 0.55) ) {
            return  "闷热";
        }
        return  "信息出错";
    }


    /**
     * @param T
     * @param RH
     * @return
     */
    public  static String getKinect(String T,String RH){
        double heat = calcHeat(T,RH);
        String kinect = kinect(heat,RH);
        return  kinect;
    }

    public static void main(String[] args){
        String s = getKinect("29","0.129");
        System.out.print(s);
    }
}
