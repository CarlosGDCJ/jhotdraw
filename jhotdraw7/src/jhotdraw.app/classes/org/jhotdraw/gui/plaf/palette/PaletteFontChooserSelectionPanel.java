/* @(#)PaletteFontChooserSelectionPanel.java
 * Copyright © 1996-2017 The authors and contributors of JHotDraw.
 * MIT License, CC-by License, or LGPL License.
 */

package org.jhotdraw.gui.plaf.palette;

import java.awt.Color;
import java.util.ResourceBundle;
import javax.swing.*;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 * PaletteFontChooserSelectionPanel.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class PaletteFontChooserSelectionPanel extends javax.swing.JPanel {
private ResourceBundleUtil labels;
    private static final long serialVersionUID = 1L;
    /** Creates new form FontChooserPanel */
    public PaletteFontChooserSelectionPanel() {
         labels = new ResourceBundleUtil(ResourceBundle.getBundle("org.jhotdraw.gui.Labels"));
        initComponents();
        collectionList.setModel(new DefaultListModel());
        familyList.setModel(new DefaultListModel());
        faceList.setModel(new DefaultListModel());
        
        // Customization of Quaqua Look and Feel: Set small scroll bars
        collectionsScrollPane.putClientProperty("JComponent.sizeVariant", "small");
        familiesScrollPane.putClientProperty("JComponent.sizeVariant", "small");
        facesScrollPane.putClientProperty("JComponent.sizeVariant", "small");

        // Customization of Nimbus Look and Feel: Set small scroll bars
        collectionsScrollPane.getVerticalScrollBar().putClientProperty("JComponent.sizeVariant", "small");
        familiesScrollPane.getVerticalScrollBar().putClientProperty("JComponent.sizeVariant", "small");
        facesScrollPane.getVerticalScrollBar().putClientProperty("JComponent.sizeVariant", "small");
        collectionsScrollPane.getVerticalScrollBar().updateUI();
        familiesScrollPane.updateUI();
        facesScrollPane.getVerticalScrollBar().updateUI();

        setOpaque(true);
        setBackground(new Color(0xededed));
    }
    
    public JList getCollectionList() {
        return collectionList;
    }
    public JList getFamilyList() {
        return familyList;
    }
    public JList getFaceList() {
        return faceList;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        collectionsPanel = new javax.swing.JPanel();
        collectionsScrollPane = new javax.swing.JScrollPane();
        collectionList = new javax.swing.JList();
        collectionsLabel = new javax.swing.JLabel();
        familiesPanel = new javax.swing.JPanel();
        familiesScrollPane = new javax.swing.JScrollPane();
        familyList = new javax.swing.JList();
        familyLabel = new javax.swing.JLabel();
        facesPanel = new javax.swing.JPanel();
        facesScrollPane = new javax.swing.JScrollPane();
        faceList = new javax.swing.JList();
        faceLabel = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        setLayout(new java.awt.GridBagLayout());

        collectionsPanel.setPreferredSize(new java.awt.Dimension(80, 200));
        collectionsPanel.setLayout(new java.awt.BorderLayout());

        collectionsScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        collectionList.setFont(collectionList.getFont().deriveFont((float)11));
        collectionsScrollPane.setViewportView(collectionList);

        collectionsPanel.add(collectionsScrollPane, java.awt.BorderLayout.CENTER);

        collectionsLabel.setFont(collectionsLabel.getFont().deriveFont((float)11));
        collectionsLabel.setText(labels.getString("FontCollection.collections")); // NOI18N
        collectionsPanel.add(collectionsLabel, java.awt.BorderLayout.PAGE_START);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 1.0;
        add(collectionsPanel, gridBagConstraints);

        familiesPanel.setPreferredSize(new java.awt.Dimension(140, 200));
        familiesPanel.setLayout(new java.awt.BorderLayout());

        familiesScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        familyList.setFont(familyList.getFont().deriveFont((float)11));
        familiesScrollPane.setViewportView(familyList);

        familiesPanel.add(familiesScrollPane, java.awt.BorderLayout.CENTER);

        familyLabel.setFont(familyLabel.getFont().deriveFont((float)11));
        familyLabel.setText(labels.getString("FontCollection.family")); // NOI18N
        familiesPanel.add(familyLabel, java.awt.BorderLayout.PAGE_START);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
        add(familiesPanel, gridBagConstraints);

        facesPanel.setPreferredSize(new java.awt.Dimension(80, 200));
        facesPanel.setLayout(new java.awt.BorderLayout());

        facesScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        facesScrollPane.setPreferredSize(new java.awt.Dimension(130, 240));

        faceList.setFont(faceList.getFont().deriveFont((float)11));
        facesScrollPane.setViewportView(faceList);

        facesPanel.add(facesScrollPane, java.awt.BorderLayout.CENTER);

        faceLabel.setFont(faceLabel.getFont().deriveFont((float)11));
        faceLabel.setText(labels.getString("FontCollection.typeface")); // NOI18N
        facesPanel.add(faceLabel, java.awt.BorderLayout.PAGE_START);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
        add(facesPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList collectionList;
    private javax.swing.JLabel collectionsLabel;
    private javax.swing.JPanel collectionsPanel;
    private javax.swing.JScrollPane collectionsScrollPane;
    private javax.swing.JLabel faceLabel;
    private javax.swing.JList faceList;
    private javax.swing.JPanel facesPanel;
    private javax.swing.JScrollPane facesScrollPane;
    private javax.swing.JPanel familiesPanel;
    private javax.swing.JScrollPane familiesScrollPane;
    private javax.swing.JLabel familyLabel;
    private javax.swing.JList familyList;
    // End of variables declaration//GEN-END:variables

}
