/* @(#)Inspector.java
 * Copyright © 2017 by the authors and contributors of JHotDraw. MIT License.
 */
package org.jhotdraw8.draw.inspector;

import javafx.scene.Node;

import javax.annotation.Nullable;

import org.jhotdraw8.draw.DrawingView;

/**
 * API for inspectors.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public interface Inspector {

    void setDrawingView(@Nullable DrawingView view);

    Node getNode();
}
