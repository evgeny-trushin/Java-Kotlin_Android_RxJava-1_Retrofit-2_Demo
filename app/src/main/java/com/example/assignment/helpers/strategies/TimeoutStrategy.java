package com.example.assignment.helpers.strategies;

public class TimeoutStrategy {
  private final String TAG = TimeoutStrategy.class.getSimpleName();
  public TimeoutStrategy(Integer timeout) {
    this.timeout = timeout;
  }
  private volatile boolean isRunning = false;
  private volatile long startedTime = 0;
  private Integer timeout = 0;

  public boolean ifAllowedStartTheProcess() {
    synchronized (this) {
      if (!this.isRunning) {
        this.setRunning();
        return true;
      } else if (
          this.startedTime + timeout < System.currentTimeMillis() / 1000L
          ) {
        this.setRunning();
        return true;
      } else {
        return false;
      }
    }
  }

  public void setRunning() {
    this.isRunning = true;
    this.startedTime = System.currentTimeMillis() / 1000L;
  }

  public void setStop() {
    this.isRunning = false;
    this.startedTime = 0;
  }
}
