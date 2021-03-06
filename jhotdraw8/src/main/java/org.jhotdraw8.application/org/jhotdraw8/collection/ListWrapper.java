/* @(#)ListWrapper.java
 * Copyright © by the authors and contributors ofCollection JHotDraw. MIT License.
 */
package org.jhotdraw8.collection;

import java.util.AbstractList;
import java.util.List;

/**
 * Wraps a {@link ReadOnlyList} in the {@link List} API.
 * <p>
 * The underlying ReadOnlyList is referenced - not copied. This allows to pass a
 * ReadOnlyList to a client who does not understand the ReadOnlyList APi.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class ListWrapper<E> extends AbstractList<E> {
    private final ReadOnlyList<E> backingList;

    public ListWrapper(ReadOnlyList<E> backingList) {
        this.backingList = backingList;
    }

    @Override
    public E get(int index) {
        return backingList.get(index);
    }

    @Override
    public int size() {
        return backingList.size();
    }
}
