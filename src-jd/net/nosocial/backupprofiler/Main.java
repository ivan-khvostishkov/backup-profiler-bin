/*    */ package net.nosocial.backupprofiler;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import net.nosocial.backupprofiler.gui.InspectProfile;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Main
/*    */ {
/*    */   public static void main(String[] args) {
/* 37 */     if (args.length > 0 && args[0].equals("prepare")) {
/* 38 */       PrepareProfile.main(Arrays.<String>copyOfRange(args, 1, args.length));
/* 39 */     } else if (args.length > 0 && args[0].equals("inspect")) {
/* 40 */       InspectProfile.main(Arrays.<String>copyOfRange(args, 1, args.length));
/*    */     } else {
/* 42 */       System.out.println("Usage: prepare|inspect [options]");
/* 43 */       System.out.println("Prepare options: --prefix (tbd)");
/* 44 */       System.out.println("Inspect options: --prefix, --profile, --incomplete, --limit");
/* 45 */       System.exit(1);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Lenovo\IdeaProjects\backup-profiler-bin\backupProfiler.jar!\net\nosocial\backupprofiler\Main.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */