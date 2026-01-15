/*    */ package net.nosocial.backupprofiler;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProfileReader
/*    */ {
/*    */   private boolean complete = true;
/*    */   private boolean multiplePaths;
/*    */   
/*    */   public ProfileReader(boolean complete) {
/* 17 */     this.complete = complete;
/*    */   }
/*    */   
/*    */   public ProfileReader() {
/* 21 */     this(true);
/*    */   }
/*    */   
/*    */   public TimingProfile read(InputStream input) throws IOException {
/* 25 */     BufferedReader reader = new BufferedReader(new InputStreamReader(input));
/*    */     
/* 27 */     TimingProfile result = new TimingProfile(this.multiplePaths);
/* 28 */     Long time = null;
/* 29 */     String path = null;
/* 30 */     String line = reader.readLine();
/* 31 */     while (line != null) {
/* 32 */       String nextLine = reader.readLine();
/* 33 */       if (!this.complete && nextLine == null) {
/*    */         break;
/*    */       }
/*    */       
/* 37 */       if (path != null && !isPath(path)) {
/* 38 */         throw new IllegalArgumentException("Invalid format, expected path: " + path);
/*    */       }
/* 40 */       String[] arg = line.split("[ \t]+", 2);
/* 41 */       if (arg.length != 2) {
/* 42 */         throw new IllegalArgumentException("Invalid format, expected time and path: " + line);
/*    */       }
/* 44 */       time = Long.valueOf(arg[0]);
/* 45 */       path = arg[1];
/*    */       
/* 47 */       line = nextLine;
/*    */       
/* 49 */       if (path.startsWith("/bin/tar: ")) {
/*    */         continue;
/*    */       }
/*    */ 
/*    */ 
/*    */       
/* 55 */       if (isPath(path)) {
/* 56 */         result.observe(time.longValue(), path);
/*    */       }
/*    */     } 
/*    */     
/* 60 */     if (this.complete) {
/* 61 */       assert path != null;
/* 62 */       if (isPath(path)) {
/* 63 */         throw new IllegalStateException("Last line is path, not a total size, profile is incomplete? Try with --incomplete .");
/*    */       }
/* 65 */       Long totalSize = Long.valueOf(path);
/* 66 */       result.totalSize(time.longValue(), totalSize.longValue());
/*    */     } 
/*    */     
/* 69 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean isPath(String path) {
/* 74 */     return (path.startsWith("/") || path.startsWith("C:\\"));
/*    */   }
/*    */   
/*    */   public ProfileReader multiplePaths() {
/* 78 */     this.multiplePaths = true;
/* 79 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Lenovo\IdeaProjects\backup-profiler-bin\backupProfiler.jar!\net\nosocial\backupprofiler\ProfileReader.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */