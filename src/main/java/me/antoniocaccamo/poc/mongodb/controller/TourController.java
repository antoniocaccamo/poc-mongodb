package me.antoniocaccamo.poc.mongodb.controller;

import io.micronaut.http.annotation.Controller;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import me.antoniocaccamo.poc.mongodb.collection.TourCollection;
import me.antoniocaccamo.poc.mongodb.model.Tour;
import io.micronaut.http.annotation.Get;

@Controller("/tours")
public class TourController {

    @javax.inject.Inject
    private TourCollection tourCollection;
    
    @Get("/")
    public  Maybe<Tour> getall() {

        return Single.fromPublisher(tourCollection.getCollection().find().first())
            .toMaybe();
    }



}
