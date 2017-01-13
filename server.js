var express = require("express");
var app = express();
var pg = require('pg');
var ip = require('ip');
var ipaddress = ip.address();
var client = new pg.Client();
const conString = 'postgres://soni:sy05dec@pgdb/appdb' // make sure to match your own database's credentials
app.get('/', function (req, res, next) {  
  pg.connect(conString, function (err, client, done) {
    if (err) {
      // pass the error to the express error handler
      return next(err)
    }
   client.query('SELECT * FROM TEST', function (err, result) {
    done()

    if (err) {
      return console.error('error happened during query', err)
    }
    res.json(result.rows)
    /*client.query('INSERT INTO users (name, age) VALUES ($1, $2);', [user.name, user.age], function (err, result) {
      done() //this done callback signals the pg driver that the connection can be closed or returned to the connection pool

      if (err) {
        // pass the error to the express error handler
        return next(err)
      }

      res.send(200)*/
    })
  })
});
app.listen("3000", function(err){
	if(err){
		console.error(err);
		return;
	}
	console.log(`URL: http://${process.env.MIPADDR}:${process.env.MPORT}`);
});
