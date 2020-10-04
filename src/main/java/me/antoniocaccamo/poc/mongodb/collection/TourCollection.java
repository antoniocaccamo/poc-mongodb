package me.antoniocaccamo.poc.mongodb.collection;

import io.micronaut.context.annotation.Value;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.poc.mongodb.model.Tour;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.codecs.configuration.CodecRegistry;

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

    @Inject
    private CodecRegistry codecRegistry;

    @Getter
    private MongoCollection<Tour> collection;

    @PostConstruct
    public void postContruct() {
        log.info("mongodbname       : {}", mongodbname);
        log.info("mongodbCollection : {}", mongodbCollection);
        collection = mongoClient.getDatabase(mongodbname)
                .withCodecRegistry(codecRegistry)
                .getCollection(mongodbCollection, Tour.class)
        ;

        
    }


}
