/* @(#)FileURIChooser.java
 * Copyright © The authors and contributors of JHotDraw. MIT License.
 */
package org.jhotdraw8.gui;

import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import org.jhotdraw8.annotation.Nonnull;
import org.jhotdraw8.annotation.Nullable;

import java.io.File;
import java.net.URI;

/**
 * FileURIChooser.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class DirectoryURIChooser implements URIChooser {

    /**
     * The associated file chooser object.
     */
    private final DirectoryChooser chooser = new DirectoryChooser();

    @Nonnull
    public DirectoryChooser getDirectoryChooser() {
        return chooser;
    }

    @Nullable
    @Override
    public URI showDialog(Window parent) {
        File f = chooser.showDialog(parent);

        return f == null ? null : f.toURI();
    }
}
