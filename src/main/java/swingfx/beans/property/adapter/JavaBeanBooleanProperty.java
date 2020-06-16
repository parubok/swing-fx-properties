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

import com.sun.swingfx.binding.ExpressionHelper;
import com.sun.swingfx.property.adapter.Disposer;
import com.sun.swingfx.property.adapter.PropertyDescriptor;
import swingfx.beans.InvalidationListener;
import swingfx.beans.property.BooleanProperty;
import swingfx.beans.value.ChangeListener;
import swingfx.beans.value.ObservableValue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.swingfx.misc.MethodUtil;

/**
 * A {@code JavaBeanBooleanProperty} provides an adapter between a regular
 * Java Bean property of type {@code boolean} or {@code Boolean} and a JavaFX
 * {@code BooleanProperty}. It cannot be created directly, but a
 * {@link swingfx.beans.property.adapter.JavaBeanBooleanPropertyBuilder} has to be used.
 * <p>
 * As a minimum, the Java Bean must implement a getter and a setter for the
 * property. If the getter of an instance of this class is called, the property of
 * the Java Bean is returned. If the setter is called, the value will be passed
 * to the Java Bean property. If the Java Bean property is bound (i.e. it supports
 * PropertyChangeListeners), this {@code JavaBeanBooleanProperty} will be
 * aware of changes in the Java Bean. Otherwise it can be notified about
 * changes by calling {@link #fireValueChangedEvent()}. If the Java Bean property
 * is also constrained (i.e. it supports VetoableChangeListeners), this
 * {@code JavaBeanBooleanProperty} will reject changes, if it is bound to an
 * {@link ObservableValue ObservableValue&lt;Boolean&gt;}.
 *
 * @see BooleanProperty
 * @see JavaBeanBooleanPropertyBuilder
 * @since JavaFX 2.1
 */
public final class JavaBeanBooleanProperty extends BooleanProperty implements JavaBeanProperty<Boolean> {

    private final PropertyDescriptor descriptor;
    private final PropertyDescriptor.Listener<Boolean> listener;

    private ObservableValue<? extends Boolean> observable = null;
    private ExpressionHelper<Boolean> helper = null;

    private final AccessControlContext acc = AccessController.getContext();

    JavaBeanBooleanProperty(PropertyDescriptor descriptor, Object bean) {
        this.descriptor = descriptor;
        this.listener = descriptor.new Listener<Boolean>(bean, this);
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
    public boolean get() {
        return AccessController.doPrivileged((PrivilegedAction<Boolean>) () -> {
            try {
                return (Boolean)MethodUtil.invoke(descriptor.getGetter(), getBean(), (Object[])null);
            } catch (IllegalAccessException e) {
                throw new UndeclaredThrowableException(e);
            } catch (InvocationTargetException e) {
                throw new UndeclaredThrowableException(e);
            }
        }, acc);
    }

    /**
     * {@inheritDoc}
     *
     * @throws UndeclaredThrowableException if calling the getter of the Java Bean
     * property throws an {@code IllegalAccessException} or an
     * {@code InvocationTargetException}.
     */
    @Override
    public void set(final boolean value) {
        if (isBound()) {
            throw new RuntimeException("A bound value cannot be set.");
        }

        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            try {
                MethodUtil.invoke(descriptor.getSetter(), getBean(), new Object[] {value});
                ExpressionHelper.fireValueChangedEvent(helper);
            } catch (IllegalAccessException e) {
                throw new UndeclaredThrowableException(e);
            } catch (InvocationTargetException e) {
                throw new UndeclaredThrowableException(e);
            }
            return null;
        }, acc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bind(ObservableValue<? extends Boolean> observable) {
        if (observable == null) {
            throw new NullPointerException("Cannot bind to null");
        }

        if (!observable.equals(this.observable)) {
            unbind();
            set(observable.getValue());
            this.observable = observable;
            this.observable.addListener(listener);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unbind() {
        if (observable != null) {
            observable.removeListener(listener);
            observable = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBound() {
        return observable != null;
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
    public void addListener(ChangeListener<? super Boolean> listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeListener(ChangeListener<? super Boolean> listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addListener(InvalidationListener listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeListener(InvalidationListener listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fireValueChangedEvent() {
        ExpressionHelper.fireValueChangedEvent(helper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        descriptor.removeListener(listener);

    }

    /**
     * Returns a string representation of this {@code JavaBeanBooleanProperty} object.
     * @return a string representation of this {@code JavaBeanBooleanProperty} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("BooleanProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if ((name != null) && (!name.equals(""))) {
            result.append("name: ").append(name).append(", ");
        }
        if (isBound()) {
            result.append("bound, ");
        }
        result.append("value: ").append(get());
        result.append("]");
        return result.toString();
    }
}
