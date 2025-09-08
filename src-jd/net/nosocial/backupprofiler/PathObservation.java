/*    */ package net.nosocial.backupprofiler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathObservation
/*    */ {
/*    */   private final long time;
/*    */   private final String path;
/*    */   
/*    */   public PathObservation(long time, String path) {
/* 13 */     this.time = time;
/* 14 */     this.path = path;
/*    */   }
/*    */   
/*    */   public long getTime() {
/* 18 */     return this.time;
/*    */   }
/*    */   
/*    */   public String getPath() {
/* 22 */     return this.path;
/*    */   }
/*    */ }


/* Location:              C:\Users\Lenovo\IdeaProjects\backup-profiler-bin\backupProfiler.jar!\net\nosocial\backupprofiler\PathObservation.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */