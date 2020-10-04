package me.antoniocaccamo.poc.mongodb.collection;

import com.mongodb.reactivestreams.client.MongoCollection;

/**
 * @author ConsCaccamoAntonio  on 02/10/2020
 */
public interface ICollection<E> {

    MongoCollection<E> getCollection();
}
