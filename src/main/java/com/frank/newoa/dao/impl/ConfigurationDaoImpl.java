package com.frank.newoa.dao.impl;

/**
 * Created by fzhang090 on 9/23/2016.
 */

import java.util.List;

import com.frank.newoa.dao.ConfigurationDao;
import com.frank.newoa.model.Configuration;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.frank.newoa.model.ConfigurationBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;
import org.springframework.stereotype.Repository;

@Repository("configurationDaoImpl")

public class ConfigurationDaoImpl implements ConfigurationDao {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public Long countConfiguration(ConfigurationBO configurationBO) {
        if (null != configurationBO.getModuleName() && !"".equals(configurationBO.getModuleName())) {
            return mongoTemplate.count(new Query(Criteria.where("module.name")
                    .is(configurationBO.getModuleName())), Configuration.class);
        }
        return mongoTemplate.count(new Query(), Configuration.class);
    }

    /**
     * Get all trees.
     */
    @Override
    public List<Configuration> getAllConfigurations(ConfigurationBO configurationBO) {
        Query query = new Query();
        if (null != configurationBO.getModuleName() && !"".equals(configurationBO.getModuleName())) {
            query.addCriteria(Criteria.where("module.name").is(configurationBO.getModuleName()));
        }
        query.limit(configurationBO.getItemPerPage());
        query.skip(configurationBO.getCurrentPage());

        return mongoTemplate.find(query, Configuration.class);

    }

    @Override
    public Configuration getConfigurationBy(String module, String table) {
        return mongoTemplate.find(new Query(Criteria.where("module.name").is(module).and("table.name").is(table)),
                Configuration.class)
                .stream().
                        findFirst().orElse(null);
    }

    /**
     * Get Configuration by table's lable.
     */
    public Configuration getConfigurationByLabel(String data) {
        return mongoTemplate.findOne(new Query(Criteria.where("table.label").is(data)), Configuration.class);
    }

    /**
     * Saves a {@link  Configuration}.
     */
    @Override
    public void saveConfiguration(Configuration configuration) {

        mongoTemplate.insert(configuration);
    }

    /**
     * Gets a {@link  Configuration} for a particular id.
     */
    @Override
    public Configuration getConfiguration(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)),
                Configuration.class);
    }

    /**
     * Updates a {@link  Configuration} name for a particular id.
     */
    @Override
    public WriteResult updateConfiguration(Configuration configuration) {
        DBObject dbDoc = new BasicDBObject();
        mongoTemplate.getConverter().write(configuration, dbDoc); //it is the one spring use for convertions.
        Update update = Update.fromDBObject(dbDoc);
        return mongoTemplate.upsert(new Query(Criteria.where("id").is(configuration.getId())), update, Configuration.class);
    }

    /**
     * Delete a {@link  Configuration} for a particular id.
     */
    @Override
    public void deleteConfiguration(String id) {
        mongoTemplate
                .remove(new Query(Criteria.where("id").is(id)), Configuration.class);
    }

}