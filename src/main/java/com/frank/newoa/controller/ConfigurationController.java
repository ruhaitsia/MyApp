package com.frank.newoa.controller;

import com.frank.newoa.common.OARuntimeException;
import com.frank.newoa.dao.ConfigurationDao;
import com.frank.newoa.model.Configuration;
import com.frank.newoa.service.ConfigurationService;
import com.frank.newoa.model.ConfigurationBO;
import com.frank.newoa.util.BaseAction;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/configuration")


public class ConfigurationController extends BaseAction {

    @Autowired
    private ConfigurationDao configurationDaoImpl;

    @Autowired
    private ConfigurationService configurationServiceImpl;


    private static final Logger logger = Logger.getLogger(ConfigurationController.class);

    @RequestMapping(value = "/addConfiguration", consumes = APPLICATION_JSON, method = RequestMethod.POST)
    public
    @ResponseBody
    Object addConfiguration(@RequestBody Configuration configuration) {
        configurationDaoImpl.saveConfiguration(configuration);
        configurationServiceImpl.cleanConfigurationCache();
        return successReturnObject("Add Successfully");
    }

    @RequestMapping(value = "/findConfiguration")
    public
    @ResponseBody
    Object findConfiguration(@RequestBody ConfigurationBO configurationBO) {
        List configuration = null;
        Long totalCount = configurationDaoImpl.countConfiguration(configurationBO);
        try {
            configuration = configurationServiceImpl.getAllConfigurations(configurationBO);
        } catch (OARuntimeException e) {
            logger.error("get configuration data from cache error", e);
        }
        return successReturnObject(configuration, totalCount);
    }

    @RequestMapping(value = "/deleteConfiguration", consumes = APPLICATION_JSON, method = RequestMethod.POST)
    public
    @ResponseBody
    Object deleteConfiguration(@RequestBody String id) {
        configurationDaoImpl.deleteConfiguration(id);
        configurationServiceImpl.cleanConfigurationCache();
        return successReturnObject("Delete Successfully");
    }

    @RequestMapping(value = "/updateConfiguration", consumes = APPLICATION_JSON, method = RequestMethod.POST)
    public
    @ResponseBody
    Object updateConfiguration(@RequestBody Configuration configuration) {
        configurationDaoImpl.updateConfiguration(configuration);
        configurationServiceImpl.cleanConfigurationCache();
        return successReturnObject("Update Successfully");
    }

    @RequestMapping(value = "/getConfiguration", consumes = APPLICATION_JSON, method = RequestMethod.POST)
    public
    @ResponseBody
    List<Configuration> getConfiguration() {
        ConfigurationBO configurationBO = new ConfigurationBO();
        return configurationServiceImpl.getAllConfigurations(configurationBO);

    }

    @RequestMapping(value = "/getConfigurationByLabel", consumes = APPLICATION_JSON, method = RequestMethod.POST)
    public
    @ResponseBody
    Configuration getConfigurationByType(@RequestBody String label) {
        logger.info("The type is:" + label);
        return configurationDaoImpl.getConfigurationByLabel(label);

    }

    @RequestMapping(value = "/cleanCache", method = RequestMethod.GET)
    public
    @ResponseBody
    String cleanCache() {
        configurationServiceImpl.cleanConfigurationCache();
        return "clean cache successfully!";

    }
}


