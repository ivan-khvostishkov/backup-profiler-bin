/*     */ package net.nosocial.backupprofiler;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileSystemTraverse
/*     */ {
/*     */   private final TraverseEvent eventLog;
/*     */   private final String[] includeList;
/*     */   private final String[] excludeList;
/*  21 */   private List<String> includeFiles = new ArrayList<>();
/*  22 */   private List<String> excludeFiles = new ArrayList<>();
/*  23 */   private File filePrefix = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public FileSystemTraverse(TraverseEvent eventLog, String[] includeList, String[] excludeList) {
/*  28 */     this.eventLog = eventLog;
/*  29 */     this.includeList = includeList;
/*  30 */     this.excludeList = excludeList;
/*     */   }
/*     */   
/*     */   public void start() {
/*  34 */     start(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void start(String prefix) {
/*  39 */     if (prefix != null) {
/*  40 */       File filePrefix = new File(prefix);
/*  41 */       if (!filePrefix.exists()) {
/*  42 */         throw new IllegalArgumentException("Prefix doesn't exist: " + prefix);
/*     */       }
/*  44 */       this.filePrefix = filePrefix;
/*     */     } 
/*     */     
/*     */     try {
/*  48 */       init();
/*  49 */     } catch (IOException e) {
/*  50 */       throw new IllegalStateException(e);
/*     */     } 
/*     */ 
/*     */     
/*  54 */     for (String root : this.includeFiles) {
/*  55 */       File includeFile = (prefix == null) ? new File(root) : new File(this.filePrefix, root);
/*  56 */       if (!includeFile.exists()) {
/*  57 */         throw new IllegalStateException("Starting path doesn't exist: " + includeFile);
/*     */       }
/*  59 */       traverse(includeFile);
/*     */     } 
/*     */     
/*  62 */     this.eventLog.done();
/*     */   }
/*     */   
/*     */   private void init() throws IOException {
/*  66 */     for (String s : this.includeList) {
/*  67 */       List<String> lines = IOUtils.readLines(new FileReader(s));
/*  68 */       this.includeFiles.addAll(lines);
/*     */     } 
/*     */     
/*  71 */     for (String s : this.excludeList) {
/*  72 */       List<String> lines = IOUtils.readLines(new FileReader(s));
/*  73 */       this.excludeFiles.addAll(lines);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void traverse(File file) {
/*  79 */     String formattedFileName = FormatData.formatFileName(file);
/*     */     
/*  81 */     if (isExclude(file.getAbsolutePath())) {
/*  82 */       this.eventLog.skip(file);
/*     */     } else {
/*  84 */       this.eventLog.traverse(file, removePrefix(formattedFileName));
/*     */       
/*  86 */       if (file.isDirectory() && !Files.isSymbolicLink(file.toPath())) {
/*  87 */         File[] files = file.listFiles();
/*  88 */         if (files != null) {
/*  89 */           for (File child : files) {
/*  90 */             traverse(child);
/*     */           }
/*     */         } else {
/*  93 */           this.eventLog.errorListFiles(file);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String removePrefix(String formattedFileName) {
/* 102 */     if (this.filePrefix == null) {
/* 103 */       return formattedFileName;
/*     */     }
/* 105 */     return formattedFileName.substring(this.filePrefix.getAbsolutePath().length());
/*     */   }
/*     */   
/*     */   private boolean isExclude(String root) {
/* 109 */     for (String excludeFile : this.excludeFiles) {
/* 110 */       if (isInside(excludeFile, root)) {
/* 111 */         return true;
/*     */       }
/*     */     } 
/* 114 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isInside(String parent, String child) {
/* 118 */     if (parent.endsWith("/") || parent.endsWith("\\")) {
/* 119 */       return child.startsWith(parent);
/*     */     }
/* 121 */     return parent.equals(child);
/*     */   }
/*     */ }


/* Location:              C:\Users\Lenovo\IdeaProjects\backup-profiler-bin\backupProfiler.jar!\net\nosocial\backupprofiler\FileSystemTraverse.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */