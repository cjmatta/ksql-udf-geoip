package com.github.cjmatta.kafka.ksql.udfs;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GetCityForIPTest {

  private GetCityForIP udf;
  @Before
  public void setUp() {
    udf = new GetCityForIP();
    File mmDb = new File("src/test/resources/GeoLite2-City/GeoLite2-City.mmdb");
    configure(mmDb.getAbsolutePath());
  }

  @Test
  public void getCityForIPTest() throws IOException, GeoIp2Exception {
    Map<String, String> cityMap = new HashMap<>();
    cityMap.put("128.106.20.194", "Singapore");
    cityMap.put("68.80.162.250", "Philadelphia");
    cityMap.put("49.217.88.22", "Taipei");
    cityMap.put("138.197.66.165", "Clifton");

    Iterator it = cityMap.entrySet().iterator();
    while(it.hasNext()) {
      Map.Entry pair = (Map.Entry) it.next();
      assertEquals(pair.getValue(), udf.getcityforip(pair.getKey().toString()));
    }
  }

  private void configure(String mmdbPath) {
    Map<String, String> config = new HashMap<String, String>();
    config.put("ksql.functions.getcityforip.geolite.db.path", mmdbPath);
    udf.configure(Collections.unmodifiableMap(new LinkedHashMap<String, String>(config)));
  }
}