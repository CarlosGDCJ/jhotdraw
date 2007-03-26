/*
 * @(#)OpenAction.java  2.0.1  2006-05-18
 *
 * Copyright (c) 1996-2007 by the original authors of JHotDraw
 * and all its contributors ("JHotDraw.org")
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * JHotDraw.org ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with
 * JHotDraw.org.
 */

package org.jhotdraw.application.action;

import org.jhotdraw.application.*;
import org.jhotdraw.gui.Worker;
import org.jhotdraw.util.*;
import org.jhotdraw.gui.*;
import org.jhotdraw.gui.event.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.prefs.*;
import javax.swing.*;
import java.io.*;


/**
 * Opens a file in new project, or in the current project, if it is empty.
 *
 * @author  Werner Randelshofer
 * @version 2.0.1 2006-05-18 Print stack trace added.
 * <br>2.0 2006-02-16 Support for preferences added.
 * <br>1.0.1 2005-07-14 Make project explicitly visible after creating it.
 * <br>1.0  04 January 2005  Created.
 */
public class OpenAction extends AbstractApplicationAction {
    public final static String ID = "open";
    
    /** Creates a new instance. */
    public OpenAction() {
        ResourceBundleUtil labels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.application.Labels");
        labels.configureAction(this, ID);
    }
    
    public void actionPerformed(ActionEvent evt) {
        final DocumentOrientedApplication app = getApplication();
        if (app.isEnabled()) {
            app.setEnabled(false);
            // Search for an empty project
            Project emptyProject = WindowManager.getInstance().getCurrentProject();
            if (emptyProject == null ||
                    emptyProject.getFile() != null ||
                    emptyProject.hasUnsavedChanges()) {
                emptyProject = null;
            }
            
            final Project p;
            if (emptyProject == null) {
                p = app.createProject();
                WindowManager.getInstance().add(p);
            } else {
                p = emptyProject;
            }
            JFileChooser fileChooser = p.getOpenChooser();
            if (fileChooser.showOpenDialog(WindowManager.getInstance().getComponent()) == JFileChooser.APPROVE_OPTION) {
                WindowManager.getInstance().show(p);
                openFile(fileChooser, p);
            } else {
                app.setEnabled(true);
            }
        }
    }
    
    protected void openFile(JFileChooser fileChooser, final Project project) {
        final DocumentOrientedApplication app = getApplication();
        final File file = fileChooser.getSelectedFile();
        app.setEnabled(true);
        project.setEnabled(false);
        
        // If there is another project with we set the multiple open
        // id of our project to max(multiple open id) + 1.
        int multipleOpenId = 1;
        for (Project aProject : WindowManager.getInstance().projects()) {
            if (aProject != project &&
                    aProject.getFile() != null &&
                    aProject.getFile().equals(file)) {
                multipleOpenId = Math.max(multipleOpenId, aProject.getMultipleOpenId() + 1);
            }
        }
        project.setMultipleOpenId(multipleOpenId);
        project.setEnabled(false);

        // Open the file
        project.execute(new Worker() {
            public Object construct() {
                try {
                    project.read(file);
                    return null;
                } catch (Throwable e) {
                    return e;
                }
            }
            public void finished(Object value) {
                fileOpened(project, file, value);
            }
        });
    }
    protected void fileOpened(final Project project, File file, Object value) {
        final DocumentOrientedApplication app = getApplication();
        if (value == null) {
            project.setFile(file);
            project.setEnabled(true);
            Frame w = (Frame) SwingUtilities.getWindowAncestor(project.getComponent());
            if (w != null) {
            w.setExtendedState(w.getExtendedState() & ~Frame.ICONIFIED);
            w.toFront();
            }
            project.getComponent().requestFocus();
                WindowManager.getInstance().addRecentFile(file);
                app.setEnabled(true);
        } else {
            if (value instanceof Throwable) {
                ((Throwable) value).printStackTrace();
            }
            JSheet.showMessageSheet(project.getComponent(),
                    "<html>"+UIManager.getString("OptionPane.css")+
                    "<b>Couldn't open the file \""+file+"\".</b><br>"+
                    value,
                    JOptionPane.ERROR_MESSAGE, new SheetListener() {
                public void optionSelected(SheetEvent evt) {
                   // app.dispose(project);
                }
            }
            );
        }
    }
}