package com.examples;

import java.util.Map;

import org.slf4j.Logger;

/**
 * Created by jacace on March/2021.
 */
class Utils {

  public static String getConnectorTaskName() {
    return "CONNECTOR_TASK_NAME";
  }

  public static String getVersion() {
    try {
      return Utils.class.getPackage().getImplementationVersion();
    } catch (Exception ex) {
      return "0.0.0.1";
    }
  }

  public Object loadClass(String className) {
    ClassLoader classLoader = getClass().getClassLoader();
    Class aClass = null;
    Object obj = null;
    try {
      aClass = classLoader.loadClass(className);
      obj = aClass.newInstance();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return obj;
  }

  public static void printConfig(Map<String, String> map, Logger log) {
    int len = 0;
    String value = null;
    int STR_REMOVE_COUNT = 3;

    for (Map.Entry<String, String> entry : map.entrySet()) {
      value = entry.getValue().toString();
      len = value.length();
      if (len > STR_REMOVE_COUNT) {
        log.info("Param Name: " + entry.getKey() + ". Value: " + value.substring(len - STR_REMOVE_COUNT));
      } else {
        log.info("Param Name: " + entry.getKey() + ". Value: " + value);
      }

    }
  }

  public static void main(String[] args) {
    MySourceConnector mock = new MySourceConnector();
    mock.stop();
  }

}
