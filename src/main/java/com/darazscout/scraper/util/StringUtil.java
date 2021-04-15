package com.darazscout.scraper.util;

public abstract class StringUtil {

  private StringUtil() {
  }

  public static double extractNumeric(String s) {
    //str.replaceAll("[^\\d.]", "")
    return Double.parseDouble(s.replaceAll("[^0-9.]", ""));
  }
}
