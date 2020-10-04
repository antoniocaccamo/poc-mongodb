package me.antoniocaccamo.poc.mongodb;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.poc.mongodb.SubscriberHelpers.TourDocumentSubscriber;
import me.antoniocaccamo.poc.mongodb.collection.TourCollection;
import me.antoniocaccamo.poc.mongodb.model.Tour;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.inject.Inject;

import java.util.function.Consumer;

@Command(name = "poc-mongodb", description = "...", mixinStandardHelpOptions = true)
@Slf4j
public class PocMongodbCommand implements Runnable {

    @Inject
    private TourCollection tourCollection;

    @Option(names = { "-v", "--verbose" }, description = "...")
    boolean verbose;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(PocMongodbCommand.class, args);
    }

    public void run() {
        // business logic here
        if (verbose) {
            System.out.println("Hi!");
        }

        

        Flowable.fromPublisher( tourCollection.getCollection().find() )
            .observeOn(Schedulers.single())
            .subscribe(t -> log.info("\t : {}", t));
        
            TourDocumentSubscriber tourDocumentSubscriber = new TourDocumentSubscriber();
        tourCollection.getCollection().find() .subscribe(tourDocumentSubscriber);
        try {
            tourDocumentSubscriber.await();
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
