/*
 * Screen.java
 *
 * Autor: Rafael Polo
 * Date: Julho 2011
 */
package com.extrapolo.graphplan.view;

import com.extrapolo.graphplan.control.Planner;
import com.extrapolo.graphplan.model.Action;
import com.extrapolo.graphplan.model.State;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Screen extends javax.swing.JFrame {

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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextInicialStates = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextResult = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextOperators = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButtonRun = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jCheckBoxDebugMutex = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextFinalStates = new javax.swing.JTextArea();

        jButton1.setText("Go!");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PoloGraphPlan v0.1");

        jTextInicialStates.setColumns(15);
        jTextInicialStates.setRows(2);
        jTextInicialStates.setText("cozinhaSuja, \nmaoLimpa, \nsilencio");
        jScrollPane1.setViewportView(jTextInicialStates);

        jTextResult.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        jTextResult.setColumns(20);
        jTextResult.setEditable(false);
        jTextResult.setRows(5);
        jTextResult.setTabSize(4);
        jScrollPane2.setViewportView(jTextResult);

        jTextOperators.setColumns(20);
        jTextOperators.setRows(5);
        jTextOperators.setTabSize(2);
        jTextOperators.setText("cozinhar(){\n\t:precondition: maoLimpa.\n\t:effect: janta.\n}\n\nembrulhar(){\n\t:precondition: silencio.\n\t:effect: presente.\n}\n\ncarry(){\n\t:effect: !cozinhaSuja, !maoLimpa.\n}\n\ndolly(){\n\t:effect: !cozinhaSuja, !silencio.\n}");
        jScrollPane3.setViewportView(jTextOperators);

        jLabel1.setText("Estado Inicial:");

        jLabel2.setText("Operadores:");

        jButtonRun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/extrapolo/graphplan/view/run.png"))); // NOI18N
        jButtonRun.setText("Run");
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

        jLabel4.setText("Estado Final:");

        jTextFinalStates.setColumns(15);
        jTextFinalStates.setRows(2);
        jTextFinalStates.setText("!cozinhaSuja,\npresente,\njanta");
        jScrollPane4.setViewportView(jTextFinalStates);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel2)
                            .add(layout.createSequentialGroup()
                                .add(jLabel3)
                                .add(19, 19, 19)
                                .add(jCheckBoxDebugMutex))
                            .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 288, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(jLabel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(215, 215, 215))
                            .add(jLabel1)
                            .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jButtonRun))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(layout.createSequentialGroup()
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                        .add(10, 10, 10)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jCheckBoxDebugMutex)
                            .add(jLabel3)))
                    .add(layout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 129, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(14, 14, 14)
                        .add(jLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButtonRun)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 281, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunActionPerformed
        jTextResult.setText("");
        Action.reset();
        State.reset();

        // Get current time
        long start = System.currentTimeMillis();

        // converte string em ações e suas propriedades
        String operators = clean(jTextOperators.getText());
        Pattern ops = Pattern.compile("(\\w*\\(\\))(\\{.*?\\})");
        Matcher matcher = ops.matcher(operators);
        while (matcher.find()) {
            // cria ação
            String actionName = matcher.group(1).replace("()", "");
            Action action = new Action(actionName);
            // define propriedades
            String tmp = matcher.group(2);
            Pattern opts = Pattern.compile(":(\\w*): (.*?)\\.");
            Matcher optsMatcher = opts.matcher(tmp);
            while (optsMatcher.find()) {
                String which = optsMatcher.group(1);
                String[] properties = optsMatcher.group(2).split(", ");
                if (which.equals("effect")) {
                    ArrayList efeitos = new ArrayList<State>();
                    for (String prop : properties) {
                        efeitos.add(State.addOrCreate(prop));
                    }
                    action.setEffects(efeitos);
                } else if (which.equals("precondition")) {
                    ArrayList precondicoes = new ArrayList<State>();
                    for (String prop : properties) {
                        precondicoes.add(State.addOrCreate(prop));
                    }
                    action.setPrecondition(precondicoes);
                }
            }
        }

        // define estados iniciais
        ArrayList<State> inicialStates = new ArrayList<State>();
        for (String inicialState : clean(jTextInicialStates.getText()).split(", ")) {
            if (!inicialState.isEmpty()){
                inicialStates.add(State.addOrCreate(inicialState));
            }
        }

        // define estados finais
        ArrayList<State> finalStates = new ArrayList<State>();
        for (String finalState : clean(jTextFinalStates.getText()).split(", ")) {
            finalStates.add(State.addOrCreate(finalState));
        }

        // go!
        Planner planner = new Planner(inicialStates, finalStates);
        ArrayList<List<Action>> plan = planner.getPlan();

        if (plan != null) {
            // print plan
            int step = 1;
            for (List<Action> actions : plan) {
                log("Passo #" + step);
                for (Action action : actions) {
                    log("\t- " + action.getId());
                }
                step++;
            }
            log("\nAções Instanciadas: " + Action.getAllActions().size());
            log("Estados Instanciados: " + State.getAllStates().size());            
        } else {
            log("Sem plano.");
        }

        // Get elapsed time
        long elapsedTimeMillis = System.currentTimeMillis() - start;
        float elapsedTimeSec = elapsedTimeMillis / 1000F;
        log("\nsegundos: " + elapsedTimeSec);
}//GEN-LAST:event_jButtonRunActionPerformed

    // Run
    public static void log(String txt) {
        jTextResult.append(txt + "\n");
    }

    public static void logMutex(String txt) {
        if (jCheckBoxDebugMutex.isSelected()) {
            log(txt);
        }
    }

    private String clean(String txt) {
        return txt.replace("\n", " ").replaceAll("\\s+", " ");
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
    private javax.swing.JButton jButtonRun;
    private static javax.swing.JCheckBox jCheckBoxDebugMutex;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextFinalStates;
    private javax.swing.JTextArea jTextInicialStates;
    private javax.swing.JTextArea jTextOperators;
    private static javax.swing.JTextArea jTextResult;
    // End of variables declaration//GEN-END:variables
}
