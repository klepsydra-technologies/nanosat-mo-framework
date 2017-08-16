/* ----------------------------------------------------------------------------
 * Copyright (C) 2015      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : ESA NanoSat MO Framework
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package esa.mo.nmf.nmfpackage.assembler.gui;

import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * This class provides a simple form for the control of the consumer.
 */
public class NMFPackageAssemblerGUI extends javax.swing.JFrame {
    
    private final static String TYPE_APPLICATION = "Application";
    private final static String TYPE_LIBRARY = "Library";

    /**
     * Main command line entry point.
     *
     * @param args the command line arguments
     */
    public static void main(final String args[]) {
        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (UnsupportedLookAndFeelException e) {
        } catch (ClassNotFoundException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
            // handle exception
        }

        final String name = System.getProperty("application.name", "NMF Package Assembler");
        final NMFPackageAssemblerGUI gui = new NMFPackageAssemblerGUI(name);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui.setVisible(true);
            }
        });
    }

    /**
     * Creates new form MOConsumerGUI
     *
     * @param name The name to display on the title bar of the form.
     */
    public NMFPackageAssemblerGUI(final String name) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle(name);
    }

    /**
     * Removes an Panel entry.
     *
     * @param aPanel The panel to be removed
     */
    public void removeEntry(SlicePanel aPanel) {
        // Remove the entry:
        jPanel2.remove(aPanel);
        repaint();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel11 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jSeparator7 = new javax.swing.JSeparator();
        jPanel13 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        textFieldName = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jSeparator8 = new javax.swing.JSeparator();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1000, 600));
        setName("Form"); // NOI18N
        setResizable(false);
        getContentPane().setLayout(new java.awt.BorderLayout(0, 4));

        tabs.setToolTipText("");
        tabs.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabs.setMaximumSize(new java.awt.Dimension(800, 600));
        tabs.setMinimumSize(new java.awt.Dimension(800, 600));
        tabs.setName("tabs"); // NOI18N
        tabs.setPreferredSize(new java.awt.Dimension(800, 600));
        tabs.setRequestFocusEnabled(false);

        jPanel1.setName("jPanel1"); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Package Definition");
        jLabel7.setToolTipText("");
        jLabel7.setMaximumSize(new java.awt.Dimension(820, 22));
        jLabel7.setMinimumSize(new java.awt.Dimension(820, 22));
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(820, 22));
        jPanel1.add(jLabel7);

        jPanel8.setName("jPanel8"); // NOI18N
        jPanel8.setPreferredSize(new java.awt.Dimension(2510, 25));

        jSeparator6.setName("jSeparator6"); // NOI18N
        jSeparator6.setPreferredSize(new java.awt.Dimension(700, 15));
        jPanel8.add(jSeparator6);

        jPanel1.add(jPanel8);

        jPanel11.setName("jPanel11"); // NOI18N
        jPanel11.setPreferredSize(new java.awt.Dimension(2510, 25));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Package Name:");
        jLabel1.setMaximumSize(new java.awt.Dimension(150, 14));
        jLabel1.setMinimumSize(new java.awt.Dimension(150, 14));
        jLabel1.setName("jLabel1"); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(150, 14));
        jPanel11.add(jLabel1);

        jTextField1.setMinimumSize(new java.awt.Dimension(400, 20));
        jTextField1.setName("jTextField1"); // NOI18N
        jTextField1.setPreferredSize(new java.awt.Dimension(400, 20));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel11.add(jTextField1);

        jPanel1.add(jPanel11);

        jPanel12.setName("jPanel12"); // NOI18N
        jPanel12.setPreferredSize(new java.awt.Dimension(2510, 25));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Package Version:");
        jLabel3.setMaximumSize(new java.awt.Dimension(150, 14));
        jLabel3.setMinimumSize(new java.awt.Dimension(150, 14));
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(150, 14));
        jPanel12.add(jLabel3);

        jTextField5.setMinimumSize(new java.awt.Dimension(400, 20));
        jTextField5.setName("jTextField5"); // NOI18N
        jTextField5.setPreferredSize(new java.awt.Dimension(400, 20));
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        jPanel12.add(jTextField5);

        jPanel1.add(jPanel12);

        tabs.addTab("Step 1: Define NMF Package", jPanel1);

        jPanel2.setName("jPanel2"); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Content Selection");
        jLabel8.setToolTipText("");
        jLabel8.setMaximumSize(new java.awt.Dimension(820, 22));
        jLabel8.setMinimumSize(new java.awt.Dimension(820, 22));
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(820, 22));
        jPanel2.add(jLabel8);

        jPanel9.setName("jPanel9"); // NOI18N
        jPanel9.setPreferredSize(new java.awt.Dimension(2510, 25));

        jSeparator7.setName("jSeparator7"); // NOI18N
        jSeparator7.setPreferredSize(new java.awt.Dimension(700, 15));
        jPanel9.add(jSeparator7);

        jPanel2.add(jPanel9);

        jPanel13.setMinimumSize(new java.awt.Dimension(693, 40));
        jPanel13.setName("jPanel13"); // NOI18N
        jPanel13.setPreferredSize(new java.awt.Dimension(2510, 40));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Name:");
        jLabel2.setMaximumSize(new java.awt.Dimension(100, 14));
        jLabel2.setMinimumSize(new java.awt.Dimension(100, 14));
        jLabel2.setName("jLabel2"); // NOI18N
        jLabel2.setPreferredSize(new java.awt.Dimension(100, 14));
        jPanel13.add(jLabel2);

        textFieldName.setMinimumSize(new java.awt.Dimension(200, 20));
        textFieldName.setName("textFieldName"); // NOI18N
        textFieldName.setPreferredSize(new java.awt.Dimension(200, 20));
        textFieldName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldNameActionPerformed(evt);
            }
        });
        jPanel13.add(textFieldName);

        jButton2.setText("Add Application");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton2);

        jButton1.setText("Add Library");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton1);

        jPanel2.add(jPanel13);

        tabs.addTab("Step 2: Select Files", jPanel2);

        jPanel3.setName("jPanel3"); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Package generation");
        jLabel9.setToolTipText("");
        jLabel9.setMaximumSize(new java.awt.Dimension(820, 22));
        jLabel9.setMinimumSize(new java.awt.Dimension(820, 22));
        jLabel9.setName("jLabel9"); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(820, 22));
        jPanel3.add(jLabel9);

        jPanel10.setName("jPanel10"); // NOI18N
        jPanel10.setPreferredSize(new java.awt.Dimension(2510, 25));

        jSeparator8.setName("jSeparator8"); // NOI18N
        jSeparator8.setPreferredSize(new java.awt.Dimension(700, 15));
        jPanel10.add(jSeparator8);

        jPanel3.add(jPanel10);

        tabs.addTab("Step 3: Generate NMF Package", jPanel3);

        getContentPane().add(tabs, java.awt.BorderLayout.CENTER);

        jMenuBar1.setName("jMenuBar1"); // NOI18N

        jMenu1.setText("File");
        jMenu1.setName("jMenu1"); // NOI18N
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenu2.setName("jMenu2"); // NOI18N
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void textFieldNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldNameActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Here we add a new Library to the NMF Package:
        final String name = textFieldName.getText();
        textFieldName.setText(""); // reset text field
        javax.swing.JPanel newPanel = new SlicePanel(this, TYPE_LIBRARY, name);
        jPanel2.add(newPanel);
        repaint();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Here we add a new Application to the NMF Package:
        final String name = textFieldName.getText();
        textFieldName.setText(""); // reset text field
        javax.swing.JPanel newPanel = new SlicePanel(this, TYPE_APPLICATION, name);
        jPanel2.add(newPanel);
        repaint();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTextField textFieldName;
    // End of variables declaration//GEN-END:variables

}