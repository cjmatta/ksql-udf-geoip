package com.github.cjmatta.kafka.ksql.udfs;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

public class GetCityForIPTest {

  @Test
  public void getCityForIP() throws IOException, GeoIp2Exception {
    GetCityForIP getCityForIP = new GetCityForIP();
    assertEquals("Philadelphia", getCityForIP.getcityforip("68.80.162.250"));
  }
}