package com.frank.newoa.dao;

import java.util.List;

import com.frank.newoa.model.Configuration;
import com.mongodb.WriteResult;
import com.frank.newoa.model.ConfigurationBO;

/**
 * Created by fzhang090 on 9/23/2016.
 */
public interface ConfigurationDao {
    Long countConfiguration(ConfigurationBO configurationBo);

    List<Configuration> getAllConfigurations(ConfigurationBO configurationBO);

    Configuration getConfigurationBy(String module, String table);

    Configuration getConfigurationByLabel(String lable);

    void saveConfiguration(Configuration configuration);

    Configuration getConfiguration(String id);

    WriteResult updateConfiguration(Configuration configuration);

    void deleteConfiguration(String id);
}
