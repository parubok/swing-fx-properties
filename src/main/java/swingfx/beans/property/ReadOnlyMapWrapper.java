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

package swingfx.beans.property;

import swingfx.collections.ObservableMap;
import static swingfx.collections.MapChangeListener.Change;

/**
 * This class provides a convenient class to define read-only properties. It
 * creates two properties that are synchronized. One property is read-only
 * and can be passed to external users. The other property is read- and
 * writable and should be used internally only.
 *
 * @since JavaFX 2.1
 */
public class ReadOnlyMapWrapper<K, V> extends SimpleMapProperty<K, V> {

    private ReadOnlyPropertyImpl readOnlyProperty;

    /**
     * The constructor of {@code ReadOnlyMapWrapper}
     */
    public ReadOnlyMapWrapper() {
    }

    /**
     * The constructor of {@code ReadOnlyMapWrapper}
     *
     * @param initialValue
     *            the initial value of the wrapped value
     */
    public ReadOnlyMapWrapper(ObservableMap<K, V> initialValue) {
        super(initialValue);
    }

    /**
     * The constructor of {@code ReadOnlyMapWrapper}
     *
     * @param bean
     *            the bean of this {@code ReadOnlyMapWrapper}
     * @param name
     *            the name of this {@code ReadOnlyMapWrapper}
     */
    public ReadOnlyMapWrapper(Object bean, String name) {
        super(bean, name);
    }

    /**
     * The constructor of {@code ReadOnlyMapWrapper}
     *
     * @param bean
     *            the bean of this {@code ReadOnlyMapWrapper}
     * @param name
     *            the name of this {@code ReadOnlyMapWrapper}
     * @param initialValue
     *            the initial value of the wrapped value
     */
    public ReadOnlyMapWrapper(Object bean, String name,
                              ObservableMap<K, V> initialValue) {
        super(bean, name, initialValue);
    }

    /**
     * Returns the readonly property, that is synchronized with this
     * {@code ReadOnlyMapWrapper}.
     *
     * @return the readonly property
     */
    public ReadOnlyMapProperty<K, V> getReadOnlyProperty() {
        if (readOnlyProperty == null) {
            readOnlyProperty = new ReadOnlyPropertyImpl();
        }
        return readOnlyProperty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fireValueChangedEvent() {
        super.fireValueChangedEvent();
        if (readOnlyProperty != null) {
            readOnlyProperty.fireValueChangedEvent();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fireValueChangedEvent(Change<? extends K, ? extends V> change) {
        super.fireValueChangedEvent(change);
        if (readOnlyProperty != null) {
            readOnlyProperty.fireValueChangedEvent(change);
        }
    }

    private class ReadOnlyPropertyImpl extends ReadOnlyMapPropertyBase<K, V> {

        @Override
        public ObservableMap<K, V> get() {
            return ReadOnlyMapWrapper.this.get();
        }

        @Override
        public Object getBean() {
            return ReadOnlyMapWrapper.this.getBean();
        }

        @Override
        public String getName() {
            return ReadOnlyMapWrapper.this.getName();
        }

        @Override
        public ReadOnlyIntegerProperty sizeProperty() {
            return ReadOnlyMapWrapper.this.sizeProperty();
        }

        @Override
        public ReadOnlyBooleanProperty emptyProperty() {
            return ReadOnlyMapWrapper.this.emptyProperty();
        }
    }
}
