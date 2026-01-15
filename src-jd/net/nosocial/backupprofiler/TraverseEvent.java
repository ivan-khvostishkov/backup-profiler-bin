package net.nosocial.backupprofiler;

import java.io.File;

public interface TraverseEvent {
  void done();
  
  void skip(File paramFile);
  
  void traverse(File paramFile, String paramString);
  
  void errorListFiles(File paramFile);
}


/* Location:              C:\Users\Lenovo\IdeaProjects\backup-profiler-bin\backupProfiler.jar!\net\nosocial\backupprofiler\TraverseEvent.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */