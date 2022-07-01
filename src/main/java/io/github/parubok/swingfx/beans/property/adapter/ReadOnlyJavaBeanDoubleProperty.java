/*
 * Copyright (c) 2011, 2015, Oracle and/or its affiliates. All rights reserved.
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

import io.github.parubok.com.sun.swingfx.property.adapter.Disposer;
import io.github.parubok.com.sun.swingfx.property.adapter.ReadOnlyPropertyDescriptor;
import io.github.parubok.swingfx.beans.property.ReadOnlyDoubleProperty;
import io.github.parubok.swingfx.beans.property.ReadOnlyDoublePropertyBase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

import java.security.AccessController;
import java.security.AccessControlContext;
import java.security.PrivilegedAction;

import io.github.parubok.fxprop.misc.MethodUtil;

/**
 * A {@code ReadOnlyJavaBeanDoubleProperty} provides an adapter between a regular
 * read only Java Bean property of type {@code double} or {@code Double} and a JavaFX
 * {@code ReadOnlyDoubleProperty}. It cannot be created directly, but a
 * {@link ReadOnlyJavaBeanDoublePropertyBuilder} has to be used.
 * <p>
 * As a minimum, the Java Bean must implement a getter for the
 * property. If the getter of an instance of this class is called, the property of
 * the Java Bean is returned. If the Java Bean property is bound (i.e. it supports
 * PropertyChangeListeners), this {@code ReadOnlyJavaBeanDoubleProperty} will be
 * aware of changes in the Java Bean. Otherwise it can be notified about
 * changes by calling {@link #fireValueChangedEvent()}.
 *
 * @see ReadOnlyDoubleProperty
 * @see ReadOnlyJavaBeanDoublePropertyBuilder
 * @since JavaFX 2.1
 */
public final class ReadOnlyJavaBeanDoubleProperty extends ReadOnlyDoublePropertyBase implements ReadOnlyJavaBeanProperty<Number> {

    private final ReadOnlyPropertyDescriptor descriptor;
    private final ReadOnlyPropertyDescriptor.ReadOnlyListener<Number> listener;

    private final AccessControlContext acc = AccessController.getContext();

    ReadOnlyJavaBeanDoubleProperty(ReadOnlyPropertyDescriptor descriptor, Object bean) {
        this.descriptor = descriptor;
        this.listener = descriptor.new ReadOnlyListener<Number>(bean, this);
        descriptor.addListener(listener);
        Disposer.addRecord(this, new DescriptorListenerCleaner(descriptor, listener));
    }

    /**
     * {@inheritDoc}
     *
     * @throws UndeclaredThrowableException if calling the getter of the Java Bean
     * property throws an {@code IllegalAccessException} or an
     * {@code InvocationTargetException}.
     */
    @Override
    public double get() {
        return AccessController.doPrivileged((PrivilegedAction<Double>) () -> {
            try {
                return ((Number)MethodUtil.invoke(
                    descriptor.getGetter(), getBean(), (Object[])null)).doubleValue();
            } catch (IllegalAccessException e) {
                throw new UndeclaredThrowableException(e);
            } catch (InvocationTargetException e) {
                throw new UndeclaredThrowableException(e);
            }
        }, acc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getBean() {
        return listener.getBean();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return descriptor.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fireValueChangedEvent() {
        super.fireValueChangedEvent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        descriptor.removeListener(listener);
    }
}
