package com.lincomb.dmp.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * ClassName: PropertiesUtil 
 * Function: 读取资源文件[config.properties]
 * Reason:
 * date: 2016年5月17日 上午9:52:59 
 *
 * @author bo.shen
 * @version 
 * @since JDK 1.7
 */
public class PropertiesUtil {
    static Logger log = Logger.getLogger(PropertiesUtil.class);

    public static Map<String, String> listMap;

    static {
        if (listMap == null) {
            listMap = new HashMap<String, String>();
            Properties props = new Properties();
            File f = new File(System.getProperty("global.config.path")+File.separator+"config.properties");

            try {
                props.load(new FileInputStream(f));
                Set<Map.Entry<Object, Object>> entrySet = props.entrySet();
                for (Map.Entry<Object, Object> entry : entrySet) {
                    if (!entry.getKey().toString().startsWith("#")) {
                        listMap.put(((String) entry.getKey()).trim(),
                            ((String) entry.getValue()).trim());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static String getValue(String key) {
        if (null == listMap) {
            return "";
        }
        return listMap.get(key);
    }

}
