package me.antoniocaccamo.poc.mongodb.collection;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.micronaut.context.annotation.Value;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.poc.mongodb.model.Tour;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author ConsCaccamoAntonio  on 02/10/2020
 */

@Singleton
@Slf4j
public class TourCollection implements ICollection<Tour>{

    @Value("${micronaut.params.mongodb.dbname}")
    private String mongodbname;

    @Value("${micronaut.params.mongodb.collection}")
    private String mongodbCollection;

    @Inject
    private MongoClient mongoClient;

    @Getter
    private MongoCollection<Tour> collection;

    @PostConstruct
    public void postContruct() {
        log.info("mongodbname       : {}", mongodbname);
        log.info("mongodbCollection : {}", mongodbCollection);
        collection = mongoClient.getDatabase(mongodbname).getCollection(mongodbCollection, Tour.class);
    }


}
