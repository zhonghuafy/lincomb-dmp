/**
 * Created by shiyu.cao on 2017/12/27.
 */

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * 代码生成
 *
 * @author ShenHuaJie
 */
public class Generator {
    /**
     * 测试 run 执行
     * <p>
     * 配置方法查看
     * </p>
     */
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("D:\\mybatis\\generate\\");
        gc.setFileOverride(true);
        // 不需要ActiveRecord特性的请改为false
        gc.setActiveRecord(false);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(false);
        gc.setAuthor("wang");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(GlobalConfig gc,String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return (DbColumnType) super.processTypeConvert(gc,fieldType);
            }
        });
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("ggs2");
//        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/xkcrawler?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false");
//        dsc.setUrl("jdbc:mysql://103.10.0.117:3306/xkmagiccrawler?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false");
        dsc.setUrl("jdbc:mysql://localhost:3306/user_portrait?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表
        /*strategy.setInclude(new String[]{"bank_manager","business_management","diagnosis_management","diagnostic_match",
                "diagnostic_summary_management","drug_management","duty_manager",
                "duty_service_type_match","explanation_management","medical_info_management",
                "outpatient_special_disease","service_catagory_manager","service_medical_match",
                "social_insurance_directory","social_insurance_outpatient_special_disease","socialinsurance_management"});
        */
//        strategy.setInclude(new String[]{"Crawl_Task","Crawl_Task_Auth","Crawl_Task_Config","Crawl_Task_Pattern",
//                "Crawled_Data","Crawled_Page_Data","Crawled_Page_Outgoing","Task_History",
// "Crawl_Task_Config_Cookie","Crawled_Data_Header","Crawled_Page_File","Flow_Crawled_Data","Flow_Crawled_Data_Header",
// "Flow_Crawled_Page_Data","Flow_Crawled_Page_File","Flow_Crawled_Page_Outgoing"});
//        strategy.setInclude(new String[]{"crawl_adapterbus_druglistpage"});
        strategy.setInclude(new String[]{"request_label","order_sign"});
        // 排除生成的表
        // strategy.setExclude(new String[]{"test"});
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("org.fe.pack.basic.persistence");
        pc.setEntity("model");
        pc.setMapper("mapper");
        pc.setXml("mapper");
        mpg.setPackageInfo(pc);

        // 执行生成
        mpg.execute();
    }
}

