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
