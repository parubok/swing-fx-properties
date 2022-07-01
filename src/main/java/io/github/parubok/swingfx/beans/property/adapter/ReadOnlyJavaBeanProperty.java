/*
 * Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved.
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

package io.github.parubok.swingfx.beans.property.adapter;

import io.github.parubok.swingfx.beans.property.ReadOnlyProperty;

/**
 * {@code JavaBeanProperty&lt;T&gt;} is the super interface of all adapters between
 * readonly Java Bean properties and JavaFX properties.
 *
 * @param T The type of the wrapped property
 * @since JavaFX 2.1
 */
public interface ReadOnlyJavaBeanProperty<T> extends ReadOnlyProperty<T> {
    /**
     * This method can be called to notify the adapter of a change of the Java
     * Bean value, if the Java Bean property is not bound (i.e. it does not
     * support PropertyChangeListeners).
     */
    void fireValueChangedEvent();

    /**
     * Signals to the JavaFX property that it will not be used anymore and any
     * references can be removed. A call of this method usually results in the
     * property stopping to observe the Java Bean property by unregistering its
     * listener(s).
     */
    void dispose();
}
