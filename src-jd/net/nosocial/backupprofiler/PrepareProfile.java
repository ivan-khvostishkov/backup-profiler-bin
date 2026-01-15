/*    */ package net.nosocial.backupprofiler;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrepareProfile
/*    */ {
/*    */   public static void main(String[] args) {
/* 11 */     if (args.length == 0) {
/* 12 */       defaultArgs();
/* 13 */       System.exit(0);
/*    */     } 
/* 15 */     if (args[0].equals("--list-roots")) {
/* 16 */       (new RootsEnumerator(new StdOutRoots())).listRoots();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void defaultArgs() {
/* 31 */     String profileStartName = "profile-start.txt";
/* 32 */     if ((new File(profileStartName)).exists()) {
/* 33 */       if (!(new File("important")).exists() || (new File("unimportant")).exists()) {
/* 34 */         (new File("important", "size")).mkdirs();
/* 35 */         (new File("important", "count")).mkdirs();
/* 36 */         (new File("important", "time")).mkdirs();
/* 37 */         (new File("unimportant", "size")).mkdirs();
/* 38 */         (new File("unimportant", "count")).mkdirs();
/*    */       } 
/*    */     } else {
/* 41 */       throw new IllegalStateException(profileStartName + " not found");
/*    */     } 
/*    */     
/* 44 */     (new FileSystemTraverse(new ProfileEventLog("important"), new String[] { profileStartName }, new String[] { "profile-skip.txt", "profile-unimportant.txt"
/*    */ 
/*    */         
/* 47 */         })).start();
/*    */     
/* 49 */     (new FileSystemTraverse(new ProfileEventLog("unimportant"), new String[] { "profile-unimportant.txt" }, new String[0]))
/*    */       
/* 51 */       .start();
/*    */   }
/*    */ }


/* Location:              C:\Users\Lenovo\IdeaProjects\backup-profiler-bin\backupProfiler.jar!\net\nosocial\backupprofiler\PrepareProfile.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */