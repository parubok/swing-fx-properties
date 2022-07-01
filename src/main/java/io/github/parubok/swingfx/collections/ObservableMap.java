/*
 * Copyright (c) 2010, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package io.github.parubok.swingfx.collections;

import io.github.parubok.swingfx.beans.Observable;
import io.github.parubok.swingfx.beans.binding.Bindings;
import io.github.parubok.swingfx.beans.binding.ObjectBinding;

import java.util.Map;

/**
 * A map that allows observers to track changes when they occur.
 *
 * @see MapChangeListener
 * @see MapChangeListener.Change
 * @since JavaFX 2.0
 */
public interface ObservableMap<K, V> extends Map<K, V>, Observable {
    /**
     * Add a listener to this observable map.
     * @param listener the listener for listening to the list changes
     */
    void addListener(MapChangeListener<? super K, ? super V> listener);

    /**
     * Tries to removed a listener from this observable map. If the listener is not
     * attached to this map, nothing happens.
     * @param listener a listener to remove
     */
    void removeListener(MapChangeListener<? super K, ? super V> listener);

    /**
     * Creates a new {@link ObjectBinding} that contains the mapping of a specific key in this map.
     *
     * @param key the key in the {@code Map}
     * @param defaultValue Value of binding when the key has no value in the map.
     * @return the new {@code ObjectBinding}
     * @since swing-fx-properties 1.10
     */
    default ObjectBinding<V> valueAt(K key, V defaultValue) {
        return Bindings.valueAt(this, key, defaultValue);
    }
}
