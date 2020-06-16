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

package swingfx.beans.property.adapter;

import com.sun.swingfx.property.adapter.Disposer;
import com.sun.swingfx.property.adapter.ReadOnlyPropertyDescriptor;
import swingfx.beans.property.ReadOnlyLongProperty;
import swingfx.beans.property.ReadOnlyLongPropertyBase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

import java.security.AccessController;
import java.security.AccessControlContext;
import java.security.PrivilegedAction;

import org.swingfx.misc.MethodUtil;

/**
 * A {@code ReadOnlyJavaBeanLongProperty} provides an adapter between a regular
 * read only Java Bean property of type {@code long} or {@code Long} and a JavaFX
 * {@code ReadOnlyLongProperty}. It cannot be created directly, but a
 * {@link swingfx.beans.property.adapter.ReadOnlyJavaBeanLongPropertyBuilder} has to be used.
 * <p>
 * As a minimum, the Java Bean must implement a getter for the
 * property. If the getter of an instance of this class is called, the property of
 * the Java Bean is returned. If the Java Bean property is bound (i.e. it supports
 * PropertyChangeListeners), this {@code ReadOnlyJavaBeanLongProperty} will be
 * aware of changes in the Java Bean. Otherwise it can be notified about
 * changes by calling {@link #fireValueChangedEvent()}.
 *
 * @see ReadOnlyLongProperty
 * @see ReadOnlyJavaBeanLongPropertyBuilder
 * @since JavaFX 2.1
 */
public final class ReadOnlyJavaBeanLongProperty extends ReadOnlyLongPropertyBase implements ReadOnlyJavaBeanProperty<Number> {

    private final ReadOnlyPropertyDescriptor descriptor;
    private final ReadOnlyPropertyDescriptor.ReadOnlyListener<Number> listener;

    private final AccessControlContext acc = AccessController.getContext();

    ReadOnlyJavaBeanLongProperty(ReadOnlyPropertyDescriptor descriptor, Object bean) {
        this.descriptor = descriptor;
        this.listener = descriptor.new ReadOnlyListener<Number>(bean, this);
        descriptor.addListener(listener);
        Disposer.addRecord(this, new swingfx.beans.property.adapter.DescriptorListenerCleaner(descriptor, listener));
    }

    /**
     * {@inheritDoc}
     *
     * @throws UndeclaredThrowableException if calling the getter of the Java Bean
     * property throws an {@code IllegalAccessException} or an
     * {@code InvocationTargetException}.
     */
    @Override
    public long get() {
        return AccessController.doPrivileged((PrivilegedAction<Long>) () -> {
            try {
                return ((Number)MethodUtil.invoke(
                    descriptor.getGetter(), getBean(), (Object[])null)).longValue();
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
