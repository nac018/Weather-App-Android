const express = require("express");
const app = express();
const https = require("https");

app.all("/*", function(req,res,next){
    res.header("Access-Control-Allow-Origin","*");
    res.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
    res.header("Access-Control-Allow-Headers", "Content-Type, Authorization, Content-Length, X-Requested-With");
    next();
});

app.get("/search",(req,res) => {
    var map_key = "AIzaSyDBV5qdnKDITPvI5CaxZNA9kzD5y7GC1oA";
    var map_url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + encodeURIComponent(req.query.street + req.query.city + req.query.state) + "&key=" + map_key;
    
    https.get(map_url ,(req2,res2) => {
        var str = "";
        req2.on("data", (data) => {
            str += data;
        });
        req2.on("end",() => {
            var jsonObj = JSON.parse(str);
            if (jsonObj.status == "ZERO_RESULTS"){
                res.send({status:"ZERO_RESULTS"});
            }
            else {
                var weather_key = "5a56c3ad0e7deb6af73391c1b7d31450";
                var weather_url = "https://api.darksky.net/forecast/" + weather_key + "/" + jsonObj.results[0].geometry.location.lat + "," + jsonObj.results[0].geometry.location.lng;
                https.get(weather_url,(req3,res3) =>{
                    var str2 = "";
                    req3.on("data",(data) => {
                        str2 += data;
                    });
                    req3.on("end",() => {
                        return res.send(str2);
                    });
                });
            }
        });
    });
});

app.get("/seal",(req,res) => {
    var map_key = "AIzaSyDBV5qdnKDITPvI5CaxZNA9kzD5y7GC1oA";
    var search_id = "018206582045148543133:br519fcy2yt";
    var search_url = "https://www.googleapis.com/customsearch/v1?q="+ req.query.city +"&cx="+ search_id +"&imgSize=huge&imgType=news&num=8&searchType=image&key=" + map_key;
    
    https.get(search_url ,(req2,res2) => {
        var str = "";
        req2.on("data", (data) => {
            str += data;
        });
        req2.on("end",() => {
            //var jsonObj = JSON.parse(str);
            console.log(str);
            return res.send(str);
        });
    });
});

app.get("/time",(req,res) => {
    var weather_key = "5a56c3ad0e7deb6af73391c1b7d31450";
    var weather_url = "https://api.darksky.net/forecast/" + weather_key + "/" + req.query.lat + "," + req.query.lng + "," + req.query.time;
    
    https.get(weather_url ,(req2,res2) => {
        var str = "";
        req2.on("data", (data) => {
            str += data;
        });
        req2.on("end",() => {
            //var jsonObj = JSON.parse(str);
            return res.send(str);
        });
    });
});

app.get("/autocomplete",(req,res) => {
    var map_key = "AIzaSyDBV5qdnKDITPvI5CaxZNA9kzD5y7GC1oA";
    var ac_url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input="+ req.query.cityInput +"&types=(cities)&language=en&key=" + map_key;
    
    https.get(ac_url ,(req2,res2) => {
        var str = "";
        req2.on("data", (data) => {
            str += data;
        });
        req2.on("end",() => {
            var jsonObj = JSON.parse(str);
            for (i = 0; i < jsonObj.predictions.length; i++){
                console.log(jsonObj.predictions[i].structured_formatting.main_text);
            }
            return res.send(str);
        });
    });
});

app.get("/current",(req,res) => {
    var weather_key = "5a56c3ad0e7deb6af73391c1b7d31450";
    var weather_url = "https://api.darksky.net/forecast/" + weather_key + "/" + req.query.lat + "," + req.query.lng;
    
    https.get(weather_url ,(req2,res2) => {
        var str = "";
        req2.on("data", (data) => {
            str += data;
        });
        req2.on("end",() => {
            //var jsonObj = JSON.parse(str);
            return res.send(str);
        });
    });
});

app.get("/favsearch",(req,res) => {
    var map_key = "AIzaSyDBV5qdnKDITPvI5CaxZNA9kzD5y7GC1oA";
    var map_url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + encodeURIComponent(req.query.street + req.query.city + req.query.state) + "&key=" + map_key;
    
    https.get(map_url ,(req2,res2) => {
        var str = "";
        req2.on("data", (data) => {
            str += data;
        });
        req2.on("end",() => {
            var jsonObj = JSON.parse(str);
            if (jsonObj.status == "ZERO_RESULTS"){
                res.send({status:"ZERO_RESULTS"});
            }
            else {
                var weather_key = "5a56c3ad0e7deb6af73391c1b7d31450";
                var weather_url = "https://api.darksky.net/forecast/" + weather_key + "/" + jsonObj.results[0].geometry.location.lat + "," + jsonObj.results[0].geometry.location.lng;
                https.get(weather_url,(req3,res3) =>{
                    var str2 = "";
                    req3.on("data",(data) => {
                        str2 += data;
                    });
                    req3.on("end",() => {
                        return res.send(str2);
                    });
                });
            }
        });
    });
});


app.use((req, res, next) => {
    res.status(404).send("Sorry, that route doesn't exist. Have a nice day :)");
});

app.listen(8080, () => {
    console.log("Listening on port 8080..");
});