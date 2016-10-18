package com.frank.newoa.cache;

import com.frank.newoa.common.OARuntimeException;
import com.frank.newoa.controller.ConfigurationController;
import com.frank.newoa.dao.ConfigurationDao;
import com.frank.newoa.model.Configuration;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.frank.newoa.model.ConfigurationBO;
import com.frank.newoa.util.ConfigUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by fzhang090 on 10/11/2016.
 * 用于管理configuration的本地缓存
 * 使用google的guava framework
 * 缓存策略是先看cache中是否有数据，没有就从数据查询数据并放到缓存中
 */
@Component
public class ConfigurationCache {

    private static final Logger logger = Logger.getLogger(ConfigurationController.class);


    @Autowired
    private ConfigurationDao configurationDaoImpl;

    //load properties
    static {
        ConfigUtil.loadPropertiesFile("configuration.properties");
    }

    //获取配置文件内容，如果没有给一个默认值
    private static long autoCacheSize = ConfigUtil.getLong("autoCacheSize", 100);
    private static long expireAfterAccessTime = ConfigUtil.getLong("expireAfterAccessTime", 60);
    private static long expireAfterWriteTime = ConfigUtil.getLong("expireAfterWriteTime", 60);

    /*
    *
    * */
    private LoadingCache<ConfigurationBO, List<Configuration>> configurationCache = CacheBuilder.newBuilder().concurrencyLevel(8).maximumSize(autoCacheSize).expireAfterAccess(expireAfterAccessTime, TimeUnit.MINUTES)
            .expireAfterWrite(expireAfterWriteTime, TimeUnit.MINUTES).recordStats()
            .build(new CacheLoader<ConfigurationBO, List<Configuration>>() {
                @Override
                public List<Configuration> load(ConfigurationBO configurationBO) throws Exception {
                    List<Configuration> result = getConfigurationFormDB(configurationBO);
                    logger.info("Load configuration data from db to cache" + result.toString());
                    return result;
                }
            });

    private List<Configuration> getConfigurationFormDB(ConfigurationBO configurationBO) {
        return configurationDaoImpl.getAllConfigurations(configurationBO);
    }

    public List<Configuration> getConfigurationCache(ConfigurationBO configurationBO) throws OARuntimeException {
        List<Configuration> response;

        try {
            response = configurationCache.get(configurationBO);
        } catch (ExecutionException e) {
            throw new OARuntimeException();
        }
        return response;
    }

    public void cleanCache(){
        configurationCache.cleanUp();
        configurationCache.invalidateAll();
    }

}
