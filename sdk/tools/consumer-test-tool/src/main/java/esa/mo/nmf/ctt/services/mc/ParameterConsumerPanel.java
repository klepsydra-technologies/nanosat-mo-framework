/* ----------------------------------------------------------------------------
 * Copyright (C) 2021      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : ESA NanoSat MO Framework
 * ----------------------------------------------------------------------------
 * Licensed under European Space Agency Public License (ESA-PL) Weak Copyleft – v2.4
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
package esa.mo.nmf.ctt.services.mc;

import esa.mo.com.impl.provider.ArchivePersistenceObject;
import esa.mo.com.impl.util.HelperArchive;
import esa.mo.helpertools.helpers.HelperAttributes;
import esa.mo.mc.impl.consumer.ParameterConsumerServiceImpl;
import esa.mo.tools.mowindow.MOWindow;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.ccsds.moims.mo.com.structures.InstanceBooleanPair;
import org.ccsds.moims.mo.com.structures.InstanceBooleanPairList;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mc.parameter.ParameterHelper;
import org.ccsds.moims.mo.mc.parameter.consumer.ParameterAdapter;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterCreationRequest;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterCreationRequestList;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterDefinitionDetails;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterDefinitionDetailsList;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterRawValue;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterRawValueList;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterValueDetailsList;
import org.ccsds.moims.mo.mc.structures.ObjectInstancePairList;

/**
 *
 * @author Cesar Coelho
 */
public class ParameterConsumerPanel extends javax.swing.JPanel {

    private final ParameterConsumerServiceImpl serviceMCParameter;
    private final ParameterTablePanel parameterTable;

    /**
     *
     * @param serviceMCParameter
     */
    public ParameterConsumerPanel(ParameterConsumerServiceImpl serviceMCParameter) {
        initComponents();

        this.serviceMCParameter = serviceMCParameter;
        parameterTable = new ParameterTablePanel(serviceMCParameter.getCOMServices().getArchiveService());
        jScrollPane2.setViewportView(parameterTable);
    }

    public void init(){
        this.listDefinitionAllButtonActionPerformed(null);
    }
    
    /**
     * This method is called from within the constructor to initialize the
     * formAddModifyParameter. WARNING: Do NOT modify this code. The content of
     * this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        actionDefinitionsTable = new javax.swing.JTable();
        parameterTab = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        getValueButton = new javax.swing.JButton();
        setValueButton = new javax.swing.JButton();
        enableDefinitionAllAgg = new javax.swing.JButton();
        enableDefinitionButtonAgg = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        addDefinitionButton = new javax.swing.JButton();
        updateDefinitionButton = new javax.swing.JButton();
        removeDefinitionButton = new javax.swing.JButton();
        listDefinitionAllButton = new javax.swing.JButton();
        removeDefinitionAllButton = new javax.swing.JButton();

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Parameter Service - Definitions");
        jLabel6.setToolTipText("");

        jScrollPane2.setHorizontalScrollBar(null);
        jScrollPane2.setPreferredSize(new java.awt.Dimension(796, 380));
        jScrollPane2.setRequestFocusEnabled(false);

        actionDefinitionsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, Boolean.TRUE, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Obj Inst Id", "name", "description", "rawType", "rawUnit", "generationEnabled", "updateInterval"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        actionDefinitionsTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        actionDefinitionsTable.setAutoscrolls(false);
        actionDefinitionsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        actionDefinitionsTable.setMaximumSize(null);
        actionDefinitionsTable.setMinimumSize(null);
        actionDefinitionsTable.setPreferredSize(null);
        actionDefinitionsTable.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                actionDefinitionsTableComponentAdded(evt);
            }
        });
        jScrollPane2.setViewportView(actionDefinitionsTable);

        parameterTab.setLayout(new java.awt.GridLayout(2, 1));

        getValueButton.setText("getValue");
        getValueButton.addActionListener(this::getValueButtonActionPerformed);
        jPanel1.add(getValueButton);

        setValueButton.setText("setValue");
        setValueButton.addActionListener(this::setValueButtonActionPerformed);
        jPanel1.add(setValueButton);

        enableDefinitionAllAgg.setText("enableGeneration(group=false, 0)");
        enableDefinitionAllAgg.addActionListener(this::enableDefinitionAllAggActionPerformed);
        jPanel1.add(enableDefinitionAllAgg);

        enableDefinitionButtonAgg.setText("enableGeneration");
        enableDefinitionButtonAgg.addActionListener(this::enableDefinitionButtonAggActionPerformed);
        jPanel1.add(enableDefinitionButtonAgg);

        parameterTab.add(jPanel1);

        addDefinitionButton.setText("addDefinition");
        addDefinitionButton.addActionListener(this::addDefinitionButtonActionPerformed);
        jPanel5.add(addDefinitionButton);

        updateDefinitionButton.setText("updateDefinition");
        updateDefinitionButton.addActionListener(this::updateDefinitionButtonActionPerformed);
        jPanel5.add(updateDefinitionButton);

        removeDefinitionButton.setText("removeDefinition");
        removeDefinitionButton.addActionListener(this::removeDefinitionButtonActionPerformed);
        jPanel5.add(removeDefinitionButton);

        listDefinitionAllButton.setText("listDefinition(\"*\")");
        listDefinitionAllButton.addActionListener(this::listDefinitionAllButtonActionPerformed);
        jPanel5.add(listDefinitionAllButton);

        removeDefinitionAllButton.setText("removeDefinition(0)");
        removeDefinitionAllButton.addActionListener(this::removeDefinitionAllButtonActionPerformed);
        jPanel5.add(removeDefinitionAllButton);

        parameterTab.add(jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parameterTab, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 882, Short.MAX_VALUE)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(parameterTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addDefinitionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDefinitionButtonActionPerformed
        // Create and Show the Action Definition to the user
        ParameterDefinitionDetails parameterDefinition = new ParameterDefinitionDetails();
        parameterDefinition.setDescription("This Parameter Definition gives a simulated value of the GPS latitude.");
        parameterDefinition.setRawType(Union.DOUBLE_TYPE_SHORT_FORM.byteValue());
        parameterDefinition.setRawUnit("degrees");
        parameterDefinition.setGenerationEnabled(false);
        parameterDefinition.setReportInterval(new Duration(2));
        parameterDefinition.setValidityExpression(null);
        parameterDefinition.setConversion(null);

        ParameterCreationRequest request = new ParameterCreationRequest();
        request.setName(new Identifier("GPS.Latitude"));
        request.setParamDefDetails(parameterDefinition);
        MOWindow parameterDefinitionWindow = new MOWindow(request, true);

        ParameterCreationRequestList requestList = new ParameterCreationRequestList();
        try {
            requestList.add((ParameterCreationRequest) parameterDefinitionWindow.getObject());
        } catch (InterruptedIOException ex) {
            return;
        }
        
        try {
            ObjectInstancePairList objIds = this.serviceMCParameter.getParameterStub().addParameter(requestList);

            if (objIds.isEmpty()) {
                return;
            }

            Thread.sleep(500);
            // Get the stored Parameter Definition from the Archive
            ArchivePersistenceObject comObject = HelperArchive.getArchiveCOMObject(
                    this.serviceMCParameter.getCOMServices().getArchiveService().getArchiveStub(),
                    ParameterHelper.PARAMETERDEFINITION_OBJECT_TYPE, 
                    serviceMCParameter.getConnectionDetails().getDomain(), 
                    objIds.get(0).getObjDefInstanceId());

            if (comObject == null){
                JOptionPane.showMessageDialog(null, "The COM object could not be returned! The objId is: "
                        + objIds.get(0).toString(), "Error", JOptionPane.PLAIN_MESSAGE);

                Thread.sleep(2500);
                // Get the stored Parameter Definition from the Archive
                comObject = HelperArchive.getArchiveCOMObject(
                        this.serviceMCParameter.getCOMServices().getArchiveService().getArchiveStub(),
                    ParameterHelper.PARAMETERDEFINITION_OBJECT_TYPE, 
                    serviceMCParameter.getConnectionDetails().getDomain(), 
                    objIds.get(0).getObjDefInstanceId());
            }
            
            // Add the Parameter Definition to the table
            parameterTable.addEntry(request.getName(), comObject);
        } catch (MALInteractionException | MALException ex) {
            JOptionPane.showMessageDialog(null, "There was an error with the submitted parameter definition.", "Error", JOptionPane.PLAIN_MESSAGE);
            Logger.getLogger(ParameterConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ParameterConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_addDefinitionButtonActionPerformed

    private void updateDefinitionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateDefinitionButtonActionPerformed
        if (parameterTable.getSelectedRow() == -1) { // The row is not selected?
            return;  // Well, then nothing to be done here folks!
        }

        ArchivePersistenceObject obj = parameterTable.getSelectedCOMObject();
        MOWindow moObject = new MOWindow(obj.getObject(), true);

        LongList objIds = new LongList();
        objIds.add(parameterTable.getSelectedIdentityObjId());

        ParameterDefinitionDetailsList defs = new ParameterDefinitionDetailsList();
        try {
            defs.add((ParameterDefinitionDetails) moObject.getObject());
        } catch (InterruptedIOException ex) {
            return;
        }

        try {
            this.serviceMCParameter.getParameterStub().updateDefinition(objIds, defs);
            this.listDefinitionAllButtonActionPerformed(null);
        } catch (MALInteractionException | MALException ex) {
            Logger.getLogger(ParameterConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_updateDefinitionButtonActionPerformed

    private void removeDefinitionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeDefinitionButtonActionPerformed
        if (parameterTable.getSelectedRow() == -1) { // The row is not selected?
            return;  // Well, then nothing to be done here folks!
        }

        Long objId = parameterTable.getSelectedIdentityObjId();
        LongList longlist = new LongList();
        longlist.add(objId);

        try {
            this.serviceMCParameter.getParameterStub().removeParameter(longlist);
            parameterTable.removeSelectedEntry();
        } catch (MALInteractionException | MALException ex) {
            Logger.getLogger(ParameterConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_removeDefinitionButtonActionPerformed

    private void listDefinitionAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listDefinitionAllButtonActionPerformed
        IdentifierList idList = new IdentifierList();
        idList.add(new Identifier("*"));
/*
        ObjectInstancePairList output;
        try {
            output = this.serviceMCParameter.getParameterStub().listDefinition(idList);
            parameterTable.refreshTableWithIds(output, serviceMCParameter.getConnectionDetails().getDomain(), 
                    ParameterHelper.PARAMETERDEFINITION_OBJECT_TYPE);
        } catch (MALInteractionException ex) {
            JOptionPane.showMessageDialog(null, "There was an error during the listDefinition operation.", "Error", JOptionPane.PLAIN_MESSAGE);
            Logger.getLogger(ParameterConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
            return;
        } catch (MALException ex) {
            JOptionPane.showMessageDialog(null, "There was an error during the listDefinition operation.", "Error", JOptionPane.PLAIN_MESSAGE);
            Logger.getLogger(ParameterConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        Logger.getLogger(ParameterConsumerPanel.class.getName()).log(Level.INFO, "listDefinition(\"*\") returned {0} object instance identifiers", output.size());
*/

        try {
            this.serviceMCParameter.getParameterStub().asyncListDefinition(idList, new ParameterAdapter() {
                @Override
                public void listDefinitionResponseReceived(MALMessageHeader msgHeader, ObjectInstancePairList objInstIds, Map qosProperties) {
                    parameterTable.refreshTableWithIds(objInstIds, serviceMCParameter.getConnectionDetails().getDomain(), ParameterHelper.PARAMETERDEFINITION_OBJECT_TYPE);
                    Logger.getLogger(ParameterConsumerPanel.class.getName()).log(Level.INFO, "listDefinition(\"*\") returned {0} object instance identifiers", objInstIds.size());
                }

                @Override
                public void listDefinitionErrorReceived(MALMessageHeader msgHeader, MALStandardError error, Map qosProperties) {
                    JOptionPane.showMessageDialog(null, "There was an error during the listDefinition operation.", "Error", JOptionPane.PLAIN_MESSAGE);
                    Logger.getLogger(ParameterConsumerPanel.class.getName()).log(Level.SEVERE, null, error);
                }
            }
            );
        } catch (MALInteractionException | MALException ex) {
            Logger.getLogger(ParameterConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_listDefinitionAllButtonActionPerformed

    private void removeDefinitionAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeDefinitionAllButtonActionPerformed
        Long objId = (long) 0;
        LongList longlist = new LongList();
        longlist.add(objId);

        try {
            this.serviceMCParameter.getParameterStub().removeParameter(longlist);
            parameterTable.removeAllEntries();
        } catch (MALInteractionException | MALException ex) {
            Logger.getLogger(ParameterConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_removeDefinitionAllButtonActionPerformed

    private void actionDefinitionsTableComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_actionDefinitionsTableComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_actionDefinitionsTableComponentAdded

    private void enableDefinitionAllAggActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enableDefinitionAllAggActionPerformed
        Boolean curState;

        if (parameterTable.getSelectedRow() == -1) {  // Used to avoid problems if no row is selected
            ParameterDefinitionDetails parameterDefinition = (ParameterDefinitionDetails) parameterTable.getFirstCOMObject().getObject();
            if (parameterDefinition != null) {
                curState = parameterDefinition.getGenerationEnabled();
            } else {
                curState = true;
            }
        } else {
            curState = ((ParameterDefinitionDetails) parameterTable.getSelectedCOMObject().getObject()).getGenerationEnabled();
        }

        InstanceBooleanPairList BoolPairList = new InstanceBooleanPairList();
        BoolPairList.add(new InstanceBooleanPair((long) 0, !curState));  // Zero is the wildcard

        try {
            this.serviceMCParameter.getParameterStub().enableGeneration(false, BoolPairList);
            parameterTable.switchEnabledstatusAll(!curState);
        } catch (MALInteractionException | MALException ex) {
            Logger.getLogger(ParameterConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_enableDefinitionAllAggActionPerformed

    private void enableDefinitionButtonAggActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enableDefinitionButtonAggActionPerformed
        if (parameterTable.getSelectedRow() == -1) { // The row is not selected?
            return;  // Well, then nothing to be done here folks!
        }

        Boolean curState = ((ParameterDefinitionDetails) parameterTable.getSelectedCOMObject().getObject()).getGenerationEnabled();
        InstanceBooleanPairList BoolPairList = new InstanceBooleanPairList();
        BoolPairList.add(new InstanceBooleanPair(parameterTable.getSelectedIdentityObjId(), !curState));  // Zero is the wildcard

        try {
            this.serviceMCParameter.getParameterStub().enableGeneration(false, BoolPairList);
            parameterTable.switchEnabledstatus(!curState);
        } catch (MALInteractionException | MALException ex) {
            Logger.getLogger(ParameterConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_enableDefinitionButtonAggActionPerformed

    private void getValueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getValueButtonActionPerformed
        if (parameterTable.getSelectedRow() == -1){ // The row is not selected?
            return;  // Well, then nothing to be done here folks!
        }
        
        LongList longlist = new LongList();
        longlist.add(parameterTable.getSelectedIdentityObjId());

        ParameterValueDetailsList values;
        try {
            values = this.serviceMCParameter.getParameterStub().getValue(longlist);
            
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < values.size(); i++) {
                str.append("The value for objId ")
                        .append(values.get(i).getParamId().toString())
                        .append(" is:");
                
                UOctet validityState = values.get(i).getValue().getValidityState();
                Attribute rawValue = values.get(i).getValue().getRawValue();
                Attribute convertedValue = values.get(i).getValue().getConvertedValue();
                str.append("\nvalidityState: ");
                str.append(validityState == null ? "null" : validityState.toString());
                str.append("\nrawValue: ");
                str.append(rawValue == null ? "null" : rawValue.toString());
                str.append("\nconvertedValue: ");
                str.append(convertedValue == null ? "null" : convertedValue);
                str.append("\n");

                Attribute attribute = values.get(i).getValue().getRawValue();
                if (attribute instanceof Blob){
                    try {
                        // Try to get a serial Object...
                        Object obj = HelperAttributes.blobAttribute2serialObject((Blob) attribute);
                        str.append(obj.toString());
                    } catch (IOException ex) {
                        // If it fails, then just pring the byte array...
                        str.append(Arrays.toString(((Blob) attribute).getValue())).append("\n");
                    }
                }
            }

            JOptionPane.showMessageDialog(null, str.toString(), "Returned Values from the Provider", JOptionPane.PLAIN_MESSAGE);
        } catch (MALInteractionException | MALException ex) {
            Logger.getLogger(ParameterConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_getValueButtonActionPerformed

    private void setValueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setValueButtonActionPerformed
        if (parameterTable.getSelectedRow() == -1){ // The row is not selected?
            return;  // Well, then nothing to be done here folks!
        }

        ParameterRawValue newValue = new ParameterRawValue();
        newValue.setParamInstId(parameterTable.getSelectedIdentityObjId());
        
        MOWindow window = new MOWindow(newValue, true);
        try {
            newValue = (ParameterRawValue) window.getObject();
        } catch (InterruptedIOException ex) {
            return;
        }

        ParameterRawValueList newValues = new ParameterRawValueList();
        newValues.add(newValue);

        try {
            serviceMCParameter.getParameterStub().setValue(newValues);
        } catch (MALInteractionException | MALException ex) {
            Logger.getLogger(ParameterConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_setValueButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable actionDefinitionsTable;
    private javax.swing.JButton addDefinitionButton;
    private javax.swing.JButton enableDefinitionAllAgg;
    private javax.swing.JButton enableDefinitionButtonAgg;
    private javax.swing.JButton getValueButton;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton listDefinitionAllButton;
    private javax.swing.JPanel parameterTab;
    private javax.swing.JButton removeDefinitionAllButton;
    private javax.swing.JButton removeDefinitionButton;
    private javax.swing.JButton setValueButton;
    private javax.swing.JButton updateDefinitionButton;
    // End of variables declaration//GEN-END:variables
}
