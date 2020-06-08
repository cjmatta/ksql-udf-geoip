package com.github.cjmatta.kafka.ksql.udfs;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import org.apache.kafka.common.Configurable;
import io.confluent.common.config.ConfigException;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;
import io.confluent.ksql.util.KsqlConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

@UdfDescription(name="getcityforip", description = "Function to lookup ip -> city information ")
public class GetCityForIP implements Configurable {

  private DatabaseReader reader;
  private Logger log = LoggerFactory.getLogger(GetCityForIP.class);


  @Override
  public void configure(final Map<String, ?> props) {

    log.info("Configuring GetCityForIP function");

    String KSQL_FUNCTIONS_GETCITYFORIP_GEOLITE_DB_PATH_CONFIG = KsqlConfig.KSQL_FUNCTIONS_PROPERTY_PREFIX + "getcityforip.geolite.db.path";

    if (!props.containsKey(KSQL_FUNCTIONS_GETCITYFORIP_GEOLITE_DB_PATH_CONFIG)) {
      throw new ConfigException("Required property "+ KSQL_FUNCTIONS_GETCITYFORIP_GEOLITE_DB_PATH_CONFIG + " not found!");
    }

    File database;
    final String geoliteDbPath = (String)props.get(KSQL_FUNCTIONS_GETCITYFORIP_GEOLITE_DB_PATH_CONFIG);

    try {
      database = new File(geoliteDbPath);
      reader = new DatabaseReader.Builder(database).build();
      log.info("loaded GeoIP database from " + geoliteDbPath.toString());
    } catch (IOException e) {
      log.error("Problem loading GeoIP database: " + e);
      throw new ExceptionInInitializerError(e);
    }
  }

  @Udf(description = "returns city from IP input")
  public String getcityforip(
    @UdfParameter(value = "ip", description = "the IP address to lookup in the geoip database") final String ip
  ) {
    log.info("IP: " + ip);

    if (reader == null ) {
      log.error("No DB found");
    }

    try {
      log.debug("Lookup up City for IP: " + ip);
      InetAddress ipAddress = InetAddress.getByName(ip);
      CityResponse response = reader.city(ipAddress);
      City city = response.getCity();
      log.debug("Got result: " + city.getName().toString());
      return city.getName();
    } catch (IOException | GeoIp2Exception e) {
      log.error("Error looking up City for IP: " + e);
      return null;
    }
  }
}
