/*    */ package net.nosocial.backupprofiler;
/*    */ import java.awt.BorderLayout;
/*    */ import java.awt.Component;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JScrollPane;
/*    */ import javax.swing.JSplitPane;
/*    */ import javax.swing.SwingUtilities;
/*    */ import org.jdesktop.swingx.JXTreeTable;
/*    */ import org.jdesktop.swingx.treetable.FileSystemModel;
/*    */ import org.jdesktop.swingx.treetable.TreeTableModel;
/*    */ 
/*    */ public class LaunchGui {
/*    */   public static void main(String[] args) {
/* 17 */     SwingUtilities.invokeLater(new Runnable() {
/*    */           public void run() {
/* 19 */             LaunchGui.createAndShowGUI();
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   private static void createAndShowGUI() {
/* 25 */     profilerFrame();
/* 26 */     newProjectFrame();
/*    */   }
/*    */ 
/*    */   
/*    */   private static void newProjectFrame() {
/* 31 */     JFrame frame = new JFrame("New Configuration - Backup Profiler");
/* 32 */     frame.setDefaultCloseOperation(3);
/*    */     
/* 34 */     JPanel includePanel = new JPanel();
/* 35 */     includePanel.setBorder(BorderFactory.createTitledBorder("Include directories"));
/* 36 */     JPanel excludePanel = new JPanel();
/* 37 */     excludePanel.setBorder(BorderFactory.createTitledBorder("Exclude directories"));
/*    */     
/* 39 */     JSplitPane includeExclude = new JSplitPane(0, includePanel, excludePanel);
/*    */     
/* 41 */     JPanel fileTreePanel = new JPanel();
/* 42 */     JSplitPane treeWithLists = new JSplitPane(1, fileTreePanel, includeExclude);
/*    */     
/* 44 */     JPanel configurationPanel = new JPanel();
/* 45 */     configurationPanel.setBorder(BorderFactory.createEtchedBorder());
/* 46 */     configurationPanel.setLayout(new BorderLayout());
/* 47 */     configurationPanel.add(treeWithLists);
/*    */     
/* 49 */     frame.getContentPane().add(new JLabel("Configuration name:"));
/* 50 */     frame.getContentPane().add(new JLabel("Configuration directory:"));
/* 51 */     frame.getContentPane().add(configurationPanel);
/*    */     
/* 53 */     frame.pack();
/* 54 */     frame.setVisible(true);
/*    */   }
/*    */   
/*    */   private static void profilerFrame() {
/* 58 */     JFrame frame = new JFrame("spacecraft - [/home/root/backup/spacecraft] - Backup Profiler");
/* 59 */     frame.setDefaultCloseOperation(3);
/*    */     
/* 61 */     FileSystemModel fileSystemModel = new FileSystemModel();
/* 62 */     JXTreeTable treeTable = new JXTreeTable((TreeTableModel)new ProfilerTreeTableModel());
/* 63 */     JScrollPane scrollPane = new JScrollPane((Component)treeTable);
/*    */     
/* 65 */     frame.getContentPane().add(scrollPane);
/*    */     
/* 67 */     frame.pack();
/* 68 */     frame.setVisible(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\Lenovo\IdeaProjects\backup-profiler-bin\backupProfiler.jar!\net\nosocial\backupprofiler\LaunchGui.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */