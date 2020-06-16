/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
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

package swingfx.beans.property;

import com.sun.swingfx.binding.MapExpressionHelper;
import swingfx.beans.InvalidationListener;
import swingfx.beans.value.ChangeListener;
import swingfx.collections.MapChangeListener;
import swingfx.collections.ListChangeListener;
import swingfx.collections.ObservableMap;
import swingfx.collections.ObservableList;

/**
 * Base class for all readonly properties wrapping an {@link ObservableMap}.
 * This class provides a default implementation to attach listener.
 *
 * @see swingfx.beans.property.ReadOnlyMapProperty
 * @since JavaFX 2.1
 */
public abstract class ReadOnlyMapPropertyBase<K, V> extends ReadOnlyMapProperty<K, V> {

    private MapExpressionHelper<K, V> helper;

    @Override
    public void addListener(InvalidationListener listener) {
        helper = MapExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        helper = MapExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public void addListener(ChangeListener<? super ObservableMap<K, V>> listener) {
        helper = MapExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super ObservableMap<K, V>> listener) {
        helper = MapExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public void addListener(MapChangeListener<? super K, ? super V> listener) {
        helper = MapExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(MapChangeListener<? super K, ? super V> listener) {
        helper = MapExpressionHelper.removeListener(helper, listener);
    }

    /**
     * This method needs to be called if the reference to the
     * {@link ObservableList} changes.
     *
     * It sends notifications to all attached
     * {@link InvalidationListener InvalidationListeners},
     * {@link ChangeListener ChangeListeners}, and
     * {@link ListChangeListener}.
     *
     * This method needs to be called, if the value of this property changes.
     */
    protected void fireValueChangedEvent() {
        MapExpressionHelper.fireValueChangedEvent(helper);
    }

    /**
     * This method needs to be called if the content of the referenced
     * {@link ObservableList} changes.
     *
     * Sends notifications to all attached
     * {@link InvalidationListener InvalidationListeners},
     * {@link ChangeListener ChangeListeners}, and
     * {@link ListChangeListener}.
     *
     * This method is called when the content of the list changes.
     *
     * @param change the change that needs to be propagated
     */
    protected void fireValueChangedEvent(MapChangeListener.Change<? extends K, ? extends V> change) {
        MapExpressionHelper.fireValueChangedEvent(helper, change);
    }



}
