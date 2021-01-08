package com.darazscout.scraper.util;

/**
 * @author Muhammad Haris
 * @date 08-Jan-21
 */
public abstract class StringUtil {

  private StringUtil() {
  }

  public static double extractNumeric(String s) {
    //str.replaceAll("[^\\d.]", "")
    return Double.parseDouble(s.replaceAll("[^0-9.]", ""));
  }
}
