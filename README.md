

  ## start

    docker run --name mongo-srv -v /opt/development/docker/data/mongodb/:/data/db -p 27017:27017 -d mongo
    
    docker exec -it mongo-srv bash
 
    root@4e4b3a400473:/# mongo
 
    > db
    test
    
    > use learning_mongo
    switched to db learning_mongo
    
    > show dbs
    admin 0.000GB    
    config 0.000GB    
    local 0.000GB

	> db.cars.insert({"vendor":"subaru"})
	WriteResult({ "nInserted" : 1 })

	> show dbs
	admin 0.000GB
	config 0.000GB
	learning_mongo 0.000GB
	local 0.000GB
	> for ( i=0; i < 10000; i++) { db.numbers.insert({"nbr":i}) }
	WriteResult({ "nInserted" : 1 })
	> db.numbers.find({"nbr": 400})
	{ "_id" : ObjectId("5d8884babe46e369e6675d2c"), "nbr" : 400 }
	> db.numbers.find({"nbr": 400}).execution()
	> db.numbers.find({"nbr": 400}).explain("executionStats")
	> db.numbers.createIndex({nbr:1})
	> db.numbers.find({"nbr": 400}).explain("executionStats")
  
  ## import

	root@4e4b3a400473:/# mongoimport -d learning_monogo -c tours --jsonArray \
	    --file Ex_Files_Learning_MongoDB/Exercise Files/Chapter2/02_02/Start/tours.json
	2019-09-23T09:05:08.057+0000 connected to: mongodb://localhost/
	2019-09-23T09:05:08.090+0000 29 document(s) imported successfully. 0 document(s) failed to import.   
	
	root@4e4b3a400473:/# mongo 
	> use learning_mongo
	switched to db learning_mongo
	> show collections
	cars
	tours
	> db.tours.count()
	29
	> db.tours.find({"tourTags" : "hiking"})

## crud

### create	
	db.tours.insert({ "tourName" : "The Wines of Santa Cruz", 
	..."tourLength": 3, 
	..."tourDescription" : "Discover Santa Cruz's winery", 
	..."tourPrice" : 500, 
	..."tourTags": ["wine", "SantaCruz"]
	... }
	... )
	WriteResult({ "nInserted" : 1 })

### update	

	db.tours.update({"tourName":"The Wines of Santa Cruz"},{
	... $set:{"tourRegion":"Central Coast"}
	...}
	...)
	WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })


	db.tours.update({"tourName":"The Wines of Santa Cruz"}, {
	...$addToSet : {"tourTag":"boardwalk"}
	...}
	...)
	WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })

### delete

	> db.tours.remove({"tourName":"The Wines of Santa Cruz"})
	WriteResult({ "nRemoved" : 1 })
	> db.tours.find({"tourTags" : "wine"}).count()
	1
	

	> db.tours.drop()
	true
	> db.tours.count()
	0

## indexing
    > db.tours.find({"tourPackage":"Taste of California"}).explain("executionStats")
    {
    	"queryPlanner" : {
    		"plannerVersion" : 1,
    		"namespace" : "learning_mongo.tours",
    		"indexFilterSet" : false,
    		"parsedQuery" : {
    			"tourPackage" : {
    				"$eq" : "Taste of California"
    			}
    		},
    		"queryHash" : "6AA4109D",
    		"planCacheKey" : "6AA4109D",
    		"winningPlan" : {
    			"stage" : "COLLSCAN",
    			"filter" : {
    				"tourPackage" : {
    					"$eq" : "Taste of California"
    				}
    			},
    			"direction" : "forward"
    		},
    		"rejectedPlans" : [ ]
    	},
    	"executionStats" : {
    		"executionSuccess" : true,
    		"nReturned" : 4,
    		"executionTimeMillis" : 0,
    		"totalKeysExamined" : 0,
    		"totalDocsExamined" : 29,
    		"executionStages" : {
    			"stage" : "COLLSCAN",
    			"filter" : {
    				"tourPackage" : {
    					"$eq" : "Taste of California"
    				}
    			},
    			"nReturned" : 4,
    			"executionTimeMillisEstimate" : 0,
    			"works" : 31,
    			"advanced" : 4,
    			"needTime" : 26,
    			"needYield" : 0,
    			"saveState" : 0,
    			"restoreState" : 0,
    			"isEOF" : 1,
    			"direction" : "forward",
    			"docsExamined" : 29
    		}
    	},
    	"serverInfo" : {
    		"host" : "4e4b3a400473",
    		"port" : 27017,
    		"version" : "4.2.0",
    		"gitVersion" : "a4b751dcf51dd249c5865812b390cfd1c0129c30"
    	},
    	"ok" : 1
    }
    > 
	> db.tours.createIndex({"tourPackage":1})
	{
		"createdCollectionAutomatically" : false,
		"numIndexesBefore" : 1,
		"numIndexesAfter" : 2,
		"ok" : 1
	}

    > db.tours.find({"tourPackage":"Taste of California"}).explain("executionStats")
    {
    	"queryPlanner" : {
    		"plannerVersion" : 1,
    		"namespace" : "learning_mongo.tours",
    		"indexFilterSet" : false,
    		"parsedQuery" : {
    			"tourPackage" : {
    				"$eq" : "Taste of California"
    			}
    		},
    		"queryHash" : "6AA4109D",
    		"planCacheKey" : "79DB31BC",
    		"winningPlan" : {
    			"stage" : "FETCH",
    			"inputStage" : {
    				"stage" : "IXSCAN",
    				"keyPattern" : {
    					"tourPackage" : 1
    				},
    				"indexName" : "tourPackage_1",
    				"isMultiKey" : false,
    				"multiKeyPaths" : {
    					"tourPackage" : [ ]
    				},
    				"isUnique" : false,
    				"isSparse" : false,
    				"isPartial" : false,
    				"indexVersion" : 2,
    				"direction" : "forward",
    				"indexBounds" : {
    					"tourPackage" : [
    						"[\"Taste of California\", \"Taste of California\"]"
    					]
    				}
    			}
    		},
    		"rejectedPlans" : [ ]
    	},
    	"executionStats" : {
    		"executionSuccess" : true,
    		"nReturned" : 4,
    		"executionTimeMillis" : 1,
    		"totalKeysExamined" : 4,
    		"totalDocsExamined" : 4,
    		"executionStages" : {
    			"stage" : "FETCH",
    			"nReturned" : 4,
    			"executionTimeMillisEstimate" : 0,
    			"works" : 5,
    			"advanced" : 4,
    			"needTime" : 0,
    			"needYield" : 0,
    			"saveState" : 0,
    			"restoreState" : 0,
    			"isEOF" : 1,
    			"docsExamined" : 4,
    			"alreadyHasObj" : 0,
    			"inputStage" : {
    				"stage" : "IXSCAN",
    				"nReturned" : 4,
    				"executionTimeMillisEstimate" : 0,
    				"works" : 5,
    				"advanced" : 4,
    				"needTime" : 0,
    				"needYield" : 0,
    				"saveState" : 0,
    				"restoreState" : 0,
    				"isEOF" : 1,
    				"keyPattern" : {
    					"tourPackage" : 1
    				},
    				"indexName" : "tourPackage_1",
    				"isMultiKey" : false,
    				"multiKeyPaths" : {
    					"tourPackage" : [ ]
    				},
    				"isUnique" : false,
    				"isSparse" : false,
    				"isPartial" : false,
    				"indexVersion" : 2,
    				"direction" : "forward",
    				"indexBounds" : {
    					"tourPackage" : [
    						"[\"Taste of California\", \"Taste of California\"]"
    					]
    				},
    				"keysExamined" : 4,
    				"seeks" : 1,
    				"dupsTested" : 0,
    				"dupsDropped" : 0
    			}
    		}
    	},
    	"serverInfo" : {
    		"host" : "4e4b3a400473",
    		"port" : 27017,
    		"version" : "4.2.0",
    		"gitVersion" : "a4b751dcf51dd249c5865812b390cfd1c0129c30"
    	},
    	"ok" : 1
    }
    > 	
    
	db.tours.find({ 
	... "tourPrice" : {$lte:500} , 
	... "tourLength": {$lte:3} 
	...}
	...)

	db.tours.createIndex({ "tourPrice" : 1 , tourLength: 1 })


### unique


### text

	> db.tours.createIndex( { tourDescription:"text",   tourBlurb:"text" })
	{
		"createdCollectionAutomatically" : false,
		"numIndexesBefore" : 1,
		"numIndexesAfter" : 2,
		"ok" : 1
	}

	db.tours.find(
	... {$text : {$search:"wine"}}, 
	... {score:{$meta:"textScore"}
	... }).pretty(

  
## Query

	> db.tours.find({ 
	..."tourPrice" : {$lte:500} , 
	..."tourLength": {$lte:3} 
	..}, {
	  "_id":0,"tourName":1,
    ... "tourPrice":2})
    ... .pretty()
    ... .sort({"tourPrice":1})

	> db.tours.find({$text : {$search:"wine"}}, {score:{$meta:"textScore"}}).sort({score:{$meta:"textScore"}}).pretty()
		
	> db.tours.find({ tourDescription : 

    {$regex:/valley/i}} , {"_id":0,tourName:1})
    { "tourName" : "The Death Valley Survivor's Trek" }
    { "tourName" : "Day Spa Package" }
    { "tourName" : "Restoration Package" }
    { "tourName" : "Ski Lake Tahoe" }
    
    > db.tours.find({ tourDescription : /valley/i} , {"_id":0,tourName:1})
    { "tourName" : "The Death Valley Survivor's Trek" }
    { "tourName" : "Day Spa Package" }
    { "tourName" : "Restoration Package" }
    { "tourName" : "Ski Lake Tahoe" }

## Model your schema

    There are two ways to include information in your schema, either by inclusion into another document or by reference to documents in another collection.
    
    In this case, we're going to have an array of movies for each person, and those movie names will map to items in the movies collection. 
    
    So basically, for any query involving both a movie and a related person, we will need to query both collections. 
    
    We'll be querying two collections to get what we're looking for. Before we do the import, let's look at the various types of information we have
    
        root@4e4b3a400473:/# mongoimport -d learning_mongo -c people --jsonArray --file /data/db/people.json 
        2019-09-23T12:44:33.528+0000	connected to: mongodb://localhost/
        2019-09-23T12:44:33.558+0000	20 document(s) imported successfully. 0 document(s) failed to import.
        root@4e4b3a400473:/# mongoimport -d learning_mongo -c movie --jsonArray --file /data/db/mo          
        mongod.lock  movies.json  
        root@4e4b3a400473:/# mongoimport -d learning_mongo -c movies --jsonArray --file /data/db/movies.json 
        2019-09-23T12:44:54.690+0000	connected to: mongodb://localhost/
        2019-09-23T12:44:54.710+0000	55 document(s) imported successfully. 0 document(s) failed to import.
    
    > var movieName="The Avengers"
    > var movie = db.movies.findOne({ _id : movieName})
    > movie.cast = []
    > var persons =  db.people.find({ movies : movieName})
    > persons.forEach( function(person){
    ... movie.cast.push(person)
    ... })
    > movie
    {
    	"_id" : "The Avengers",
    	"overview" : "When an unexpected enemy emerges and threatens global safety and security, Nick Fury, director of the international peacekeeping agency known as S.H.I.E.L.D., finds himself in need of a team to pull the world back from the brink of disaster. Spanning the globe, a daring recruitment effort begins!",
    	"genres" : [
    		"Science Fiction",
    		"Action",
    		"Adventure"
    	],
    	"img" : "/cezWGskPY5x7GaglTTRN4Fugfb8.jpg",
    	"popularity" : 8.185595,
    	"cast" : [
    		{
    			"_id" : "Jeremy Renner",
    			"movies" : [
    				"The Avengers",
    				"Thor",
    				"Avengers: Age of Ultron"
    			],
    			"img" : "/g8gheNEdPSXWH5SnjfjTYWj5ziU.jpg",
    			"popularity" : 14.416074
    		},
    		{
    			"_id" : "Chris Evans",
    			"movies" : [
    				"The Avengers",
    				"Captain America: The First Avenger",
    				"Avengers: Age of Ultron"
    			],
    			"img" : "/ueIzur9vURNLoqZCdfWltnpuZTq.jpg",
    			"popularity" : 14.415341
    		}
    	]
    }
	
## Aggregation

    > db.tours.aggregate([ { $group : { _id : '$tourPackage', count:{$sum:1}}}])
    { "_id" : "Kids California", "count" : 3 }
    { "_id" : "Taste of California", "count" : 4 }
    { "_id" : "Nature Watch", "count" : 3 }
    { "_id" : "Cycle California", "count" : 3 }
    { "_id" : "From Desert to Sea", "count" : 2 }
    { "_id" : "Snowboard Cali", "count" : 3 }
    { "_id" : "Backpack Cal", "count" : 5 }
    { "_id" : "California Calm", "count" : 3 }
    { "_id" : "California Hot springs", "count" : 3 }
 

    > db.tours.aggregate([ { $group : { _id : '$tourOrganizer.organizerName', average : {$avg :'$tourPrice'},  count:{$sum:1}}}, {$out : 'prices' }])
    > db.prices.find()
    { "_id" : "Martin Jones", "average" : 2187.5, "count" : 4 }
    { "_id" : "Alison Birch", "average" : 418.75, "count" : 4 }
    { "_id" : "John Smith", "average" : 480, "count" : 5 }
    { "_id" : "Amber Fineway", "average" : 558.3333333333334, "count" : 3 }
    { "_id" : "Ishmael Fisher", "average" : 700, "count" : 4 }
    { "_id" : "Tony Hawkins", "average" : 900, "count" : 3 }
    { "_id" : "Jack Kerouak", "average" : 200, "count" : 1 }
    { "_id" : "Mickey Mouse", "average" : 1200, "count" : 1 }
    { "_id" : "Smokey Bear", "average" : 550, "count" : 2 }
    { "_id" : "Lance Armstrong", "average" : 1850, "count" : 2 }

    




