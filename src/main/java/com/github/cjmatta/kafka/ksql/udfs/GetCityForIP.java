package com.github.cjmatta.kafka.ksql.udfs;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.EnterpriseResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Location;
import io.confluent.ksql.function.udaf.UdafDescription;
import io.confluent.ksql.function.udf.Udf;
import sun.misc.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.util.Map;

@UdafDescription(name="geoip_lookup", description = "Function to lookup ip -> city information ")
public class GetCityForIP {
    @Udf(description = "returns city from IP input")
    public String getcityforip(String ip) {
      try {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File database = new File(classLoader.getResource("GeoLite2-City_20181009/GeoLite2-City.mmdb").toURI());
        DatabaseReader reader = new DatabaseReader.Builder(database).build();

        InetAddress ipAddress = InetAddress.getByName(ip);

        CityResponse response = reader.city(ipAddress);
        City city = response.getCity();
        return city.getName();
      } catch (IOException | GeoIp2Exception | URISyntaxException e) {
        System.out.println(e.toString());
        return null;
      }
  }
}
