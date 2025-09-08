/*    */ package net.nosocial.backupprofiler.gui;
/*    */ 
/*    */ import net.nosocial.backupprofiler.FileSystemTraverse;
/*    */ 
/*    */ public class Prefix {
/*    */   private final String path;
/*    */   private int lastVisibleIndex;
/*  8 */   private String search = null;
/*    */   
/*    */   public Prefix(String search, String path, int lastVisibleIndex) {
/* 11 */     this.search = search;
/* 12 */     this.path = path;
/* 13 */     this.lastVisibleIndex = lastVisibleIndex;
/*    */   }
/*    */   
/*    */   public Prefix(String path, int lastVisibleIndex) {
/* 17 */     this.path = path;
/* 18 */     this.lastVisibleIndex = lastVisibleIndex;
/*    */   }
/*    */   
/*    */   public String getPath() {
/* 22 */     return this.path;
/*    */   }
/*    */   
/*    */   public String getSearch() {
/* 26 */     return this.search;
/*    */   }
/*    */   
/*    */   public boolean isSearchPrefix() {
/* 30 */     return (this.search != null);
/*    */   }
/*    */   
/*    */   public boolean belongsTo(String profilePath) {
/* 34 */     if (isSearchPrefix()) {
/* 35 */       return profilePath.contains(this.search);
/*    */     }
/* 37 */     return FileSystemTraverse.isInside(this.path, profilePath);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLastVisibleIndex() {
/* 42 */     return this.lastVisibleIndex;
/*    */   }
/*    */ }


/* Location:              C:\Users\Lenovo\IdeaProjects\backup-profiler-bin\backupProfiler.jar!\net\nosocial\backupprofiler\gui\Prefix.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */