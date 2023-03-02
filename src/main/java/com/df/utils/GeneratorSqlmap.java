package com.df.utils;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author feng.dai
 * @Date 2022/10/20 10:20
 * @Project_Name Mybatis_Re
 * @Package_Name com.df.utils
 */
public class GeneratorSqlmap {
    public void generator() throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;

        //根据项目文件的路径更改
        File configFile = new File("D:\\Code\\Mybatis_Re\\src\\main\\resources\\generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);

    }

    public static void main(String[] args) {
        try {
            GeneratorSqlmap sqlmap = new GeneratorSqlmap();
            sqlmap.generator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
