package com.msb.serviceDriverUser.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class MysqlGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/service-driver" +
                        "-user?useUnicode=true&characterEncoding=utf-8&serverTimezone" +
                        "=UTC", "root", "root")
                .globalConfig(builder -> {
                    builder.author("baomidou") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("service-driver-user/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.msb.serviceDriverUser") // 设置父包名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                                    "service-driver-user/src/main/java/com/msb/serviceDriverUser/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("car"); // 设置需要生成的表名
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
