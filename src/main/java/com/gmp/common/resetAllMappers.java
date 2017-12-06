package com.gmp.common;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class resetAllMappers {

    private SqlSessionFactory sqlSessionFactory;

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    private String mapperUrl = "classpath:mappings/**/*.xml";//映射文件地址
    private Resource[] mapperLocations;//存放所有映射资源
    Configuration configuration;

    Logger logger = LoggerFactory.getLogger(resetAllMappers.class);

    /**
     * 扫描配置文件
     */
    public void ScanMappers() {
        try {
            this.mapperLocations = new PathMatchingResourcePatternResolver().getResources(mapperUrl);
            logger.info("获取mapper映射文件路径成功！");
        } catch (IOException e) {
            logger.info("获取mapper映射文件失败，请检查路径是否正确");
            e.printStackTrace();
        }
    }

    /**
     * 清空mybatis几个必要清除的缓存
     */
    public void ClearCache() {
        try {
            this.removeConfig(sqlSessionFactory.getConfiguration());
            logger.info("清除Configuration缓存成功！");
        } catch (Exception e) {
            logger.info("清除Configuration缓存失败！");
            e.printStackTrace();
        }
    }
    /**      * 清空Configuration中几个重要的缓存      * @param configuration      * @throws Exception      */
    private void removeConfig(Configuration configuration) throws Exception {
        Class<?> classConfig = configuration.getClass();
        clearMap(classConfig, configuration, "mappedStatements");
        clearMap(classConfig, configuration, "caches");
        clearMap(classConfig, configuration, "resultMaps");
        clearMap(classConfig, configuration, "parameterMaps");
        clearMap(classConfig, configuration, "keyGenerators");
        clearMap(classConfig, configuration, "sqlFragments");
        clearSet(classConfig, configuration, "loadedResources");

    }
    @SuppressWarnings("rawtypes")
    private void clearMap(Class<?> classConfig, Configuration configuration, String fieldName) throws Exception {
        Field field = classConfig.getDeclaredField(fieldName);
        field.setAccessible(true);
        Map mapConfig = (Map) field.get(configuration);
        mapConfig.clear();
    }

    @SuppressWarnings("rawtypes")
    private void clearSet(Class<?> classConfig, Configuration configuration, String fieldName) throws Exception {
        Field field = classConfig.getDeclaredField(fieldName);
        field.setAccessible(true);
        Set setConfig = (Set) field.get(configuration);
        setConfig.clear();
    }

    /**
     * 重新加载
     */
    public List<String> reLoadMappers() {
        List<String> list = new ArrayList<String>();
        for (Resource configLocation : mapperLocations) {
            try {
                XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configLocation.getInputStream(),
                        sqlSessionFactory.getConfiguration(), configLocation.toString(), sqlSessionFactory.getConfiguration().getSqlFragments());
                xmlMapperBuilder.parse();
                 logger.info("mapper文件[" + configLocation.getFilename() + "]加载成功");
                 list.add("mapper文件[" + configLocation.getFilename() + "]加载成功");
            } catch (IOException e) {
                list.add("mapper文件[" + configLocation.getFilename() + "]不存在或内容格式不对");
                logger.info("mapper文件[" + configLocation.getFilename() + "]不存在或内容格式不对");
                continue;
            }
        }
        return list;
    }
}
