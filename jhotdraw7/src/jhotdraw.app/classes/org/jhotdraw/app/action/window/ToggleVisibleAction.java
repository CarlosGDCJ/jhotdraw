/* @(#)ToggleVisibleAction.java
 * Copyright © 1996-2017 The authors and contributors of JHotDraw.
 * MIT License, CC-by License, or LGPL License.
 */

package org.jhotdraw.app.action.window;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.jhotdraw.app.action.ActionUtil;

/**
 * Toggles the visible state of a Component.
 * Is selected, when the Component is visible.
 *
 * @author Werner Randelshofer.
 * @version $Id$
 */
public class ToggleVisibleAction extends AbstractAction {
    private static final long serialVersionUID = 1L;
    private Component component;
    
    /** Creates a new instance. */
    public ToggleVisibleAction(Component c, String name) {
        this.component = c;
        putValue(Action.NAME, name);
        putValue(ActionUtil.SELECTED_KEY, c.isVisible());
        c.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                putValue(ActionUtil.SELECTED_KEY, component.isVisible());
            }
            
            @Override
            public void componentHidden(ComponentEvent e) {
                putValue(ActionUtil.SELECTED_KEY, component.isVisible());
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        component.setVisible(! component.isVisible());
    }
}