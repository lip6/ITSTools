package fr.lip6.move.gal.application;

import java.lang.management.ThreadInfo;
import java.util.Map;

public class DeadlockConsoleHandler implements DeadlockHandler {

  @Override
  public void handleDeadlock(final ThreadInfo[] deadlockedThreads) {
    if (deadlockedThreads != null) {
      System.err.println("Deadlock detected!");
      
      Map<Thread, StackTraceElement[]> stackTraceMap = Thread.getAllStackTraces();
      for (ThreadInfo threadInfo : deadlockedThreads) {
      
        if (threadInfo != null) {
      
          for (Thread thread : stackTraceMap.keySet()) {
        
            if (thread.getId() == threadInfo.getThreadId()) {
              System.err.println(threadInfo.toString().trim());
                
              for (StackTraceElement ste : thread.getStackTrace()) {
                  System.err.println("\t" + ste.toString().trim());
              }
            }
          }
        }
      }
    }
  }
}