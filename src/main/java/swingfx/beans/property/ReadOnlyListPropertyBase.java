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

import com.sun.swingfx.binding.ListExpressionHelper;
import swingfx.beans.InvalidationListener;
import swingfx.beans.value.ChangeListener;
import swingfx.collections.ListChangeListener;
import swingfx.collections.ObservableList;

/**
 * Base class for all readonly properties wrapping a {@link ObservableList}.
 * This class provides a default implementation to attach listener.
 *
 * @see swingfx.beans.property.ReadOnlyListProperty
 * @since JavaFX 2.1
 */
public abstract class ReadOnlyListPropertyBase<E> extends ReadOnlyListProperty<E> {

    private ListExpressionHelper<E> helper;

    @Override
    public void addListener(InvalidationListener listener) {
        helper = ListExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        helper = ListExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public void addListener(ChangeListener<? super ObservableList<E>> listener) {
        helper = ListExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super ObservableList<E>> listener) {
        helper = ListExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public void addListener(ListChangeListener<? super E> listener) {
        helper = ListExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ListChangeListener<? super E> listener) {
        helper = ListExpressionHelper.removeListener(helper, listener);
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
        ListExpressionHelper.fireValueChangedEvent(helper);
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
    protected void fireValueChangedEvent(ListChangeListener.Change<? extends E> change) {
        ListExpressionHelper.fireValueChangedEvent(helper, change);
    }



}
