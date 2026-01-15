/*    */ package net.nosocial.backupprofiler;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class ProfileObserver
/*    */ {
/*    */   public static final double DEFAULT_FRACTION = 0.95D;
/*    */   private final Iterator<PathTime> iterator;
/*    */   private final TimingProfile profile;
/*    */   
/*    */   public ProfileObserver(TimingProfile profile) {
/* 16 */     this.profile = profile;
/*    */     
/* 18 */     List<PathTime> list = createPaths(profile);
/*    */     
/* 20 */     sortTimes(list);
/*    */     
/* 22 */     this.iterator = list.iterator();
/*    */   }
/*    */   
/*    */   private void sortTimes(List<PathTime> list) {
/* 26 */     list.sort((o1, o2) -> (o1.getTime() != o2.getTime()) ? -Math.round(Math.signum((float)(o1.getTime() - o2.getTime()))) : o1.getPath().compareTo(o2.getPath()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private List<PathTime> createPaths(TimingProfile profile) {
/* 35 */     List<PathTime> list = new ArrayList<>();
/* 36 */     for (Map.Entry<String, Long> entry : profile.getTimeMap().entrySet()) {
/* 37 */       list.add(new PathTime(entry.getKey(), ((Long)entry.getValue()).longValue()));
/*    */     }
/* 39 */     return list;
/*    */   }
/*    */   
/*    */   public PathTime nextPathTime() {
/* 43 */     if (!this.iterator.hasNext()) {
/* 44 */       return null;
/*    */     }
/* 46 */     return this.iterator.next();
/*    */   }
/*    */   
/*    */   public int getPathsCount() {
/* 50 */     return this.profile.getPathsCount();
/*    */   }
/*    */ }


/* Location:              C:\Users\Lenovo\IdeaProjects\backup-profiler-bin\backupProfiler.jar!\net\nosocial\backupprofiler\ProfileObserver.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */