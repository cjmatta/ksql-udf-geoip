KSQL example UDF for looking up a City for an IP address.
### Pre-requisite
You'll need to supply your own GeoLite database, specify it's location as a JVM property when starting KSQL:
```$xslt
KSQL_OPTS="-Dgeolite.db.path=/Users/chris/Downloads/GeoLite2-City_20181009/GeoLite2-City.mmdb" ksql-server-start /etc/ksql/ksql.properties
```

### Build
```
mvn clean package
```

and move the jar `target/ksql-udf-geoip-1.0-SNAPSHOT-jar-with-dependencies.jar` into the `ext/` directory in your KSQL installation. 

### Usage
```sql
SELECT ip, getcityforip(ip) from eventstream;
```
