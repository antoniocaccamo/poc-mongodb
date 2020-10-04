package me.antoniocaccamo.poc.mongodb;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.reactivex.functions.Action;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.poc.mongodb.collection.TourCollection;
import me.antoniocaccamo.poc.mongodb.model.Tour;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.inject.Inject;

import com.mongodb.client.FindIterable;

import java.util.function.Consumer;

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

        FindIterable<Tour> findIterable = tourCollection.getCollection().find();
        findIterable.forEach((Consumer<? super Tour>) tour -> log.info("\ttour : {}", tour));

    }


}
