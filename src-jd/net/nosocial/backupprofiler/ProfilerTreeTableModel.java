/*    */ package net.nosocial.backupprofiler;
/*    */ 
/*    */ import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProfilerTreeTableModel
/*    */   extends AbstractTreeTableModel
/*    */ {
/*    */   public int getColumnCount() {
/* 12 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getColumnName(int column) {
/* 17 */     switch (column) {
/*    */       case 0:
/* 19 */         return "Name";
/*    */       case 1:
/* 21 */         return "Size";
/*    */     } 
/* 23 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getValueAt(Object node, int column) {
/* 28 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getChild(Object parent, int index) {
/* 33 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getChildCount(Object parent) {
/* 38 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getIndexOfChild(Object parent, Object child) {
/* 43 */     return -1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Lenovo\IdeaProjects\backup-profiler-bin\backupProfiler.jar!\net\nosocial\backupprofiler\ProfilerTreeTableModel.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */