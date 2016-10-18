package com.frank.newoa.service;

import com.frank.newoa.common.OARuntimeException;
import com.frank.newoa.model.Configuration;
import com.frank.newoa.model.ConfigurationBO;

import java.util.List;

/**
 * Created by fzhang090 on 10/11/2016.
 */
public interface ConfigurationService {

    List<Configuration> getAllConfigurations(ConfigurationBO configurationBO) throws OARuntimeException;

    void cleanConfigurationCache();
}
