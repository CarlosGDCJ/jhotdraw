/* @(#)SetWrapper.java
 * Copyright © The authors and contributors of JHotDraw. MIT License.
 */
package org.jhotdraw8.collection;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Wraps a {@link ReadOnlySet} in the {@link Set} API.
 * <p>
 * The underlying ReadOnlySet is referenced - not copied. This allows to pass a
 * ReadOnlySet to a client who does not understand the ReadOnlySet APi.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class SetWrapper<E> extends AbstractSet<E> {
    private final ReadOnlySet<E> backingSet;

    public SetWrapper(ReadOnlySet<E> backingSet) {
        this.backingSet = backingSet;
    }

    @Override
    public Iterator<E> iterator() {
        return backingSet.iterator();
    }

    @Override
    public int size() {
        return backingSet.size();
    }

    @Override
    public boolean contains(Object o) {
        return backingSet.contains(o);
    }
}
