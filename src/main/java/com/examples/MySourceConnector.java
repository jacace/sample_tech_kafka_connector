package com.examples;

import java.util.List;
import java.util.Map;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySourceConnector extends SourceConnector {

  private static Logger log = LoggerFactory.getLogger(MySourceConnector.class);
  private MySourceConnectorConfig config;
  private SourceConnector connector = null;

  @Override
  public String version() {
    return VersionUtil.getVersion();
  }

  @Override
  public Class<? extends Task> taskClass() {
    return connector.taskClass();
  }

  @Override
  public void stop() {
    connector.stop();
  }

  @Override
  public ConfigDef config() {
    return MySourceConnectorConfig.conf();
  }

  @Override
  public List<Map<String, String>> taskConfigs(int i) {
    return connector.taskConfigs(i);
  }

  @Override
  public void start(Map<String, String> map) {

    log.info("Loading config");
    config = new MySourceConnectorConfig(map);

    ClassLoader classLoader = getClass().getClassLoader();
    Class aClass;
    String className = config.getMy();
    log.info("Loading class: " + className);

    try {
      aClass = classLoader.loadClass(className);
      connector = (SourceConnector) aClass.newInstance();
      connector.start(map);
      log.info("Class loaded");
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
