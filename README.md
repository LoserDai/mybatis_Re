# mybatis_Re
mybatis的逆向工程（用于生成vo、mapper文件）
## 一、Mybatis逆向工程

### 1.1 新建maven工程（最好）

### 1.2 配置好所需的依赖

```xml
<dependencies>
    <!--mysql驱动包-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.29</version>
    </dependency>
    <!--日志文件-->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-log4j12</artifactId>
        <version>2.0.3</version>
    </dependency>
    <!--插件-->
    <dependency>
        <groupId>org.mybatis.generator</groupId>
        <artifactId>mybatis-generator-core</artifactId>
        <version>1.3.2</version>
    </dependency>
</dependencies>
```

### 1.3 配置好`generatorConfig.xml`文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <context id="testTables" targetRuntime="MyBatis3">
        <commentGenerator>
            <!-- 是否去除自动生成的注释 true：是 ： false:否 -->
            <property name="suppressAllComments" value="true" />
        </commentGenerator>
        <!--数据库连接的信息：驱动类、连接地址、用户名、密码 -->
        <!-- <jdbcConnection driverClass="com.mysql.jdbc.Driver"
           connectionURL="jdbc:mysql://localhost:3306/mybatis" userId="root"
           password="123">
        </jdbcConnection> -->
        <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                        connectionURL="jdbc:mysql://127.0.0.1:3306/mall?useSSL=false&amp;useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=Asia/Shanghai&amp;allowPublicKeyRetrieval=true"
                        userId="root"
                        password="root">
        </jdbcConnection>

        <!-- 默认false，把JDBC DECIMAL 和 NUMERIC 类型解析为 Integer，为 true时把JDBC DECIMAL 和
           NUMERIC 类型解析为java.math.BigDecimal -->
        <javaTypeResolver>
            <property name="forceBigDecimals" value="false" />
        </javaTypeResolver>

        <!-- targetProject:生成PO类的位置 -->
        <javaModelGenerator targetPackage="com.df.vo"
                            targetProject="D:\Code\Mybatis_Re\src\main\java">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="false" />
            <!-- 从数据库返回的值被清理前后的空格 -->
            <property name="trimStrings" value="true" />
        </javaModelGenerator>
        <!-- targetProject:mapper映射文件生成的位置 -->
        <sqlMapGenerator targetPackage="com.df.mapper"
                         targetProject="D:\Code\Mybatis_Re\src\main\java">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="false" />
        </sqlMapGenerator>
        <!-- targetPackage：mapper接口生成的位置 -->
        <javaClientGenerator type="XMLMAPPER"
                             targetPackage="com.df.mapper"
                             targetProject="D:\Code\Mybatis_Re\src\main\java">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="false" />
        </javaClientGenerator>

        <!-- 指定数据库表 可以写多个table标签-->
        <table tableName="oms_order" domainObjectName="Order"
               enableCountByExample="false" enableUpdateByExample="false" enableDeleteByExample="false"
               enableSelectByExample="false" selectByExampleQueryId="false" >
            <columnOverride column="id" javaType="Integer" />
        </table>
        <table tableName="oms_order_item" domainObjectName="OrderItem"
               enableCountByExample="false" enableUpdateByExample="false" enableDeleteByExample="false"
               enableSelectByExample="false" selectByExampleQueryId="false" >
            <columnOverride column="id" javaType="Integer" />
        </table>

    </context>
</generatorConfiguration>
```

### 1.4 配置好log4j文件

```properties
log4j.rootLogger=debug,stdout

log4j.appender.stdout=org.apache.log4j.ConsoleAppender

log4j.appender.stdout.Target=System.err

log4j.appender.stdout.layout=org.apache.log4j.SimpleLayout

log4j.appender.logfile=org.apache.log4j.FileAppender

log4j.appender.logfile.File=d:/log.log

log4j.appender.logfile.layout=org.apache.log4j.PatternLayout

log4j.appender.logfile.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %l %F %p %m%n
```

### 1.5 实现方法

```java
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
```

### 1.6 在`generatorConfig.xml`中注意下输出的vo，mapper文件的目录

```xml
<!-- targetProject:生成PO类的位置 -->
<javaModelGenerator targetPackage="com.df.vo"
                    targetProject="D:\Code\Mybatis_Re\src\main\java">
    <!-- enableSubPackages:是否让schema作为包的后缀 -->
    <property name="enableSubPackages" value="false" />
    <!-- 从数据库返回的值被清理前后的空格 -->
    <property name="trimStrings" value="true" />
</javaModelGenerator>
<!-- targetProject:mapper映射文件生成的位置 -->
<sqlMapGenerator targetPackage="com.df.mapper"
                 targetProject="D:\Code\Mybatis_Re\src\main\java">
    <!-- enableSubPackages:是否让schema作为包的后缀 -->
    <property name="enableSubPackages" value="false" />
</sqlMapGenerator>
<!-- targetPackage：mapper接口生成的位置 -->
<javaClientGenerator type="XMLMAPPER"
                     targetPackage="com.df.mapper"
                     targetProject="D:\Code\Mybatis_Re\src\main\java">
    <!-- enableSubPackages:是否让schema作为包的后缀 -->
    <property name="enableSubPackages" value="false" />
</javaClientGenerator>
```

### 1.7 基本可以实现全部的crud的mapper文件
