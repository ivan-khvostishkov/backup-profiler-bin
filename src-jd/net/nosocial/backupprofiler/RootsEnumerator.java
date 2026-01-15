/*    */ package net.nosocial.backupprofiler;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RootsEnumerator
/*    */ {
/*    */   private final StdOutRoots callback;
/*    */   
/*    */   public RootsEnumerator(StdOutRoots stdOutRoots) {
/* 14 */     this.callback = stdOutRoots;
/*    */   }
/*    */   
/*    */   public void listRoots() {
/* 18 */     for (File root : File.listRoots())
/* 19 */       this.callback.root(root); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Lenovo\IdeaProjects\backup-profiler-bin\backupProfiler.jar!\net\nosocial\backupprofiler\RootsEnumerator.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */