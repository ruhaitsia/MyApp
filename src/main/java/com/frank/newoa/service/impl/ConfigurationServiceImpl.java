package com.frank.newoa.service.impl;

import com.frank.newoa.common.OARuntimeException;
import com.frank.newoa.model.Configuration;
import com.frank.newoa.cache.ConfigurationCache;
import com.frank.newoa.model.ConfigurationBO;
import com.frank.newoa.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fzhang090 on 10/11/2016.
 */
@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    @Autowired
    private ConfigurationCache configurationCache;


    @Override
    public List<Configuration> getAllConfigurations(ConfigurationBO configurationBO) throws OARuntimeException {
        return configurationCache.getConfigurationCache(configurationBO);
    }

    @Override
    public void cleanConfigurationCache() {
        configurationCache.cleanCache();
    }


}
