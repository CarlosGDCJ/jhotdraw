/* @(#)InputFormat.java
 * Copyright © The authors and contributors of JHotDraw. MIT License.
 */
package org.jhotdraw8.draw.input;

import javafx.scene.input.Clipboard;
import org.jhotdraw8.annotation.Nullable;
import org.jhotdraw8.draw.figure.Drawing;
import org.jhotdraw8.draw.figure.Figure;
import org.jhotdraw8.draw.figure.Layer;
import org.jhotdraw8.draw.model.DrawingModel;

import java.io.IOException;
import java.util.Set;

/**
 * InputFormat for clipboard.
 *
 * @author Werner Randelshofer
 * @version $Id$
 * @design.pattern Drawing Strategy, Strategy.
 */
public interface ClipboardInputFormat {

    /**
     * Reads a Drawing from the clipboard.
     *
     * @param clipboard The clipboard.
     * @param model     the drawing model over which updates of the drawing must be
     *                  performed.
     * @param drawing   The contents of the clipboard is added to this drawing.
     * @param layer     If you provide a non-null value, the contents of the
     *                  clipboard is added to this layer. Otherwise the content is added into an
     *                  unspecified layer.
     * @return the figures that were read from the clipboard
     * @throws java.io.IOException if an IO error occurs
     */
    Set<Figure> read(Clipboard clipboard, DrawingModel model, Drawing drawing, @Nullable Layer layer) throws IOException;
}
