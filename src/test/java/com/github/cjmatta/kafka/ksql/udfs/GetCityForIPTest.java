package com.github.cjmatta.kafka.ksql.udfs;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class GetCityForIPTest {

  private GetCityForIP udf;
  @Before
  public void setUp() {
    udf = new GetCityForIP();
    configure("/Users/chris/Downloads/GeoLite2-City_20181009/GeoLite2-City.mmdb");
  }

  @Test
  public void getCityForIPTest() throws IOException, GeoIp2Exception {
    assertEquals("Philadelphia", udf.getcityforip("68.80.162.250"));
  }

  private void configure(String mmdbPath) {
    Map<String, String> config = new HashMap<String, String>();
    config.put("geolite.db.path", mmdbPath);
    udf.configure(Collections.unmodifiableMap(new LinkedHashMap<String, String>(config)));
  }
}