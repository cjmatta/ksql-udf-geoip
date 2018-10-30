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
    configure(getClass().getClassLoader().getResource("GeoIP2-City-Test.mmdb").getFile());
  }

  @Test
  public void getCityForIPTest() throws IOException, GeoIp2Exception {
    assertEquals("London", udf.getcityforip("81.2.69.160"));
  }

  private void configure(String mmdbPath) {
    Map<String, String> config = new HashMap<String, String>();
    config.put("geolite.db.path", mmdbPath);
    udf.configure(Collections.unmodifiableMap(new LinkedHashMap<String, String>(config)));
  }
}