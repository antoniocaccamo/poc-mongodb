
    docker run --name mongo-srv -v /opt/development/docker/data/mongodb/:/data/db -p 27017:27017 -d mongo

    docker exec -it  mongo-srv bash

    root@4e4b3a400473:/# mongo

        > db
        test
        
        > use learning_mongo
        switched to db learning_mongo
        
        > show dbs
        admin   0.000GB
        config  0.000GB
        local   0.000GB

        > db.cars.insert({"vendor":"subaru"})
        WriteResult({ "nInserted" : 1 })
        
        > show dbs
        admin           0.000GB
        config          0.000GB
        learning_mongo  0.000GB
        local           0.000GB

        > for ( i=0; i < 10000; i++) { db.numbers.insert({"nbr":i}) }
        WriteResult({ "nInserted" : 1 })
        
        > db.numbers.find({"nbr": 400})
        { "_id" : ObjectId("5d8884babe46e369e6675d2c"), "nbr" : 400 }

        > db.numbers.find({"nbr": 400}).execution()

        > db.numbers.find({"nbr": 400}).explain("executionStats")

        > db.numbers.createIndex({nbr:1})

        > db.numbers.find({"nbr": 400}).explain("executionStats")


root@4e4b3a400473:/# mongoimport -d learning_monogo -c tours --jsonArray --file     
[Ex_Files_Learning_MongoDB/Exercise Files/Chapter2/02_02/Start/tours.json](Ex_Files_Learning_MongoDB/Exercise Files/Chapter2/02_02/Start/tours.json)

    2019-09-23T09:05:08.057+0000	connected to: mongodb://localhost/
    2019-09-23T09:05:08.090+0000	29 document(s) imported successfully. 0 document(s) failed to import.


    root@4e4b3a400473:/# mongo

        > use learning_mongo
        switched to db learning_mongo
        > show collections
        cars
        tours
        > db.tours.count()
        29
        >  db.tours.find({"tourTags" : "hiking"})
   
