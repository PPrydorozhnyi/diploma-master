package com.diploma.master.configs;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.lang.NonNull;

@Configuration
@EnableMongoRepositories(basePackages = "com.diploma.master.repositories")
@EnableMongoAuditing
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.host}")
    private String host;
    @Value("${spring.data.mongodb.port}")
    private String port;
    @Value("${spring.data.mongodb.database}")
    private String database;
    @Value("${spring.data.mongodb.username}")
    private String user;
    @Value("${spring.data.mongodb.password}")
    private String password;


    @NonNull
    @Override
    protected String getDatabaseName() {
        return database;
    }

    @NonNull
    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString =
            new ConnectionString("mongodb://" + user + ":" + password
                + "@" + host + ":" + Integer.parseInt(port) + "/?authSource"
                + "=" + database);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build();

        return MongoClients.create(mongoClientSettings);
    }

    @NonNull
    @Override
    public Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.diploma.master");
    }

}
