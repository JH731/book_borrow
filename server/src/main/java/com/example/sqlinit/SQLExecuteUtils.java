package com.example.sqlinit;

import cn.hutool.core.io.resource.ClassPathResource;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;

/**
 * 常用的SQL执行操作的实用类
 */
@Slf4j
public class SQLExecuteUtils {

    /**
     * 创建一个数据库
     *
     * @param databaseName 要创建的数据库名
     * @param connection   JDBC连接
     */
    public static void createDatabase(String databaseName, Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("create database if not exists `" + databaseName + "`");
            log.info("已创建数据库{}！", databaseName);
        } catch (Exception e) {
            log.error("创建数据库失败！");
            log.error(e.getMessage());
        }
    }

    /**
     * 读取并执行一个SQL脚本文件
     *
     * @param filepath    SQL脚本文件路径
     * @param isClasspath SQL脚本路径是否是classpath路径
     * @param connection  执行脚本文件的JDBC连接
     */
    public static void executeSQLScript(String filepath, boolean isClasspath, Connection connection) {
        // 根据传入参数判断是从classpath读取还是本地文件系统读取
        try (InputStream sqlFileStream = isClasspath ? new ClassPathResource(filepath).getStream() : new FileInputStream(filepath); Statement statement = connection.createStatement()) {
            // 以UTF-8编码读取文件流
            BufferedReader sqlFileStreamReader = new BufferedReader(new InputStreamReader(sqlFileStream, StandardCharsets.UTF_8));
            // 读取到的行
            String line;
            // 每次执行的语句
            StringBuilder sqlExecute = new StringBuilder();
            // 开始读取
            while ((line = sqlFileStreamReader.readLine()) != null) {
                // 去除两端不可见字符
                line = line.trim();
                // 跳过注释或者空字符行
                if (line.isEmpty() || line.startsWith("--") || line.startsWith("#")) {
                    continue;
                }
                // 将语句追加至每次执行的语句
                sqlExecute.append(line).append(System.lineSeparator());
                // 如果当前语句以分号结尾，说明已经得到了一个完整的SQL语句，执行
                if (line.endsWith(";")) {
                    statement.execute(sqlExecute.toString());
                    System.out.println(sqlExecute);
                    // 清空语句以便下一条语句的追加
                    sqlExecute.setLength(0);
                }
            }
            // 若读取完成后，每次执行的语句中还有语句，则继续执行
            if (sqlExecute.length() != 0) {
                statement.execute(sqlExecute.toString());
                System.out.println(sqlExecute);
            }
        } catch (Exception e) {
            log.error("执行SQL文件失败！");
            log.error(e.getMessage());
        }
    }

}
