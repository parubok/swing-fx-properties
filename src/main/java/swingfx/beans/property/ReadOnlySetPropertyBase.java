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

import com.sun.swingfx.binding.SetExpressionHelper;
import swingfx.beans.InvalidationListener;
import swingfx.beans.value.ChangeListener;
import swingfx.collections.ObservableSet;
import swingfx.collections.SetChangeListener;

/**
 * Base class for all readonly properties wrapping an {@link ObservableSet}.
 * This class provides a default implementation to attach listener.
 *
 * @see swingfx.beans.property.ReadOnlySetProperty
 *
 * @param <E> the type of the {@code Set} elements
 * @since JavaFX 2.1
 */
public abstract class ReadOnlySetPropertyBase<E> extends ReadOnlySetProperty<E> {

    private SetExpressionHelper<E> helper;

    @Override
    public void addListener(InvalidationListener listener) {
        helper = SetExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        helper = SetExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public void addListener(ChangeListener<? super ObservableSet<E>> listener) {
        helper = SetExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super ObservableSet<E>> listener) {
        helper = SetExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public void addListener(SetChangeListener<? super E> listener) {
        helper = SetExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(SetChangeListener<? super E> listener) {
        helper = SetExpressionHelper.removeListener(helper, listener);
    }

    /**
     * This method needs to be called if the reference to the
     * {@link ObservableSet} changes.
     *
     * It sends notifications to all attached
     * {@link InvalidationListener InvalidationListeners},
     * {@link ChangeListener ChangeListeners}, and
     * {@link SetChangeListener}.
     *
     * This method needs to be called, if the value of this property changes.
     */
    protected void fireValueChangedEvent() {
        SetExpressionHelper.fireValueChangedEvent(helper);
    }

    /**
     * This method needs to be called if the content of the referenced
     * {@link ObservableSet} changes.
     *
     * Sends notifications to all attached
     * {@link InvalidationListener InvalidationListeners},
     * {@link ChangeListener ChangeListeners}, and
     * {@link SetChangeListener}.
     *
     * This method is called when the content of the list changes.
     *
     * @param change the change that needs to be propagated
     */
    protected void fireValueChangedEvent(SetChangeListener.Change<? extends E> change) {
        SetExpressionHelper.fireValueChangedEvent(helper, change);
    }



}
