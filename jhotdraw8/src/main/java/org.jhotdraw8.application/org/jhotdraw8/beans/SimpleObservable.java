/* @(#)SimpleObservable.java
 * Copyright © The authors and contributors of JHotDraw. MIT License.
 */
package org.jhotdraw8.beans;

import javafx.beans.InvalidationListener;
import org.jhotdraw8.annotation.Nonnull;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * SimpleObservable.
 *
 * @author Werner Randelshofer
 * @version $Id$
 * @design.pattern SimpleObservable Observer, ConcreteSubject.
 * {@link SimpleObservable} is a concrete subject implementation of the Observer
 * pattern.
 */
public class SimpleObservable implements ObservableMixin {

    private final CopyOnWriteArrayList<InvalidationListener> invalidationListeners = new CopyOnWriteArrayList<>();

    @Nonnull
    public CopyOnWriteArrayList<InvalidationListener> getInvalidationListeners() {
        return invalidationListeners;
    }
}
