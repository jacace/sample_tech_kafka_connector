package com.examples;

import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class MySourceTask extends SourceTask {

  static final Logger log = LoggerFactory.getLogger(MySourceTask.class);
  private SourceTask task = null;
  String className = null;
  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

  Timer timer = null;
  Boolean timerStarted = false;
  Boolean bRenewFlag = false;

  @Override
  public String version() {
    return Utils.getVersion();
  }

  @Override
  public void stop() {
    task.stop();
    timer.cancel();
    timerStarted = false;
  }

  @Override
  public void start(Map<String, String> map) {

    try {
      if (task == null) {
        log.info("Loading task class: " + className);
        className = map.get(Utils.getConnectorTaskName());
        task = SourceTask.class.cast(new Utils().loadClass(className));
      } else {
        log.info("Task class (" + className + ") already loaded");
      }

      task.start(map);
      log.info("Task started");
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (!timerStarted) {
      if (timer == null) {
        timer = new Timer();
      }

      // TODO: print changes and timer
      BackgroundThread thread = new BackgroundThread(this);
      int VAULT_TTL_MINS = 1;
      timer.schedule(thread, VAULT_TTL_MINS, VAULT_TTL_MINS);
      timerStarted = true;
    }

  }

  public void turnOnRenewFlag() {
    bRenewFlag = true;
    log.info("Callback started at: " + dtf.format(LocalDateTime.now()));
  }

  @Override
  public List<SourceRecord> poll() throws InterruptedException {
    if (bRenewFlag) {
      bRenewFlag = false;
      stop();
      Utils.printConfig(this.context.configs(), log);
      start(this.context.configs());      
    }

    return task.poll();
  }

}