package com.example.configuration;

import com.example.sqlinit.ConnectionMetadata;
import com.example.sqlinit.DatabaseMetadataUtils;
import com.example.sqlinit.SQLExecuteUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 用于第一次启动时，初始化数据库的配置类
 */
@Slf4j
@Configuration
public class DatabaseInitialize {

    /**
     * 读取连接地址
     */
    @Value("${spring.datasource.druid.url}")
    private String url;

    /**
     * 读取用户名
     */
    @Value("${spring.datasource.druid.username}")
    private String username;

    /**
     * 读取密码
     */
    @Value("${spring.datasource.druid.password}")
    private String password;

    /**
     * 该方法用于检测数据库是否需要初始化，如果是则执行SQL脚本进行初始化操作
     */
    @PostConstruct
    private void initDatabase() {
        log.info("开始检查数据库是否需要初始化...");
        // 解析连接地址
        ConnectionMetadata urlMeta = new ConnectionMetadata(url);
        // 建立连接，但是不连接至指定的数据库
        // 主要是检查元数据
        String checkUrl = "jdbc:" + urlMeta.getDatabasePlatform() + "://" + urlMeta.getHostAndPort() + "/";
        try (Connection connection = DriverManager.getConnection(checkUrl, username, password)) {
            // 检测数据库是否存在
            if (!DatabaseMetadataUtils.databaseExists(urlMeta.getDatabaseName(), connection)) {
                log.warn("数据库不存在！准备创建！");
                SQLExecuteUtils.createDatabase(urlMeta.getDatabaseName(), connection);
            } else {
                log.info("数据库存在，不需要创建！");
            }
        } catch (Exception e) {
            log.error("连接至数据库检查元数据时失败！");
            log.error(e.getMessage());
        }
        // 然后再次连接，本次将会连接至指定数据库，执行脚本初始化库中的表格
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // 检测数据库中表的数量，如果为0则执行SQL文件创建表
            if (DatabaseMetadataUtils.getDatabaseTableCount(urlMeta.getDatabaseName(), connection) == 0) {
                log.warn("数据库{}中没有任何表，将进行创建！", urlMeta.getDatabaseName());
                SQLExecuteUtils.executeSQLScript("/create-table.sql", true, connection);
                log.info("初始化表格完成！");
            } else {
                log.info("数据库中已经存在表格，不需要创建！");
            }
        } catch (Exception e) {
            log.error("初始化表格时，连接数据库失败！");
            log.error(e.getMessage());
        }
    }

}
