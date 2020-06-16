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

import swingfx.beans.InvalidationListener;
import swingfx.beans.value.ChangeListener;

import com.sun.swingfx.binding.ExpressionHelper;

/**
 * Base class for all readonly properties wrapping an {@code int}. This class provides a default
 * implementation to attach listener.
 *
 * @see swingfx.beans.property.ReadOnlyIntegerProperty
 * @since JavaFX 2.0
 */
public abstract class ReadOnlyIntegerPropertyBase extends ReadOnlyIntegerProperty {

    ExpressionHelper<Number> helper;

    @Override
    public void addListener(InvalidationListener listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public void addListener(ChangeListener<? super Number> listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Number> listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    /**
     * Sends notifications to all attached
     * {@link InvalidationListener InvalidationListeners} and
     * {@link ChangeListener ChangeListeners}.
     *
     * This method needs to be called, if the value of this property changes.
     */
    protected void fireValueChangedEvent() {
        ExpressionHelper.fireValueChangedEvent(helper);
    }

}
