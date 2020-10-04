package me.antoniocaccamo.poc.mongodb.factory;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;


@Factory 
@Slf4j
public class MongoClientFactory {
    

    @Value("${mongodb.uri}")
    private String mongodbURI;

    @Bean
    MongoClient mongoClient(){

        log.info("mongodbURI : {} ", mongodbURI) ;

        

        return MongoClients.create(
            MongoClientSettings.builder()
                .codecRegistry(codecRegistry())
                .applyConnectionString( new ConnectionString( mongodbURI))
                .build()
        );
    }

    @Bean
    CodecRegistry codecRegistry() {

        CodecRegistry pojoCodecRegistry = 
            CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
            );

        return pojoCodecRegistry;

    }
}
