/* @(#)TreePresentationModel.java
 * Copyright © 2017 by the authors and contributors of JHotDraw. MIT License.
 */
package org.jhotdraw8.tree;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TreeItem;
import javax.annotation.Nonnull;
import org.jhotdraw8.beans.NonnullProperty;
import org.jhotdraw8.event.Listener;

/**
 * The {@code TreePresentationModel} can be used to present a
 * {@code TreeModel} in a {@code TreeView} or a {@code TreeTableView}.
 * <p>
 * Maps {@code TreeModel} to a {@code TreeItem&lt;E&gt;} hierarchy.
 * <p>
 * Note: for performance reasons we do not expand the tree nodes by default.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public interface TreePresentationModel<N>  {

    /**
     * The name of the model property.
     */
    public final static String MODEL_PROPERTY = "model";

  
@Nonnull 
    default TreeModel<N> getTreeModel() {
        return treeModelProperty().get();
    }

    default void setTreeModel(@Nonnull TreeModel<N> newValue) {
         treeModelProperty().set(newValue);
    }
@Nonnull 
    NonnullProperty<TreeModel<N>> treeModelProperty();
@Nonnull 
     TreeItem<N> getRoot();
}
