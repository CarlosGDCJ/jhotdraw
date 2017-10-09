/* @(#)ApplicationModel.java
 * Copyright © 2017 by the authors and contributors of JHotDraw. MIT License.
 */
package org.jhotdraw8.app;

import java.util.ResourceBundle;
import java.util.concurrent.CompletionStage;
import java.util.prefs.Preferences;
import javafx.scene.control.MenuBar;
import javax.annotation.Nonnull;
import org.jhotdraw8.app.action.Action;
import org.jhotdraw8.collection.HierarchicalMap;
import org.jhotdraw8.concurrent.FXWorker;
import org.jhotdraw8.gui.URIChooser;

/**
 * {@code ApplicationModel} provides meta-data, actions and factory methods for
 * an {@link Application}.
 *
 * @design.pattern Application Framework, KeyAbstraction.
 *
 * @author Werner Randelshofer.
 * @version $Id$
 */
public interface ApplicationModel {

    // Projects
    /**
     * Instantiates a project. But does not initialize it.
     *
     * Since this operation may involve class loading, it should be performed in
     * the background.
     *
     * @return a new instance
     */
    @Nonnull
    default CompletionStage<Project> createProjectAsync() {
        return FXWorker.supply(this::createProject);
    }

    @Nonnull
    public Project createProject();
    // URI choosers

    /**
     * Creates an open chooser.
     *
     * @return chooser
     */
    @Nonnull
    public URIChooser createOpenChooser();

    /**
     * Creates a save chooser.
     *
     * @return chooser
     */
    @Nonnull
    public URIChooser createSaveChooser();

    /**
     * Creates an export chooser.
     *
     * @return chooser
     */
    @Nonnull
    public URIChooser createExportChooser();

    /**
     * Creates an import chooser.
     *
     * @return chooser
     */
    @Nonnull
    public URIChooser createImportChooser();

    // Copyright information
    /**
     * Returns the name of the application.
     *
     * @return name
     */
    @Nonnull
    public String getName();

    /**
     * Returns the version of the application.
     *
     * @return version
     */
    @Nonnull
    public String getVersion();

    /**
     * Returns the copyright of the application.
     *
     * @return copyright
     */
    @Nonnull
    public String getCopyright();

    /**
     * Returns true if the same URI can be opened more than once.
     *
     * @return whether multiple views per URI are allowed
     */
    public boolean isAllowMultipleViewsPerURI();

    /**
     * Creates a menu bar. This method is invoked by {@code Application} when it
     * needs to create a menu bar. {@code Application} uses the {@code id} of
     * the menu items in the menu bar to link the menu item with {@code Action}
     * objects.
     *
     * @return a menu bar
     */
    @Nonnull
    default CompletionStage<MenuBar> createMenuBarAsync() {
        return FXWorker.supply(this::createMenuBar);
    }

    MenuBar createMenuBar();

    /**
     * Gets the resource bundle of the application.
     *
     * @return the resource bundle
     */
    @Nonnull
    ResourceBundle getResources();

    /**
     * Creates the application map which is used to populate menu bars.
     *
     * @param app The application
     * @return the application map
     */
    @Nonnull
    HierarchicalMap<String, Action> createApplicationActionMap(@Nonnull Application app);

    /**
     * Gets the preferences of the application.
     *
     * @return the preferences
     */
    @Nonnull
    Preferences getPreferences();
}
