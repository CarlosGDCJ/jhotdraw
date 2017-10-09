/* @(#)MapAccessor.java
 * Copyright © 2017 by the authors and contributors of JHotDraw. MIT License.
 */
package org.jhotdraw8.collection;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A type safe accessor for maps.
 * <p>
 * Design pattern: Strategy
 *
 * @design.pattern MapAccessor Strategy, Strategy. Encapsulates a strategy for
 * accessing property values of a map.
 *
 * @param <T> The value type.
 * @author Werner Randelshofer
 * @version $Id$
 */
public interface MapAccessor<T> extends Serializable {

    final static long serialVersionUID = 1L;

    /**
     * Whether the map contains all keys required by this map accessor.
     *
     * @param map a map
     * @return true if map contains all keys required by this map accessor.
     */
    boolean containsKey(@Nonnull Map<Key<?>, Object> map);

    /**
     * Returns the name string.
     *
     * @return name string.
     */
    @Nonnull
    String getName();

    /**
     * Gets the value of the attribute denoted by this accessor from a Map.
     *
     * @param a A Map.
     * @return The value of the attribute.
     */
    @Nullable
    T get(@Nonnull Map<? super Key<?>, Object> a);

    /**
     * Puts the value of the attribute denoted by this accessor from a Map.
     *
     * @param a A map.
     * @param value The new value.
     * @return The old value.
     */
    @Nullable
    T put(@Nonnull Map<? super Key<?>, Object> a, @Nullable T value);

    /**
     * Removes the value of the attribute denoted by this accessor from a Map.
     *
     * @param a A map.
     * @return The old value.
     */
    @Nullable
    T remove(@Nonnull Map<? super Key<?>, Object> a);

    /**
     * Returns the value type.
     *
     * @return the value type
     */
    @Nonnull
    Class<T> getValueType();

    /**
     * Returns the type parameters of the value type.
     *
     * @return an unmodifiable list with the type parameters
     */
    @Nonnull
    List<Class<?>> getValueTypeParameters();

    /**
     * Returns the default value of this map accessor.
     *
     * @return the default value
     */
    @Nullable
    T getDefaultValue();

    /**
     * Returns a string representation of the value type and its type
     * parameters.
     *
     * @return the class name of the value type including type parameters
     */
    @Nonnull
    String getFullValueType();

    /**
     * Whether the value needs to be made persistent.
     *
     * @return true if transient
     */
    boolean isTransient();
}
