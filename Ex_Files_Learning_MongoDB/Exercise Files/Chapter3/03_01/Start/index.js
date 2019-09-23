var client = require('mongodb').MongoClient;

var url = 'mongodb://localhost:27017/learning_mongo';

var findDocuments = function(db, callback) {

    var collection = db.collection("tours");

    collection.find().toArray( function( err, docs){
        console.log(docs);
        callback;
    })

};

client.connect(url, function(err, db){
    console.log("connected to db..")

    findDocuments(db, function(){
        db.close();
    })

});