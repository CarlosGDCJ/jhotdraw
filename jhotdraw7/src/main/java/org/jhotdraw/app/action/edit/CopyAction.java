/* @(#)CopyAction.java
 * Copyright © 1996-2017 The authors and contributors of JHotDraw.
 * MIT License, CC-by License, or LGPL License.
 */
package org.jhotdraw.app.action.edit;

import javax.annotation.Nullable;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.jhotdraw.gui.datatransfer.ClipboardUtil;
import org.jhotdraw.util.*;

/**
 * Copies the selected region and place its contents into the system clipboard.
 * <p>
 * This action acts on the last {@link org.jhotdraw.gui.EditableComponent} /
 * {@code JTextComponent} which had the focus when the {@code ActionEvent}
 * was generated.
 * <p>
 * This action is called when the user selects the Copy item in the Edit
 * menu. The menu item is automatically created by the application.
 * <p>
 * If you want this behavior in your application, you have to create an action
 * with this ID and put it in your {@code ApplicationModel} in method
 * {@link org.jhotdraw.app.ApplicationModel#initApplication}.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class CopyAction extends AbstractSelectionAction {
    private static final long serialVersionUID = 1L;

    public static final String ID = "edit.copy";

    /** Creates a new instance which acts on the currently focused component. */
    public CopyAction() {
        this(null);
    }

    /** Creates a new instance which acts on the specified component.
     *
     * @param target The target of the action. Specify null for the currently
     * focused component.
     */
    public CopyAction(@Nullable JComponent target) {
        super(target);
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        JComponent c = target;
        if (c == null && (KeyboardFocusManager.getCurrentKeyboardFocusManager().
                getPermanentFocusOwner() instanceof JComponent)) {
            c = (JComponent) KeyboardFocusManager.getCurrentKeyboardFocusManager().
                    getPermanentFocusOwner();
        }
        // Note: copying is allowed for disabled components
        if (c != null) {
            c.getTransferHandler().exportToClipboard(
                    c,
                    ClipboardUtil.getClipboard(),
                    TransferHandler.COPY);
        }
    }
}
