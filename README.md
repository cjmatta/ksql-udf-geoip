KSQL example UDF for looking up a City for an IP address.

### Build
```
mvn clean package
```

and move the jar `target/ksql-udf-geoip-1.0-SNAPSHOT-jar-with-dependencies.jar` into the `ext/` directory in your KSQL installation. 

### Usage
```sql
SELECT ip, getcityforip(ip) from eventstream;
```
