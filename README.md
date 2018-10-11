KSQL example UDF for looking up a City for an IP address.
### Pre-requisite
You'll need to supply your own GeoLite database, specify it's location using the `function.getcityforip.geolite.db.path` property in your ksql-server.properties before starting KSQL.


### Build
```
mvn clean package
```

and move the jar `target/ksql-udf-geoip-1.0-SNAPSHOT-jar-with-dependencies.jar` into the `ext/` directory in your KSQL installation. 

### Usage
```sql
SELECT ip, getcityforip(ip) from eventstream;
```
