/*     */ package net.nosocial.backupprofiler;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Stack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TimingProfile
/*     */ {
/*     */   private final boolean multiplePaths;
/*     */   private long totalSize;
/*  15 */   private Map<String, Long> timeMap = new HashMap<>();
/*  16 */   private Stack<PathObservation> stack = new Stack<>();
/*     */   
/*  18 */   private long lastTime = 0L;
/*  19 */   private long firstTime = -1L;
/*     */   
/*     */   public TimingProfile(boolean multiplePaths) {
/*  22 */     this.multiplePaths = multiplePaths;
/*     */   }
/*     */   
/*     */   public long getTotalTime(String path) {
/*  26 */     Long result = this.timeMap.get(path);
/*  27 */     if (result == null) {
/*  28 */       throw new IllegalArgumentException("Path not found: " + path);
/*     */     }
/*  30 */     return result.longValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getTotalSize() {
/*  35 */     return this.totalSize;
/*     */   }
/*     */   
/*     */   public void totalSize(long time, long totalSize) {
/*  39 */     this.totalSize = totalSize;
/*  40 */     updateOverallTime(time);
/*     */     
/*  42 */     while (!this.stack.isEmpty()) {
/*  43 */       PathObservation previousPath = this.stack.pop();
/*  44 */       this.timeMap.put(previousPath.getPath(), Long.valueOf(time - previousPath.getTime()));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void observe(long time, String path) {
/*  49 */     if (this.totalSize != 0L) {
/*  50 */       throw new IllegalStateException("Total size already reported");
/*     */     }
/*  52 */     updateOverallTime(time);
/*     */     
/*  54 */     if (this.stack.isEmpty()) {
/*  55 */       this.stack.push(new PathObservation(time, path));
/*  56 */     } else if (!this.multiplePaths) {
/*  57 */       while (!FileSystemTraverse.isInside(((PathObservation)this.stack.peek()).getPath(), path)) {
/*  58 */         PathObservation previousPath = this.stack.pop();
/*  59 */         this.timeMap.put(previousPath.getPath(), Long.valueOf(time - previousPath.getTime()));
/*     */         
/*  61 */         if (this.stack.isEmpty()) {
/*  62 */           throw new IllegalArgumentException("Path is out of root: " + path);
/*     */         }
/*     */       } 
/*  65 */       this.stack.push(new PathObservation(time, path));
/*     */     } else {
/*  67 */       while (!this.stack.isEmpty() && !FileSystemTraverse.isInside(((PathObservation)this.stack.peek()).getPath(), path)) {
/*  68 */         PathObservation previousPath = this.stack.pop();
/*  69 */         this.timeMap.put(previousPath.getPath(), Long.valueOf(time - previousPath.getTime()));
/*     */       } 
/*  71 */       this.stack.push(new PathObservation(time, path));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateOverallTime(long time) {
/*  76 */     if (this.firstTime == -1L) {
/*  77 */       this.firstTime = time;
/*     */     }
/*  79 */     this.lastTime = time;
/*     */   }
/*     */   
/*     */   public Map<String, Long> getTimeMap() {
/*  83 */     return this.timeMap;
/*     */   }
/*     */   
/*     */   public long getOverallTime() {
/*  87 */     return this.lastTime - this.firstTime;
/*     */   }
/*     */   
/*     */   public boolean hasTotalTime(String path) {
/*  91 */     return (this.timeMap.get(path) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPathsCount() {
/*  96 */     return this.timeMap.size();
/*     */   }
/*     */   
/*     */   public boolean containsPath(String path) {
/* 100 */     return this.timeMap.containsKey(path);
/*     */   }
/*     */ }


/* Location:              C:\Users\Lenovo\IdeaProjects\backup-profiler-bin\backupProfiler.jar!\net\nosocial\backupprofiler\TimingProfile.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */