KSQL example UDF for looking up a City for an IP address.
### Pre-requisite
You'll need to supply your own GeoLite database, unzip it into `main/resources`, and specify the filename in `src/main/java/com/github/cjmatta/kafka/ksql/udfs/GetCityForIP.java`

### Build
```
mvn clean package
```

and move the jar `target/ksql-udf-geoip-1.0-SNAPSHOT-jar-with-dependencies.jar` into the `ext/` directory in your KSQL installation. 

### Usage
```sql
SELECT ip, getcityforip(ip) from eventstream;
```
