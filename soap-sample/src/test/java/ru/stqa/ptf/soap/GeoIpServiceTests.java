package ru.stqa.ptf.soap;

import com.lavasoft.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {

  @Test
  public  void testMyIp (){
    String location = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("31.132.178.228");
    assertEquals(location, "<GeoIP><Country>RU</Country><State>10</State></GeoIP>");
  }

  @Test
  public  void testInvalidIp (){
    String location = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("31.132.178.xxx");
    assertEquals(location, "<GeoIP><Country>RU</Country><State>10</State></GeoIP>");
  }
}
