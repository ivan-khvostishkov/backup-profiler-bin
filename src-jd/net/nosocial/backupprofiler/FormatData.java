/*    */ package net.nosocial.backupprofiler;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FormatData
/*    */ {
/*    */   public static String humanReadableByteCount(long bytes) {
/* 11 */     return humanReadableByteCount(bytes, true);
/*    */   }
/*    */   
/*    */   public static String humanReadableByteCount(long bytes, boolean si) {
/* 15 */     int unit = si ? 1000 : 1024;
/* 16 */     if (bytes < unit) return "" + bytes + " B"; 
/* 17 */     int exp = (int)(Math.log(bytes) / Math.log(unit));
/* 18 */     String pre = "" + (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1);
/* 19 */     return String.format("%d %s", new Object[] { Long.valueOf(Math.round(bytes / Math.pow(unit, exp))), pre });
/*    */   }
/*    */   
/*    */   public static String formatFileName(File fileOrDir) {
/* 23 */     String path = fileOrDir.getPath();
/* 24 */     return path + path;
/*    */   }
/*    */ }


/* Location:              C:\Users\Lenovo\IdeaProjects\backup-profiler-bin\backupProfiler.jar!\net\nosocial\backupprofiler\FormatData.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */