/* @(#)OpenRecentFileAction.java
 * Copyright © 2017 by the authors and contributors of JHotDraw. MIT License.
 */
package org.jhotdraw8.app.action.file;

import java.net.URI;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.concurrent.CancellationException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.DataFormat;
import org.jhotdraw8.app.Application;
import org.jhotdraw8.app.action.AbstractApplicationAction;
import org.jhotdraw8.app.action.Action;
import org.jhotdraw8.net.UriUtil;
import org.jhotdraw8.util.Resources;
import org.jhotdraw8.app.DocumentOrientedViewModel;

/**
 * Loads the specified URI into an empty view. If no empty view is available, a
 * new view is created.
 * <p>
 * This action is called when the user selects an item in the Recent Files
 * submenu of the File menu. The action and the menu item is automatically
 * created by the application, when the {@code ApplicationModel} provides a
 * {@code OpenFileAction}.
 * <hr>
 * <b>Features</b>
 *
 * <p>
 * <em>Allow multiple views per URI</em><br>
 * When the feature is disabled, {@code OpenRecentFileAction} prevents opening
 * an URI which is opened in another view.<br>
 * See {@link org.jhotdraw8.app} for a description of the feature.
 * </p>
 *
 * <p>
 * <em>Open last URI on launch</em><br> {@code OpenRecentFileAction} supplies
 * data for this feature by calling {@link Application#addRecentURI} when it
 * successfully opened a file. See {@link org.jhotdraw8.app} for a description
 * of the feature.
 * </p>
 *
 * @author Werner Randelshofer.
 * @version $Id$
 */
public class OpenRecentFileAction extends AbstractApplicationAction {

    private static final long serialVersionUID = 1L;

    public static final String ID = "file.openRecent";
    private URI uri;
    private boolean reuseEmptyViews = true;

    /**
     * Creates a new instance.
     *
     * @param app the application
     * @param uri the uri
     */
    public OpenRecentFileAction(Application app, URI uri) {
        super(app);
        this.uri = uri;
        set(Action.LABEL, UriUtil.getName(uri));
    }

    @Override
    protected void handleActionPerformed(ActionEvent evt, Application app) {
        {
            // Search for an empty view
            DocumentOrientedViewModel emptyView;
            if (reuseEmptyViews) {
                emptyView = (DocumentOrientedViewModel) app.getActiveView();//FIXME class cast exception
                if (emptyView == null
                        || !emptyView.isEmpty()
                        || emptyView.isDisabled()) {
                    emptyView = null;
                }
            } else {
                emptyView = null;
            }

            if (emptyView == null) {
                app.createView().thenAccept(v -> {
                    app.add(v);
                    doIt((DocumentOrientedViewModel) v, true);
                });
            } else {
                doIt(emptyView, false);
            }
        }
    }

    public void doIt(DocumentOrientedViewModel view, boolean disposeView) {
        openViewFromURI(view, uri);
    }

    private void handleException(final DocumentOrientedViewModel v, Throwable exception) throws MissingResourceException {
        Throwable value = exception;
        exception.printStackTrace();
        Resources labels = Resources.getResources("org.jhotdraw8.app.Labels");
                Alert alert = new Alert(Alert.AlertType.ERROR, createErrorMessage(exception));
        alert.getDialogPane().setMaxWidth(640.0);
        alert.setHeaderText(labels.getFormatted("file.open.couldntOpen.message", UriUtil.getName(uri)));
        
        // Note: we must invoke clear() or read() on the view, before we start using it.
        v.clear();
        alert.showAndWait();
        v.removeDisabler(this);
    }

    protected void openViewFromURI(final DocumentOrientedViewModel v, final URI uri) {
        final Application app = getApplication();
        v.addDisabler(this);

        final DataFormat format;
        Map<String, String> query = UriUtil.parseQuery(uri);
        URI u = UriUtil.clearQuery(uri);// FIXME only remove "mimeType" query.
        String formatString = query.get("mimeType");
        if (formatString != null) {
            format = DataFormat.lookupMimeType(formatString);
        }else{
            format = null;
        }
        // Open the file
        try {
            v.read(u, format, null, false).whenComplete((result, exception) -> {
                if (exception instanceof CancellationException) {
                    v.removeDisabler(this);
                } else if (exception != null) {
                    handleException(v, exception);
                } else {
                    v.setURI(u);
                    v.clearModified();
                    v.setTitle(UriUtil.getName(uri));
                    v.removeDisabler(this);
                }
            });
        } catch (Throwable t) {
            handleException(v, t);
        }
    }
}
