/*
 * Screen.java
 *
 * Created on Jun 16, 2011, 3:00:08 PM
 */
package com.extrapolo.graphplan.view;

import com.extrapolo.graphplan.control.Planner;
import com.extrapolo.graphplan.control.Util;
import com.extrapolo.graphplan.model.Action;
import com.extrapolo.graphplan.model.State;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author rafaelpolo
 */
public class Screen extends javax.swing.JFrame {

    /** Creates new form Screen */
    public Screen() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextResult = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPlan = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jButtonRun = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jCheckBoxDebugMutex = new javax.swing.JCheckBox();
        jButtonLoad = new javax.swing.JButton();
        jCheckBoxEdit = new javax.swing.JCheckBox();

        jButton1.setText("Go!");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PoloGraphPlan v0.2");

        jTextResult.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        jTextResult.setColumns(20);
        jTextResult.setEditable(false);
        jTextResult.setRows(5);
        jTextResult.setTabSize(4);
        jScrollPane2.setViewportView(jTextResult);

        jTextPlan.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        jTextPlan.setColumns(20);
        jTextPlan.setEditable(false);
        jTextPlan.setRows(5);
        jTextPlan.setTabSize(2);
        jScrollPane3.setViewportView(jTextPlan);

        jLabel2.setText("Plano:");

        jButtonRun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/extrapolo/graphplan/view/img/run.png"))); // NOI18N
        jButtonRun.setText("Run");
        jButtonRun.setEnabled(false);
        jButtonRun.setFocusable(false);
        jButtonRun.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonRun.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunActionPerformed(evt);
            }
        });

        jLabel3.setText("Resultado:");

        jCheckBoxDebugMutex.setSelected(true);
        jCheckBoxDebugMutex.setText("debug mutex");

        jButtonLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/extrapolo/graphplan/view/img/load.png"))); // NOI18N
        jButtonLoad.setText("Load");
        jButtonLoad.setFocusable(false);
        jButtonLoad.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonLoad.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoadActionPerformed(evt);
            }
        });

        jCheckBoxEdit.setText("editável");
        jCheckBoxEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxEditActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(jLabel2)
                        .add(50, 50, 50)
                        .add(jCheckBoxEdit)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 302, Short.MAX_VALUE)
                        .add(jButtonLoad))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(jLabel3)
                        .add(19, 19, 19)
                        .add(jCheckBoxDebugMutex)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 268, Short.MAX_VALUE)
                        .add(jButtonRun, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel2)
                        .add(jCheckBoxEdit))
                    .add(jButtonLoad))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 320, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(5, 5, 5)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE, false)
                            .add(jCheckBoxDebugMutex)
                            .add(layout.createSequentialGroup()
                                .add(7, 7, 7)
                                .add(jLabel3))))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jButtonRun))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Run
    private void jButtonRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunActionPerformed
        jTextResult.setText("");
        Action.reset();
        State.reset();

        // Get current time
        long start = System.currentTimeMillis();

        // go!
        Planner planner = Util.parsePlanFromADLString(jTextPlan.getText());
        if (planner!=null) {
            ArrayList<List<Action>> plan = planner.getPlan();

            if (plan != null) {
                // print plan
                int step = 1;
                for (List<Action> actionsPlan : plan) {
                    log("Passo #" + step);
                    for (Action action : actionsPlan) {
                        log("\t- " + action.getId());
                    }
                    step++;
                }
            } else {
                log("Sem plano.");
            }

            // Get elapsed time
            long elapsedTimeMillis = System.currentTimeMillis() - start;
            float elapsedTimeSec = elapsedTimeMillis / 1000F;
            log("\nsegundos: " + elapsedTimeSec);
        }
}//GEN-LAST:event_jButtonRunActionPerformed

    // carrega arquivo .adl
    private void jButtonLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoadActionPerformed
        class ADLFileFilter extends javax.swing.filechooser.FileFilter {

            @Override
            public boolean accept(File file) {
                String filename = file.getName();
                return filename.endsWith(".adl");
            }

            @Override
            public String getDescription() {
                return "*.adl";
            }
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new ADLFileFilter());
        fileChooser.showOpenDialog(this);
        try {
            jTextPlan.setText(Util.readFile(fileChooser.getSelectedFile()));
            jButtonRun.setEnabled(true);
        } catch (IOException ex) {
            Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonLoadActionPerformed

    // definir plano editável
    private void jCheckBoxEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxEditActionPerformed
        if (jTextPlan.isEditable()) {
            jTextPlan.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        } else {
            jTextPlan.setBackground(Color.WHITE);
        }
        jTextPlan.setEditable(!jTextPlan.isEditable());
    }//GEN-LAST:event_jCheckBoxEditActionPerformed

    public static void log(String txt) {
        jTextResult.append(txt + "\n");
    }

    public static void logMutex(String txt) {
        if (jCheckBoxDebugMutex.isSelected()) {
            log(txt);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Screen().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonLoad;
    private javax.swing.JButton jButtonRun;
    private static javax.swing.JCheckBox jCheckBoxDebugMutex;
    private javax.swing.JCheckBox jCheckBoxEdit;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextPlan;
    private static javax.swing.JTextArea jTextResult;
    // End of variables declaration//GEN-END:variables
}
