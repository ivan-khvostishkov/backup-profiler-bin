/*    */ package net.nosocial.backupprofiler;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.nio.file.Path;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathTime
/*    */ {
/*    */   private String path;
/*    */   private long time;
/*    */   private long acknowledgedTime;
/*    */   
/*    */   public PathTime(String path, long time) {
/* 16 */     this.path = path;
/* 17 */     this.time = time;
/*    */   }
/*    */   
/*    */   public String getPath() {
/* 21 */     return this.path;
/*    */   }
/*    */   
/*    */   public long getTime() {
/* 25 */     return this.time;
/*    */   }
/*    */   
/*    */   public void setTime(long time) {
/* 29 */     this.time = time;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 34 */     return String.format("%6s %4s %s", new Object[] { FormatData.humanReadableByteCount(this.time), 
/* 35 */           getAcknowledgedPercentAsString(), this.path });
/*    */   }
/*    */   
/*    */   private String getAcknowledgedPercentAsString() {
/* 39 */     int percent = getAcknowledgedPercent();
/* 40 */     if (percent == 0) {
/* 41 */       return "";
/*    */     }
/* 43 */     return "" + percent + "%";
/*    */   }
/*    */   
/*    */   private int getAcknowledgedPercent() {
/* 47 */     if (this.time == 0L) {
/* 48 */       return 0;
/*    */     }
/* 50 */     return (int)(this.acknowledgedTime * 100L / this.time);
/*    */   }
/*    */   
/*    */   public void initAcknowledgedTime() {
/* 54 */     this.acknowledgedTime = 0L;
/*    */   }
/*    */   
/*    */   public void addAcknowledgedTime(long time) {
/* 58 */     this.acknowledgedTime += time;
/*    */   }
/*    */   
/*    */   public String getPathName() {
/* 62 */     return (new File(this.path)).toPath().toString();
/*    */   }
/*    */   
/*    */   public String getFileName() {
/* 66 */     Path path = (new File(this.path)).toPath();
/* 67 */     if (path.getRoot().equals(path)) {
/* 68 */       return path.toString();
/*    */     }
/* 70 */     return path.getFileName().toString();
/*    */   }
/*    */   
/*    */   public String getParentName() {
/* 74 */     Path path = (new File(this.path)).toPath();
/* 75 */     if (path.getRoot().equals(path)) {
/* 76 */       return path.toString();
/*    */     }
/* 78 */     return path.getParent().toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Lenovo\IdeaProjects\backup-profiler-bin\backupProfiler.jar!\net\nosocial\backupprofiler\PathTime.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */