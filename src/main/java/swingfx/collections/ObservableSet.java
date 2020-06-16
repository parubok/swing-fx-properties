/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
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

package swingfx.collections;


import java.util.Set;

import swingfx.beans.Observable;

/**
 * A set that allows observers to track changes when they occur.
 *
 * @see swingfx.collections.SetChangeListener
 * @see swingfx.collections.SetChangeListener.Change
 * @since JavaFX 2.1
 */
public interface ObservableSet<E> extends Set<E>, Observable {
    /**
     * Add a listener to this observable set.
     * @param listener the listener for listening to the set changes
     */
    public void addListener(swingfx.collections.SetChangeListener<? super E> listener);
    /**
     * Tries to removed a listener from this observable set. If the listener is not
     * attached to this list, nothing happens.
     * @param listener a listener to remove
     */
    public void removeListener(SetChangeListener<? super E> listener);
}
