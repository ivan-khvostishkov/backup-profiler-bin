/*    */ package net.nosocial.backupprofiler;
/*    */ 
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.File;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Files;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProfileEventLog
/*    */   implements TraverseEvent
/*    */ {
/* 18 */   Logger logger = LoggerFactory.getLogger(ProfileEventLog.class);
/*    */   
/*    */   private final BufferedWriter sizeProfileWriter;
/*    */   private final FileWriter countProfileWriter;
/* 22 */   long totalSize = 0L;
/* 23 */   long totalCount = 0L;
/*    */   
/*    */   public ProfileEventLog(String outputDir) {
/*    */     try {
/* 27 */       this.sizeProfileWriter = new BufferedWriter(new FileWriter(new File(outputDir, "size/profile.log")));
/* 28 */       this.countProfileWriter = new FileWriter(new File(outputDir, "count/profile.log"));
/* 29 */     } catch (IOException e) {
/* 30 */       throw new IllegalArgumentException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void traverse(File file, String formattedFileName) {
/* 35 */     System.out.println(String.format("Traverse into file %s", new Object[] { file.toString() }));
/*    */     try {
/* 37 */       this.sizeProfileWriter.write(String.format("%d %s", new Object[] { Long.valueOf(this.totalSize), formattedFileName }));
/* 38 */       this.countProfileWriter.write(String.format("%d %s", new Object[] { Long.valueOf(this.totalCount), formattedFileName }));
/*    */       
/* 40 */       this.sizeProfileWriter.write(System.lineSeparator());
/* 41 */       this.countProfileWriter.write(System.lineSeparator());
/*    */     }
/* 43 */     catch (IOException e) {
/* 44 */       throw new IllegalStateException(e);
/*    */     } 
/* 46 */     this.totalSize += Files.isSymbolicLink(file.toPath()) ? 0L : file.length();
/* 47 */     this.totalCount++;
/*    */   }
/*    */   
/*    */   public void skip(File file) {
/* 51 */     System.out.println(String.format("Skipping file %s", new Object[] { file.toString() }));
/*    */   }
/*    */   
/*    */   public void done() {
/* 55 */     System.out.println("Done.");
/*    */     try {
/* 57 */       this.sizeProfileWriter.write(String.format("%d %d", new Object[] { Long.valueOf(this.totalSize), Long.valueOf(this.totalSize) }));
/* 58 */       this.countProfileWriter.write(String.format("%d %d", new Object[] { Long.valueOf(this.totalCount), Long.valueOf(this.totalCount) }));
/*    */       
/* 60 */       this.sizeProfileWriter.write(System.lineSeparator());
/* 61 */       this.countProfileWriter.write(System.lineSeparator());
/*    */       
/* 63 */       this.sizeProfileWriter.close();
/* 64 */       this.countProfileWriter.close();
/* 65 */     } catch (IOException e) {
/* 66 */       throw new IllegalStateException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void errorListFiles(File file) {
/* 71 */     System.out.println(String.format("Error listing files in directory %s", new Object[] { file.toString() }));
/*    */   }
/*    */ }


/* Location:              C:\Users\Lenovo\IdeaProjects\backup-profiler-bin\backupProfiler.jar!\net\nosocial\backupprofiler\ProfileEventLog.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */