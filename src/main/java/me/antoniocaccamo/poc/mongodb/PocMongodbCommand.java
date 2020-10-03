package me.antoniocaccamo.poc.mongodb;

import com.mongodb.reactivestreams.client.MongoClient;
import io.micronaut.configuration.picocli.PicocliRunner;

import io.micronaut.context.annotation.Value;
import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.poc.mongodb.collection.TourCollection;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.inject.Inject;

@Command(name = "poc-mongodb", description = "...",
        mixinStandardHelpOptions = true)
@Slf4j
public class PocMongodbCommand implements Runnable {

    @Inject
    private TourCollection tourCollection;



    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(PocMongodbCommand.class, args);
    }

    public void run() {
        // business logic here
        if (verbose) {
            System.out.println("Hi!");
        }

        Flowable.fromPublisher(tourCollection.getCollection().find())
                .subscribe(
                        document -> log.info("\tdocument : {}", document),
                        t -> log.error("{}", t),
                        () -> log.info("done !")

                )
        ;
    }


}
