/*
 * Copyright (c) 2011, 2017, Oracle and/or its affiliates. All rights reserved.
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

import com.sun.swingfx.binding.BidirectionalBinding;
import swingfx.beans.binding.Bindings;
import swingfx.beans.value.ObservableValue;
import swingfx.beans.value.ObservableBooleanValue;
import swingfx.beans.value.WritableBooleanValue;
import org.swingfx.misc.Logging;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * This class provides a full implementation of a {@link swingfx.beans.property.Property} wrapping a
 * {@code boolean} value.
 * <p>
 * The value of a {@code BooleanProperty} can be get and set with {@link #get()},
 * {@link #getValue()}, {@link #set(boolean)}, and {@link #setValue(Boolean)}.
 * <p>
 * A property can be bound and unbound unidirectional with
 * {@link #bind(ObservableValue)} and {@link #unbind()}. Bidirectional bindings
 * can be created and removed with {@link #bindBidirectional(swingfx.beans.property.Property)} and
 * {@link #unbindBidirectional(swingfx.beans.property.Property)}.
 * <p>
 * The context of a {@code BooleanProperty} can be read with {@link #getBean()}
 * and {@link #getName()}.
 *
 * <p>
 * Note: setting or binding this property to a null value will set the property to "false". See {@link #setValue(java.lang.Boolean) }.
 *
 * @see ObservableBooleanValue
 * @see WritableBooleanValue
 * @see swingfx.beans.property.ReadOnlyBooleanProperty
 * @see swingfx.beans.property.Property
 *
 * @since JavaFX 2.0
 */
public abstract class BooleanProperty extends ReadOnlyBooleanProperty implements
        swingfx.beans.property.Property<Boolean>, WritableBooleanValue {

    /**
     * Sole constructor
     */
    public BooleanProperty() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(Boolean v) {
        if (v == null) {
            Logging.getLogger().fine("Attempt to set boolean property to null, using default value instead.");
            set(false);
        } else {
            set(v.booleanValue());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bindBidirectional(swingfx.beans.property.Property<Boolean> other) {
        Bindings.bindBidirectional(this, other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unbindBidirectional(swingfx.beans.property.Property<Boolean> other) {
        Bindings.unbindBidirectional(this, other);
    }

    /**
     * Returns a string representation of this {@code BooleanProperty} object.
     * @return a string representation of this {@code BooleanProperty} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder(
                "BooleanProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if ((name != null) && (!name.equals(""))) {
            result.append("name: ").append(name).append(", ");
        }
        result.append("value: ").append(get()).append("]");
        return result.toString();
    }

    /**
     * Returns a {@code BooleanProperty} that wraps a
     * {@link swingfx.beans.property.Property}. If the
     * {@code Property} is already a {@code BooleanProperty}, it
     * will be returned. Otherwise a new
     * {@code BooleanProperty} is created that is bound to
     * the {@code Property}.
     *
     * Note: null values in the source property will be interpreted as "false"
     *
     * @param property
     *            The source {@code Property}
     * @return A {@code BooleanProperty} that wraps the
     *         {@code Property} if necessary
     * @throws NullPointerException
     *             if {@code property} is {@code null}
     * @since JavaFX 8.0
     */
    public static BooleanProperty booleanProperty(final Property<Boolean> property) {
        if (property == null) {
            throw new NullPointerException("Property cannot be null");
        }
        return property instanceof BooleanProperty ? (BooleanProperty)property : new BooleanPropertyBase() {
            private final AccessControlContext acc = AccessController.getContext();
            {
                BidirectionalBinding.bind(this, property);
            }

            @Override
            public Object getBean() {
                return null; // Virtual property, no bean
            }

            @Override
            public String getName() {
                return property.getName();
            }

            @Override
            protected void finalize() throws Throwable {
                try {
                    AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
                        BidirectionalBinding.unbind(property, this);
                        return null;
                    }, acc);
                } finally {
                    super.finalize();
                }
            }
        };
    }

    /**
     * Creates an {@link swingfx.beans.property.ObjectProperty} that holds the value
     * of this {@code BooleanProperty}. If the
     * value of this {@code BooleanProperty} changes, the value of the
     * {@code ObjectProperty} will be updated automatically.
     *
     * @return the new {@code ObjectProperty}
     * @since JavaFX 8.0
     */
    @Override
    public ObjectProperty<Boolean> asObject() {
        return new ObjectPropertyBase<Boolean>() {
            private final AccessControlContext acc = AccessController.getContext();
            {
                BidirectionalBinding.bind(this, BooleanProperty.this);
            }

            @Override
            public Object getBean() {
                return null; // Virtual property, does not exist on a bean
            }

            @Override
            public String getName() {
                return BooleanProperty.this.getName();
            }

            @Override
            protected void finalize() throws Throwable {
                try {
                    AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
                        BidirectionalBinding.unbind(this, BooleanProperty.this);
                        return null;
                    }, acc);
                } finally {
                    super.finalize();
                }
            }

        };
    }
}
