/*     */ package net.nosocial.backupprofiler.gui;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.swing.DefaultListModel;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import net.nosocial.backupprofiler.FileSystemTraverse;
/*     */ import net.nosocial.backupprofiler.FormatData;
/*     */ import net.nosocial.backupprofiler.PathTime;
/*     */ import net.nosocial.backupprofiler.ProfileObserver;
/*     */ 
/*     */ public class InspectProfile implements WindowListener, KeyListener {
/*  23 */   private static volatile int displayLimit = 1000;
/*     */   public static final String PROFILE_LOG = "profile.log";
/*     */   public static final String PROFILE_ACKNOWLEDGE = "profile-ack.txt";
/*     */   public static volatile boolean profileComplete = true;
/*  27 */   public static final boolean IS_UNIX = (File.separatorChar == '/');
/*     */   
/*     */   private JPanel panel1;
/*     */   
/*     */   private JList list1;
/*     */   
/*     */   private JLabel label1;
/*     */   
/*     */   TimingProfile profile;
/*     */   
/*     */   private PathTime[] data;
/*     */   private DefaultListModel<PathTime> dataModel;
/*     */   private Stack<Prefix> prefixHistory;
/*     */   private Font unreadFont;
/*     */   private Font readFont;
/*     */   private Set<String> acknowledgedPaths;
/*  43 */   private static String prefix = null;
/*  44 */   public static String profileDir = null; private static JFrame frame; public InspectProfile() { $$$setupUI$$$();
/*     */     this.unreadFont = new Font("Monospaced", 1, 12);
/*     */     this.readFont = new Font("Monospaced", 0, 12);
/*  47 */     System.out.printf("%s%s%n", new Object[] { "Inspecting profile", profileComplete ? "" : " (incomplete)" });
/*  48 */     this.list1.addKeyListener(new MyKeyAdapter()); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/*  54 */     System.out.println("Starting profile inspector");
/*  55 */     System.out.println("Supported keys: t (toggle), <enter>, <backspace>, c (copy clipboard), s (search), n (next), d (dump graph)");
/*     */ 
/*     */     
/*  58 */     if (args.length > 0 && args[0].equals("--prefix")) {
/*  59 */       prefix = args[1];
/*  60 */       System.out.println("Profile prefix is " + prefix);
/*  61 */       args = Arrays.<String>copyOfRange(args, 2, args.length);
/*     */     } 
/*     */     
/*  64 */     if (args.length > 0 && args[0].equals("--profile")) {
/*  65 */       profileDir = args[1];
/*  66 */       System.out.println("Starting in " + profileDir);
/*  67 */       args = Arrays.<String>copyOfRange(args, 2, args.length);
/*     */     } 
/*     */     
/*  70 */     if (args.length > 0 && args[0].equals("--incomplete")) {
/*  71 */       profileComplete = false;
/*  72 */       args = Arrays.<String>copyOfRange(args, 1, args.length);
/*     */     } 
/*     */     
/*  75 */     if (args.length > 0 && args[0].equals("--limit")) {
/*  76 */       System.out.println("Overriding default display limit");
/*  77 */       displayLimit = Integer.parseInt(args[1]);
/*     */     } 
/*     */     
/*  80 */     System.out.println("Display limit is " + displayLimit);
/*     */     
/*  82 */     frame = new JFrame("InspectProfile");
/*  83 */     InspectProfile inspectProfile = new InspectProfile();
/*  84 */     inspectProfile.updateLabel();
/*  85 */     inspectProfile.updateTitle();
/*     */     
/*  87 */     frame.addWindowListener(inspectProfile);
/*     */     
/*  89 */     frame.setContentPane(inspectProfile.panel1);
/*  90 */     frame.setDefaultCloseOperation(3);
/*  91 */     frame.pack();
/*  92 */     frame.setVisible(true);
/*     */   }
/*     */   
/*     */   private class MyKeyAdapter
/*     */     extends KeyAdapter {
/*     */     public void keyTyped(KeyEvent e) {
/*  98 */       super.keyTyped(e);
/*     */     }
/*     */ 
/*     */     
/*     */     public void keyPressed(KeyEvent e) {
/* 103 */       if (e.getKeyChar() == 't') {
/* 104 */         int[] indices = ((JList)e.getComponent()).getSelectedIndices();
/* 105 */         for (int index : indices) {
/* 106 */           InspectProfile.this.acknowledgePath(index);
/*     */         }
/* 108 */         InspectProfile.this.list1.repaint();
/* 109 */         InspectProfile.this.updateLabel();
/* 110 */       } else if (e.getKeyChar() == '\n') {
/* 111 */         int index = ((JList)e.getComponent()).getSelectedIndex();
/* 112 */         int visibleIndex = ((JList)e.getComponent()).getLastVisibleIndex();
/*     */         
/* 114 */         if (index >= 0) {
/* 115 */           InspectProfile.this.prefixHistory.push(new Prefix(((PathTime)InspectProfile.this.dataModel.getElementAt(index)).getPath(), visibleIndex));
/* 116 */           InspectProfile.this.initDataModel();
/* 117 */           InspectProfile.this.list1.setModel(InspectProfile.this.dataModel);
/* 118 */           ((JList)e.getComponent()).setSelectedIndex(0);
/* 119 */           ((JList)e.getComponent()).ensureIndexIsVisible(0);
/*     */         } 
/* 121 */         InspectProfile.this.list1.repaint();
/* 122 */         InspectProfile.this.updateLabel();
/* 123 */         InspectProfile.this.updateTitle();
/* 124 */       } else if (e.getKeyChar() == '\b') {
/* 125 */         if (InspectProfile.this.prefixHistory.size() > 1) {
/* 126 */           Prefix prefix = InspectProfile.this.prefixHistory.pop();
/* 127 */           String selectedPath = prefix.getPath();
/* 128 */           InspectProfile.this.initDataModel();
/* 129 */           InspectProfile.this.list1.setModel(InspectProfile.this.dataModel);
/* 130 */           int newIndex = InspectProfile.this.findDataModelIndex(selectedPath);
/* 131 */           ((JList)e.getComponent()).setSelectedIndex(newIndex);
/* 132 */           ((JList)e.getComponent()).ensureIndexIsVisible(prefix.getLastVisibleIndex());
/*     */         } 
/* 134 */         InspectProfile.this.list1.repaint();
/* 135 */         InspectProfile.this.updateLabel();
/* 136 */         InspectProfile.this.updateTitle();
/* 137 */       } else if (e.getKeyChar() == 's') {
/*     */         
/* 139 */         String search = JOptionPane.showInputDialog("What to search for?");
/* 140 */         int index = ((JList)e.getComponent()).getSelectedIndex();
/* 141 */         int visibleIndex = ((JList)e.getComponent()).getLastVisibleIndex();
/* 142 */         InspectProfile.this.prefixHistory.push(new Prefix(search, ((PathTime)InspectProfile.this.dataModel.getElementAt(index)).getPath(), visibleIndex));
/* 143 */         InspectProfile.this.initDataModel();
/* 144 */         InspectProfile.this.list1.setModel(InspectProfile.this.dataModel);
/* 145 */         ((JList)e.getComponent()).setSelectedIndex(0);
/* 146 */         ((JList)e.getComponent()).ensureIndexIsVisible(0);
/* 147 */         InspectProfile.this.list1.repaint();
/* 148 */         InspectProfile.this.updateLabel();
/* 149 */         InspectProfile.this.updateTitle();
/* 150 */       } else if (e.getKeyChar() == 'n') {
/*     */         
/* 152 */         int index = ((JList)e.getComponent()).getSelectedIndex();
/* 153 */         for (int i = index + 1; i < InspectProfile.this.dataModel.getSize(); i++) {
/* 154 */           String itemPath = ((PathTime)InspectProfile.this.dataModel.getElementAt(i)).getPath();
/* 155 */           if (!InspectProfile.this.acknowledgedPaths.contains(itemPath) && !InspectProfile.this.acknowledgedPathsHasPrefix(itemPath)) {
/* 156 */             ((JList)e.getComponent()).setSelectedIndex(i);
/* 157 */             ((JList)e.getComponent()).ensureIndexIsVisible(i);
/* 158 */             InspectProfile.this.list1.repaint();
/*     */             break;
/*     */           } 
/*     */         } 
/* 162 */       } else if (e.getKeyChar() == 'c') {
/*     */         
/* 164 */         int index = ((JList)e.getComponent()).getSelectedIndex();
/* 165 */         String path = ((PathTime)InspectProfile.this.dataModel.getElementAt(index)).getPath();
/*     */         
/* 167 */         StringSelection stringSelection = new StringSelection(path);
/* 168 */         Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
/* 169 */         clip.setContents(stringSelection, null);
/*     */         
/* 171 */         System.out.println("Copy to clipboard: " + path);
/* 172 */       } else if (e.getKeyChar() == 'd') {
/*     */         
/* 174 */         String dumpPath = "dump.gexf";
/* 175 */         System.out.println("Dumping graph to: " + dumpPath);
/* 176 */         InspectProfile.this.dumpGraph(dumpPath);
/*     */       } else {
/* 178 */         super.keyPressed(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void keyReleased(KeyEvent e) {
/* 185 */       super.keyReleased(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private int findDataModelIndex(String selectedPrefix) {
/* 191 */     for (int i = 0; i < this.dataModel.size(); i++) {
/* 192 */       if (((PathTime)this.dataModel.getElementAt(i)).getPath().equals(selectedPrefix)) {
/* 193 */         return i;
/*     */       }
/*     */     } 
/* 196 */     return -1;
/*     */   }
/*     */   
/*     */   private void acknowledgePath(int index) {
/* 200 */     PathTime selectedPath = this.dataModel.getElementAt(index);
/* 201 */     System.out.println("Toggle: " + selectedPath);
/*     */     
/* 203 */     boolean found = false;
/* 204 */     for (String acknowledgedPath : new HashSet(this.acknowledgedPaths)) {
/* 205 */       if (FileSystemTraverse.isInside(selectedPath.getPath(), acknowledgedPath)) {
/* 206 */         this.acknowledgedPaths.remove(acknowledgedPath);
/* 207 */         System.out.println("Remove: " + selectedPath);
/* 208 */         if (acknowledgedPath.equals(selectedPath.getPath()))
/* 209 */           found = true;  continue;
/*     */       } 
/* 211 */       if (FileSystemTraverse.isInside(acknowledgedPath, selectedPath.getPath())) {
/* 212 */         System.out.println("Unable to toggle child");
/* 213 */         found = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 217 */     if (!found) {
/* 218 */       System.out.println("Add: " + selectedPath);
/* 219 */       this.acknowledgedPaths.add(selectedPath.getPath());
/*     */     } 
/*     */     
/* 222 */     recalculateDataPercentage();
/*     */   }
/*     */   
/*     */   private void recalculateDataPercentage() {
/* 226 */     for (PathTime pathTime : this.data) {
/* 227 */       pathTime.initAcknowledgedTime();
/*     */     }
/*     */     
/* 230 */     for (String acknowledgedPath : this.acknowledgedPaths) {
/* 231 */       if (!this.profile.hasTotalTime(acknowledgedPath)) {
/*     */         continue;
/*     */       }
/* 234 */       for (PathTime pathTime : this.data) {
/* 235 */         if (FileSystemTraverse.isInside(pathTime.getPath(), acknowledgedPath)) {
/* 236 */           pathTime.addAcknowledgedTime(this.profile.getTotalTime(acknowledgedPath));
/*     */         }
/* 238 */         if (pathTime.getPath().equals(acknowledgedPath)) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateLabel() {
/* 246 */     this.label1.setText(formatAcknowledgedPercentage());
/*     */   }
/*     */   
/*     */   private void updateTitle() {
/* 250 */     Prefix prefix = this.prefixHistory.peek();
/* 251 */     frame.setTitle(prefix.isSearchPrefix() ? ("Search: " + prefix.getSearch()) : prefix.getPath());
/*     */   }
/*     */   
/*     */   public PathTime[] loadData() throws IOException {
/* 255 */     InputStream input = new FileInputStream(profileLog());
/* 256 */     this.profile = (new ProfileReader(profileComplete)).multiplePaths().read(input);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 262 */     System.out.println("Total: " + this.profile.getOverallTime());
/* 263 */     System.out.println("Total (human): " + FormatData.humanReadableByteCount(this.profile.getTotalSize()));
/*     */     
/* 265 */     ProfileObserver observer = new ProfileObserver(this.profile);
/* 266 */     int count = observer.getPathsCount();
/* 267 */     PathTime[] result = new PathTime[count];
/* 268 */     for (int i = 0; i < count; i++) {
/* 269 */       result[i] = observer.nextPathTime();
/*     */     }
/*     */     
/* 272 */     return result;
/*     */   }
/*     */   
/*     */   private File profileLog() {
/* 276 */     return getProfileFile("profile.log");
/*     */   }
/*     */   
/*     */   private File profileAck() {
/* 280 */     return getProfileFile("profile-ack.txt");
/*     */   }
/*     */   
/*     */   private File getProfileFile(String fileName) {
/* 284 */     return (profileDir == null) ? new File(fileName) : new File(profileDir, fileName);
/*     */   }
/*     */   
/*     */   private File getPrefixedFile(String fileName) {
/* 288 */     return (prefix == null) ? new File(fileName) : new File(prefix, fileName);
/*     */   }
/*     */   
/*     */   private void saveAcknowledgeData() throws IOException {
/* 292 */     BufferedWriter writer = new BufferedWriter(new FileWriter(profileAck())); 
/* 293 */     try { for (String acknowledgedPath : this.acknowledgedPaths) {
/* 294 */         writer.write(acknowledgedPath);
/* 295 */         writer.newLine();
/* 296 */         File prefixedAck = getPrefixedFile(acknowledgedPath);
/* 297 */         if (!prefixedAck.exists()) {
/* 298 */           removedAckPathWarning(prefixedAck);
/*     */         }
/*     */       } 
/* 301 */       writer.close(); }
/*     */     catch (Throwable throwable) { try { writer.close(); }
/*     */       catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 305 */      } private void removedAckPathWarning(File acknowledgedPath) { System.out.println("Warning, removed (?) path: " + acknowledgedPath); }
/*     */ 
/*     */   
/*     */   private Set<String> loadAcknowledgeData() throws IOException {
/* 309 */     Set<String> result = new HashSet<>();
/*     */     
/* 311 */     File ackFile = profileAck();
/*     */     
/* 313 */     if (!ackFile.isFile() || !ackFile.exists()) {
/* 314 */       return result;
/*     */     }
/*     */     
/* 317 */     BufferedReader reader = new BufferedReader(new FileReader(ackFile)); 
/* 318 */     try { for (String acknowledgedPath = reader.readLine(); acknowledgedPath != null; 
/* 319 */         acknowledgedPath = reader.readLine()) {
/*     */         
/* 321 */         result.add(acknowledgedPath);
/*     */         
/* 323 */         File prefixedAck = getPrefixedFile(acknowledgedPath);
/* 324 */         if (!prefixedAck.exists()) {
/* 325 */           removedAckPathWarning(prefixedAck);
/* 326 */         } else if (!this.profile.containsPath(acknowledgedPath)) {
/* 327 */           System.out.println("Warning, exist on disk but not exists in profile (profile incremental or incomplete?): " + acknowledgedPath);
/*     */         } 
/*     */       } 
/*     */       
/* 331 */       reader.close(); } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 333 */      return result;
/*     */   }
/*     */   
/*     */   private void createUIComponents() throws IOException {
/* 337 */     this.data = loadData();
/* 338 */     this.prefixHistory = new Stack<>();
/*     */ 
/*     */     
/* 341 */     if (IS_UNIX) {
/* 342 */       this.prefixHistory.push(new Prefix("/", 0));
/*     */     } else {
/* 344 */       this.prefixHistory.push(new Prefix("C:\\", 0));
/*     */     } 
/*     */     
/* 347 */     initDataModel();
/* 348 */     this.acknowledgedPaths = loadAcknowledgeData();
/* 349 */     recalculateDataPercentage();
/* 350 */     this.list1 = new JList<>(this.dataModel);
/* 351 */     this.list1.setCellRenderer((ListCellRenderer)new TogglePathRenderer());
/* 352 */     this.label1 = new JLabel();
/*     */   }
/*     */   
/*     */   private void initDataModel() {
/* 356 */     this.dataModel = new DefaultListModel<>();
/* 357 */     fillDataModel(this.data, this.dataModel, this.prefixHistory.peek());
/*     */   }
/*     */   
/*     */   private void fillDataModel(PathTime[] data, DefaultListModel<PathTime> dataModel, Prefix prefix) {
/* 361 */     dataModel.clear();
/* 362 */     List<PathTime> visiblePaths = new ArrayList<>();
/* 363 */     for (PathTime pathTime : data) {
/* 364 */       if (prefix.belongsTo(pathTime.getPath())) {
/* 365 */         visiblePaths.add(pathTime);
/*     */       }
/* 367 */       if (visiblePaths.size() > displayLimit) {
/*     */         break;
/*     */       }
/*     */     } 
/* 371 */     dataModel.addAll(visiblePaths);
/*     */   }
/*     */   
/*     */   private String formatAcknowledgedPercentage() {
/* 375 */     long acknowledgedSum = getAcknowledgedSum();
/* 376 */     long overallTime = this.profile.getOverallTime();
/* 377 */     double percent = acknowledgedSum * 100.0D / overallTime;
/*     */     
/* 379 */     long remaining90 = (long)(overallTime * 0.9D - acknowledgedSum);
/* 380 */     if (remaining90 < 0L)
/* 381 */       remaining90 = 0L; 
/* 382 */     long remaining95 = (long)(overallTime * 0.95D - acknowledgedSum);
/* 383 */     if (remaining95 < 0L)
/* 384 */       remaining95 = 0L; 
/* 385 */     long remaining99 = (long)(overallTime * 0.99D - acknowledgedSum);
/* 386 */     if (remaining99 < 0L) {
/* 387 */       remaining99 = 0L;
/*     */     }
/* 389 */     return String.format("Acknowledged: %s / %s (%.0f%%)  Remaining for target 90%%: %s, 95%%: %s, 99%%: %s", new Object[] {
/* 390 */           FormatData.humanReadableByteCount(acknowledgedSum), 
/* 391 */           FormatData.humanReadableByteCount(overallTime), Double.valueOf(percent), 
/* 392 */           FormatData.humanReadableByteCount(remaining90), 
/* 393 */           FormatData.humanReadableByteCount(remaining95), 
/* 394 */           FormatData.humanReadableByteCount(remaining99) });
/*     */   }
/*     */   
/*     */   private long getAcknowledgedSum() {
/* 398 */     long sum = 0L;
/* 399 */     for (String acknowledgedPath : this.acknowledgedPaths) {
/* 400 */       if (this.profile.hasTotalTime(acknowledgedPath)) {
/* 401 */         sum += this.profile.getTotalTime(acknowledgedPath);
/*     */       }
/*     */     } 
/* 404 */     return sum;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void windowOpened(WindowEvent e) {}
/*     */ 
/*     */   
/*     */   public void windowClosing(WindowEvent e) {
/*     */     try {
/* 414 */       saveAcknowledgeData();
/* 415 */     } catch (IOException e1) {
/* 416 */       throw new IllegalStateException(e1);
/*     */     } 
/* 418 */     System.out.println(formatAcknowledgedPercentage());
/* 419 */     System.out.println("Saving done.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void windowClosed(WindowEvent e) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void windowIconified(WindowEvent e) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void windowDeiconified(WindowEvent e) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void windowActivated(WindowEvent e) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void windowDeactivated(WindowEvent e) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyTyped(KeyEvent e) {}
/*     */ 
/*     */   
/*     */   public void keyPressed(KeyEvent e) {}
/*     */ 
/*     */   
/*     */   public void keyReleased(KeyEvent e) {}
/*     */ 
/*     */   
/*     */   private class TogglePathRenderer
/*     */     extends DefaultListRenderer
/*     */   {
/*     */     public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
/* 457 */       Component result = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
/* 458 */       String itemPath = ((PathTime)InspectProfile.this.dataModel.getElementAt(index)).getPath();
/* 459 */       if (InspectProfile.this.acknowledgedPaths.contains(itemPath)) {
/* 460 */         result.setFont(InspectProfile.this.readFont);
/* 461 */         result.setForeground(Color.BLACK);
/* 462 */       } else if (InspectProfile.this.acknowledgedPathsHasPrefix(itemPath)) {
/* 463 */         result.setFont(InspectProfile.this.readFont);
/* 464 */         result.setForeground(Color.GRAY);
/*     */       } else {
/* 466 */         result.setFont(InspectProfile.this.unreadFont);
/* 467 */         result.setForeground(Color.BLACK);
/*     */       } 
/* 469 */       return result;
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean acknowledgedPathsHasPrefix(String itemPath) {
/* 474 */     for (String acknowledgedPath : this.acknowledgedPaths) {
/* 475 */       if (FileSystemTraverse.isInside(acknowledgedPath, itemPath)) {
/* 476 */         return true;
/*     */       }
/*     */     } 
/* 479 */     return false;
/*     */   }
/*     */   private void dumpGraph(String dumpPath) {
/*     */     
/* 483 */     try { BufferedWriter writer = new BufferedWriter(new FileWriter(dumpPath)); 
/* 484 */       try { writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<gexf xmlns=\"http://www.gexf.net/1.2draft\" xmlns:viz=\"http://www.gexf.net/1.1draft/viz\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.gexf.net/1.2draft http://www.gexf.net/1.2draft/gexf.xsd\" version=\"1.2\">\n\t<meta lastmodifieddate=\"2009-03-20\">\n\t\t<creator>backupProfiler</creator>\n\t</meta>\n\t<graph defaultedgetype=\"directed\">\n\t\t<nodes>\n");
/*     */ 
/*     */ 
/*     */         
/*     */         int i;
/*     */ 
/*     */         
/* 491 */         for (i = 0; i < this.dataModel.size(); i++) {
/* 492 */           PathTime path = this.dataModel.get(i);
/* 493 */           writer.write(String.format("\t\t\t<node id=\"%s\" label=\"%s\">\n\t\t\t\t<viz:size value=\"%d\"/>\n\t\t\t\t<viz:color r=\"0\" g=\"0\" b=\"119\" />\n\t\t\t</node>\n", new Object[] {
/*     */ 
/*     */ 
/*     */                   
/* 497 */                   cleanStr(path.getPathName()), cleanStr(path.getFileName()), 
/* 498 */                   Long.valueOf(path.getTime() / 1024L / 1024L) }));
/*     */         } 
/* 500 */         writer.write("\t\t</nodes>");
/*     */         
/* 502 */         writer.write("\t\t<edges>\n");
/* 503 */         for (i = 0; i < this.dataModel.size(); i++) {
/* 504 */           PathTime path = this.dataModel.get(i);
/* 505 */           writer.write(String.format("\t\t\t<edge id=\"%d\" source=\"%s\" target=\"%s\"/>\n", new Object[] {
/* 506 */                   Integer.valueOf(i), cleanStr(path.getPathName()), cleanStr(path.getParentName()) }));
/*     */         } 
/* 508 */         writer.write("\t\t</edges>\n\t</graph>\n</gexf>\n");
/*     */ 
/*     */         
/* 511 */         writer.close(); } catch (Throwable throwable) { try { writer.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException e)
/* 512 */     { throw new IllegalStateException(e); }
/*     */   
/*     */   }
/*     */   
/*     */   private String cleanStr(String str) {
/* 517 */     return str.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}/\\\\:_.-]", "");
/*     */   }
/*     */ }


/* Location:              C:\Users\Lenovo\IdeaProjects\backup-profiler-bin\backupProfiler.jar!\net\nosocial\backupprofiler\gui\InspectProfile.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */