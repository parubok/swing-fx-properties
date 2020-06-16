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

package com.sun.swingfx.binding;

import org.swingfx.misc.Logging;
import swingfx.beans.Observable;
import swingfx.beans.WeakListener;
import swingfx.beans.property.DoubleProperty;
import swingfx.beans.value.ChangeListener;
import swingfx.beans.value.ObservableValue;
import swingfx.util.StringConverter;

import java.lang.ref.WeakReference;
import java.text.Format;
import java.text.ParseException;

public abstract class BidirectionalBinding<T> implements ChangeListener<T>, WeakListener {

    private static void checkParameters(Object property1, Object property2) {
        if ((property1 == null) || (property2 == null)) {
            throw new NullPointerException("Both properties must be specified.");
        }
        if (property1 == property2) {
            throw new IllegalArgumentException("Cannot bind property to itself");
        }
    }

    public static <T> BidirectionalBinding bind(swingfx.beans.property.Property<T> property1, swingfx.beans.property.Property<T> property2) {
        checkParameters(property1, property2);
        final BidirectionalBinding binding =
                ((property1 instanceof swingfx.beans.property.DoubleProperty) && (property2 instanceof swingfx.beans.property.DoubleProperty)) ?
                        new BidirectionalDoubleBinding((swingfx.beans.property.DoubleProperty) property1, (swingfx.beans.property.DoubleProperty) property2)
                : ((property1 instanceof swingfx.beans.property.FloatProperty) && (property2 instanceof swingfx.beans.property.FloatProperty)) ?
                        new BidirectionalFloatBinding((swingfx.beans.property.FloatProperty) property1, (swingfx.beans.property.FloatProperty) property2)
                : ((property1 instanceof swingfx.beans.property.IntegerProperty) && (property2 instanceof swingfx.beans.property.IntegerProperty)) ?
                        new BidirectionalIntegerBinding((swingfx.beans.property.IntegerProperty) property1, (swingfx.beans.property.IntegerProperty) property2)
                : ((property1 instanceof swingfx.beans.property.LongProperty) && (property2 instanceof swingfx.beans.property.LongProperty)) ?
                        new BidirectionalLongBinding((swingfx.beans.property.LongProperty) property1, (swingfx.beans.property.LongProperty) property2)
                : ((property1 instanceof swingfx.beans.property.BooleanProperty) && (property2 instanceof swingfx.beans.property.BooleanProperty)) ?
                        new BidirectionalBooleanBinding((swingfx.beans.property.BooleanProperty) property1, (swingfx.beans.property.BooleanProperty) property2)
                : new TypedGenericBidirectionalBinding<T>(property1, property2);
        property1.setValue(property2.getValue());
        property1.addListener(binding);
        property2.addListener(binding);
        return binding;
    }

    public static Object bind(swingfx.beans.property.Property<String> stringProperty, swingfx.beans.property.Property<?> otherProperty, Format format) {
        checkParameters(stringProperty, otherProperty);
        if (format == null) {
            throw new NullPointerException("Format cannot be null");
        }
        final StringConversionBidirectionalBinding<?> binding = new StringFormatBidirectionalBinding(stringProperty, otherProperty, format);
        stringProperty.setValue(format.format(otherProperty.getValue()));
        stringProperty.addListener(binding);
        otherProperty.addListener(binding);
        return binding;
    }

    public static <T> Object bind(swingfx.beans.property.Property<String> stringProperty, swingfx.beans.property.Property<T> otherProperty, StringConverter<T> converter) {
        checkParameters(stringProperty, otherProperty);
        if (converter == null) {
            throw new NullPointerException("Converter cannot be null");
        }
        final StringConversionBidirectionalBinding<T> binding = new StringConverterBidirectionalBinding<T>(stringProperty, otherProperty, converter);
        stringProperty.setValue(converter.toString(otherProperty.getValue()));
        stringProperty.addListener(binding);
        otherProperty.addListener(binding);
        return binding;
    }

    public static <T> void unbind(swingfx.beans.property.Property<T> property1, swingfx.beans.property.Property<T> property2) {
        checkParameters(property1, property2);
        final BidirectionalBinding binding = new UntypedGenericBidirectionalBinding(property1, property2);
        property1.removeListener(binding);
        property2.removeListener(binding);
    }

    public static void unbind(Object property1, Object property2) {
        checkParameters(property1, property2);
        final BidirectionalBinding binding = new UntypedGenericBidirectionalBinding(property1, property2);
        if (property1 instanceof ObservableValue) {
            ((ObservableValue) property1).removeListener(binding);
        }
        if (property2 instanceof Observable) {
            ((ObservableValue) property2).removeListener(binding);
        }
    }

    public static BidirectionalBinding bindNumber(swingfx.beans.property.Property<Integer> property1, swingfx.beans.property.IntegerProperty property2) {
        return bindNumber(property1, (swingfx.beans.property.Property<Number>)property2);
    }

    public static BidirectionalBinding bindNumber(swingfx.beans.property.Property<Long> property1, swingfx.beans.property.LongProperty property2) {
        return bindNumber(property1, (swingfx.beans.property.Property<Number>)property2);
    }

    public static BidirectionalBinding bindNumber(swingfx.beans.property.Property<Float> property1, swingfx.beans.property.FloatProperty property2) {
        return bindNumber(property1, (swingfx.beans.property.Property<Number>)property2);
    }

    public static BidirectionalBinding bindNumber(swingfx.beans.property.Property<Double> property1, swingfx.beans.property.DoubleProperty property2) {
        return bindNumber(property1, (swingfx.beans.property.Property<Number>)property2);
    }

    public static BidirectionalBinding bindNumber(swingfx.beans.property.IntegerProperty property1, swingfx.beans.property.Property<Integer> property2) {
        return bindNumberObject(property1, property2);
    }

    public static BidirectionalBinding bindNumber(swingfx.beans.property.LongProperty property1, swingfx.beans.property.Property<Long> property2) {
        return bindNumberObject(property1, property2);
    }

    public static BidirectionalBinding bindNumber(swingfx.beans.property.FloatProperty property1, swingfx.beans.property.Property<Float> property2) {
        return bindNumberObject(property1, property2);
    }

    public static BidirectionalBinding bindNumber(swingfx.beans.property.DoubleProperty property1, swingfx.beans.property.Property<Double> property2) {
        return bindNumberObject(property1, property2);
    }

    private static <T extends Number> BidirectionalBinding bindNumberObject(swingfx.beans.property.Property<Number> property1, swingfx.beans.property.Property<T> property2) {
        checkParameters(property1, property2);

        final BidirectionalBinding<Number> binding = new TypedNumberBidirectionalBinding<T>(property2, property1);

        property1.setValue(property2.getValue());
        property1.addListener(binding);
        property2.addListener(binding);
        return binding;
    }

    private static <T extends Number> BidirectionalBinding bindNumber(swingfx.beans.property.Property<T> property1, swingfx.beans.property.Property<Number> property2) {
        checkParameters(property1, property2);

        final BidirectionalBinding<Number> binding = new TypedNumberBidirectionalBinding<T>(property1, property2);

        property1.setValue((T)property2.getValue());
        property1.addListener(binding);
        property2.addListener(binding);
        return binding;
    }

    public static <T extends Number> void unbindNumber(swingfx.beans.property.Property<T> property1, swingfx.beans.property.Property<Number> property2) {
        checkParameters(property1, property2);
        final BidirectionalBinding binding = new UntypedGenericBidirectionalBinding(property1, property2);
        if (property1 instanceof ObservableValue) {
            ((ObservableValue) property1).removeListener(binding);
        }
        if (property2 instanceof Observable) {
            ((ObservableValue) property2).removeListener(binding);
        }
    }

    private final int cachedHashCode;

    private BidirectionalBinding(Object property1, Object property2) {
        cachedHashCode = property1.hashCode() * property2.hashCode();
    }

    protected abstract Object getProperty1();

    protected abstract Object getProperty2();

    @Override
    public int hashCode() {
        return cachedHashCode;
    }

    @Override
    public boolean wasGarbageCollected() {
        return (getProperty1() == null) || (getProperty2() == null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        final Object propertyA1 = getProperty1();
        final Object propertyA2 = getProperty2();
        if ((propertyA1 == null) || (propertyA2 == null)) {
            return false;
        }

        if (obj instanceof BidirectionalBinding) {
            final BidirectionalBinding otherBinding = (BidirectionalBinding) obj;
            final Object propertyB1 = otherBinding.getProperty1();
            final Object propertyB2 = otherBinding.getProperty2();
            if ((propertyB1 == null) || (propertyB2 == null)) {
                return false;
            }

            if (propertyA1 == propertyB1 && propertyA2 == propertyB2) {
                return true;
            }
            if (propertyA1 == propertyB2 && propertyA2 == propertyB1) {
                return true;
            }
        }
        return false;
    }

    private static class BidirectionalBooleanBinding extends BidirectionalBinding<Boolean> {
        private final WeakReference<swingfx.beans.property.BooleanProperty> propertyRef1;
        private final WeakReference<swingfx.beans.property.BooleanProperty> propertyRef2;
        private boolean updating = false;

        private BidirectionalBooleanBinding(swingfx.beans.property.BooleanProperty property1, swingfx.beans.property.BooleanProperty property2) {
            super(property1, property2);
            propertyRef1 = new WeakReference<swingfx.beans.property.BooleanProperty>(property1);
            propertyRef2 = new WeakReference<swingfx.beans.property.BooleanProperty>(property2);
        }

        @Override
        protected swingfx.beans.property.Property<Boolean> getProperty1() {
            return propertyRef1.get();
        }

        @Override
        protected swingfx.beans.property.Property<Boolean> getProperty2() {
            return propertyRef2.get();
        }

        @Override
        public void changed(ObservableValue<? extends Boolean> sourceProperty, Boolean oldValue, Boolean newValue) {
            if (!updating) {
                final swingfx.beans.property.BooleanProperty property1 = propertyRef1.get();
                final swingfx.beans.property.BooleanProperty property2 = propertyRef2.get();
                if ((property1 == null) || (property2 == null)) {
                    if (property1 != null) {
                        property1.removeListener(this);
                    }
                    if (property2 != null) {
                        property2.removeListener(this);
                    }
                } else {
                    try {
                        updating = true;
                        if (property1 == sourceProperty) {
                            property2.set(newValue);
                        } else {
                            property1.set(newValue);
                        }
                    } catch (RuntimeException e) {
                        try {
                            if (property1 == sourceProperty) {
                                property1.set(oldValue);
                            } else {
                                property2.set(oldValue);
                            }
                        } catch (Exception e2) {
                            e2.addSuppressed(e);
                            com.sun.swingfx.binding.BidirectionalBinding.unbind(property1, property2);
                            throw new RuntimeException(
                                "Bidirectional binding failed together with an attempt"
                                        + " to restore the source property to the previous value."
                                        + " Removing the bidirectional binding from properties " +
                                        property1 + " and " + property2, e2);
                        }
                        throw new RuntimeException(
                                "Bidirectional binding failed, setting to the previous value", e);
                    } finally {
                        updating = false;
                    }
                }
            }
        }
    }

    private static class BidirectionalDoubleBinding extends BidirectionalBinding<Number> {
        private final WeakReference<swingfx.beans.property.DoubleProperty> propertyRef1;
        private final WeakReference<swingfx.beans.property.DoubleProperty> propertyRef2;
        private boolean updating = false;

        private BidirectionalDoubleBinding(swingfx.beans.property.DoubleProperty property1, swingfx.beans.property.DoubleProperty property2) {
            super(property1, property2);
            propertyRef1 = new WeakReference<swingfx.beans.property.DoubleProperty>(property1);
            propertyRef2 = new WeakReference<swingfx.beans.property.DoubleProperty>(property2);
        }

        @Override
        protected swingfx.beans.property.Property<Number> getProperty1() {
            return propertyRef1.get();
        }

        @Override
        protected swingfx.beans.property.Property<Number> getProperty2() {
            return propertyRef2.get();
        }

        @Override
        public void changed(ObservableValue<? extends Number> sourceProperty, Number oldValue, Number newValue) {
            if (!updating) {
                final swingfx.beans.property.DoubleProperty property1 = propertyRef1.get();
                final DoubleProperty property2 = propertyRef2.get();
                if ((property1 == null) || (property2 == null)) {
                    if (property1 != null) {
                        property1.removeListener(this);
                    }
                    if (property2 != null) {
                        property2.removeListener(this);
                    }
                } else {
                    try {
                        updating = true;
                        if (property1 == sourceProperty) {
                            property2.set(newValue.doubleValue());
                        } else {
                            property1.set(newValue.doubleValue());
                        }
                    } catch (RuntimeException e) {
                        try {
                            if (property1 == sourceProperty) {
                                property1.set(oldValue.doubleValue());
                            } else {
                                property2.set(oldValue.doubleValue());
                            }
                        } catch (Exception e2) {
                            e2.addSuppressed(e);
                            com.sun.swingfx.binding.BidirectionalBinding.unbind(property1, property2);
                            throw new RuntimeException(
                                "Bidirectional binding failed together with an attempt"
                                        + " to restore the source property to the previous value."
                                        + " Removing the bidirectional binding from properties " +
                                        property1 + " and " + property2, e2);
                        }
                        throw new RuntimeException(
                                        "Bidirectional binding failed, setting to the previous value", e);
                    } finally {
                        updating = false;
                    }
                }
            }
        }
    }

    private static class BidirectionalFloatBinding extends BidirectionalBinding<Number> {
        private final WeakReference<swingfx.beans.property.FloatProperty> propertyRef1;
        private final WeakReference<swingfx.beans.property.FloatProperty> propertyRef2;
        private boolean updating = false;

        private BidirectionalFloatBinding(swingfx.beans.property.FloatProperty property1, swingfx.beans.property.FloatProperty property2) {
            super(property1, property2);
            propertyRef1 = new WeakReference<swingfx.beans.property.FloatProperty>(property1);
            propertyRef2 = new WeakReference<swingfx.beans.property.FloatProperty>(property2);
        }

        @Override
        protected swingfx.beans.property.Property<Number> getProperty1() {
            return propertyRef1.get();
        }

        @Override
        protected swingfx.beans.property.Property<Number> getProperty2() {
            return propertyRef2.get();
        }

        @Override
        public void changed(ObservableValue<? extends Number> sourceProperty, Number oldValue, Number newValue) {
            if (!updating) {
                final swingfx.beans.property.FloatProperty property1 = propertyRef1.get();
                final swingfx.beans.property.FloatProperty property2 = propertyRef2.get();
                if ((property1 == null) || (property2 == null)) {
                    if (property1 != null) {
                        property1.removeListener(this);
                    }
                    if (property2 != null) {
                        property2.removeListener(this);
                    }
                } else {
                    try {
                        updating = true;
                        if (property1 == sourceProperty) {
                            property2.set(newValue.floatValue());
                        } else {
                            property1.set(newValue.floatValue());
                        }
                    } catch (RuntimeException e) {
                        try {
                            if (property1 == sourceProperty) {
                                property1.set(oldValue.floatValue());
                            } else {
                                property2.set(oldValue.floatValue());
                            }
                        } catch (Exception e2) {
                            e2.addSuppressed(e);
                            com.sun.swingfx.binding.BidirectionalBinding.unbind(property1, property2);
                            throw new RuntimeException(
                                "Bidirectional binding failed together with an attempt"
                                        + " to restore the source property to the previous value."
                                        + " Removing the bidirectional binding from properties " +
                                        property1 + " and " + property2, e2);
                        }
                        throw new RuntimeException(
                                "Bidirectional binding failed, setting to the previous value", e);
                    } finally {
                        updating = false;
                    }
                }
            }
        }
    }

    private static class BidirectionalIntegerBinding extends BidirectionalBinding<Number>{
        private final WeakReference<swingfx.beans.property.IntegerProperty> propertyRef1;
        private final WeakReference<swingfx.beans.property.IntegerProperty> propertyRef2;
        private boolean updating = false;

        private BidirectionalIntegerBinding(swingfx.beans.property.IntegerProperty property1, swingfx.beans.property.IntegerProperty property2) {
            super(property1, property2);
            propertyRef1 = new WeakReference<swingfx.beans.property.IntegerProperty>(property1);
            propertyRef2 = new WeakReference<swingfx.beans.property.IntegerProperty>(property2);
        }

        @Override
        protected swingfx.beans.property.Property<Number> getProperty1() {
            return propertyRef1.get();
        }

        @Override
        protected swingfx.beans.property.Property<Number> getProperty2() {
            return propertyRef2.get();
        }

        @Override
        public void changed(ObservableValue<? extends Number> sourceProperty, Number oldValue, Number newValue) {
            if (!updating) {
                final swingfx.beans.property.IntegerProperty property1 = propertyRef1.get();
                final swingfx.beans.property.IntegerProperty property2 = propertyRef2.get();
                if ((property1 == null) || (property2 == null)) {
                    if (property1 != null) {
                        property1.removeListener(this);
                    }
                    if (property2 != null) {
                        property2.removeListener(this);
                    }
                } else {
                    try {
                        updating = true;
                        if (property1 == sourceProperty) {
                            property2.set(newValue.intValue());
                        } else {
                            property1.set(newValue.intValue());
                        }
                    } catch (RuntimeException e) {
                        try {
                            if (property1 == sourceProperty) {
                                property1.set(oldValue.intValue());
                            } else {
                                property2.set(oldValue.intValue());
                            }
                        } catch (Exception e2) {
                            e2.addSuppressed(e);
                            com.sun.swingfx.binding.BidirectionalBinding.unbind(property1, property2);
                            throw new RuntimeException(
                                "Bidirectional binding failed together with an attempt"
                                        + " to restore the source property to the previous value."
                                        + " Removing the bidirectional binding from properties " +
                                        property1 + " and " + property2, e2);
                        }
                        throw new RuntimeException(
                                        "Bidirectional binding failed, setting to the previous value", e);
                    } finally {
                        updating = false;
                    }
                }
            }
        }
    }

    private static class BidirectionalLongBinding extends BidirectionalBinding<Number> {
        private final WeakReference<swingfx.beans.property.LongProperty> propertyRef1;
        private final WeakReference<swingfx.beans.property.LongProperty> propertyRef2;
        private boolean updating = false;

        private BidirectionalLongBinding(swingfx.beans.property.LongProperty property1, swingfx.beans.property.LongProperty property2) {
            super(property1, property2);
            propertyRef1 = new WeakReference<swingfx.beans.property.LongProperty>(property1);
            propertyRef2 = new WeakReference<swingfx.beans.property.LongProperty>(property2);
        }

        @Override
        protected swingfx.beans.property.Property<Number> getProperty1() {
            return propertyRef1.get();
        }

        @Override
        protected swingfx.beans.property.Property<Number> getProperty2() {
            return propertyRef2.get();
        }

        @Override
        public void changed(ObservableValue<? extends Number> sourceProperty, Number oldValue, Number newValue) {
            if (!updating) {
                final swingfx.beans.property.LongProperty property1 = propertyRef1.get();
                final swingfx.beans.property.LongProperty property2 = propertyRef2.get();
                if ((property1 == null) || (property2 == null)) {
                    if (property1 != null) {
                        property1.removeListener(this);
                    }
                    if (property2 != null) {
                        property2.removeListener(this);
                    }
                } else {
                    try {
                        updating = true;
                        if (property1 == sourceProperty) {
                            property2.set(newValue.longValue());
                        } else {
                            property1.set(newValue.longValue());
                        }
                    } catch (RuntimeException e) {
                        try {
                            if (property1 == sourceProperty) {
                                property1.set(oldValue.longValue());
                            } else {
                                property2.set(oldValue.longValue());
                            }
                        } catch (Exception e2) {
                            e2.addSuppressed(e);
                            com.sun.swingfx.binding.BidirectionalBinding.unbind(property1, property2);
                            throw new RuntimeException(
                                "Bidirectional binding failed together with an attempt"
                                        + " to restore the source property to the previous value."
                                        + " Removing the bidirectional binding from properties " +
                                        property1 + " and " + property2, e2);
                        }
                        throw new RuntimeException(
                                "Bidirectional binding failed, setting to the previous value", e);
                    } finally {
                        updating = false;
                    }
                }
            }
        }
    }

    private static class TypedGenericBidirectionalBinding<T> extends BidirectionalBinding<T> {
        private final WeakReference<swingfx.beans.property.Property<T>> propertyRef1;
        private final WeakReference<swingfx.beans.property.Property<T>> propertyRef2;
        private boolean updating = false;

        private TypedGenericBidirectionalBinding(swingfx.beans.property.Property<T> property1, swingfx.beans.property.Property<T> property2) {
            super(property1, property2);
            propertyRef1 = new WeakReference<swingfx.beans.property.Property<T>>(property1);
            propertyRef2 = new WeakReference<swingfx.beans.property.Property<T>>(property2);
        }

        @Override
        protected swingfx.beans.property.Property<T> getProperty1() {
            return propertyRef1.get();
        }

        @Override
        protected swingfx.beans.property.Property<T> getProperty2() {
            return propertyRef2.get();
        }

        @Override
        public void changed(ObservableValue<? extends T> sourceProperty, T oldValue, T newValue) {
            if (!updating) {
                final swingfx.beans.property.Property<T> property1 = propertyRef1.get();
                final swingfx.beans.property.Property<T> property2 = propertyRef2.get();
                if ((property1 == null) || (property2 == null)) {
                    if (property1 != null) {
                        property1.removeListener(this);
                    }
                    if (property2 != null) {
                        property2.removeListener(this);
                    }
                } else {
                    try {
                        updating = true;
                        if (property1 == sourceProperty) {
                            property2.setValue(newValue);
                        } else {
                            property1.setValue(newValue);
                        }
                    } catch (RuntimeException e) {
                        try {
                            if (property1 == sourceProperty) {
                                property1.setValue(oldValue);
                            } else {
                                property2.setValue(oldValue);
                            }
                        } catch (Exception e2) {
                            e2.addSuppressed(e);
                            com.sun.swingfx.binding.BidirectionalBinding.unbind(property1, property2);
                            throw new RuntimeException(
                                "Bidirectional binding failed together with an attempt"
                                        + " to restore the source property to the previous value."
                                        + " Removing the bidirectional binding from properties " +
                                        property1 + " and " + property2, e2);
                        }
                        throw new RuntimeException(
                                "Bidirectional binding failed, setting to the previous value", e);
                    } finally {
                        updating = false;
                    }
                }
            }
        }
    }

    private static class TypedNumberBidirectionalBinding<T extends Number> extends BidirectionalBinding<Number> {
        private final WeakReference<swingfx.beans.property.Property<T>> propertyRef1;
        private final WeakReference<swingfx.beans.property.Property<Number>> propertyRef2;
        private boolean updating = false;

        private TypedNumberBidirectionalBinding(swingfx.beans.property.Property<T> property1, swingfx.beans.property.Property<Number> property2) {
            super(property1, property2);
            propertyRef1 = new WeakReference<swingfx.beans.property.Property<T>>(property1);
            propertyRef2 = new WeakReference<swingfx.beans.property.Property<Number>>(property2);
        }

        @Override
        protected swingfx.beans.property.Property<T> getProperty1() {
            return propertyRef1.get();
        }

        @Override
        protected swingfx.beans.property.Property<Number> getProperty2() {
            return propertyRef2.get();
        }

        @Override
        public void changed(ObservableValue<? extends Number> sourceProperty, Number oldValue, Number newValue) {
            if (!updating) {
                final swingfx.beans.property.Property<T> property1 = propertyRef1.get();
                final swingfx.beans.property.Property<Number> property2 = propertyRef2.get();
                if ((property1 == null) || (property2 == null)) {
                    if (property1 != null) {
                        property1.removeListener(this);
                    }
                    if (property2 != null) {
                        property2.removeListener(this);
                    }
                } else {
                    try {
                        updating = true;
                        if (property1 == sourceProperty) {
                            property2.setValue(newValue);
                        } else {
                            property1.setValue((T)newValue);
                        }
                    } catch (RuntimeException e) {
                        try {
                            if (property1 == sourceProperty) {
                                property1.setValue((T)oldValue);
                            } else {
                                property2.setValue(oldValue);
                            }
                        } catch (Exception e2) {
                            e2.addSuppressed(e);
                            com.sun.swingfx.binding.BidirectionalBinding.unbind(property1, property2);
                            throw new RuntimeException(
                                "Bidirectional binding failed together with an attempt"
                                        + " to restore the source property to the previous value."
                                        + " Removing the bidirectional binding from properties " +
                                        property1 + " and " + property2, e2);
                        }
                        throw new RuntimeException(
                                        "Bidirectional binding failed, setting to the previous value", e);
                    } finally {
                        updating = false;
                    }
                }
            }
        }
    }

    private static class UntypedGenericBidirectionalBinding extends BidirectionalBinding<Object> {

        private final Object property1;
        private final Object property2;

        public UntypedGenericBidirectionalBinding(Object property1, Object property2) {
            super(property1, property2);
            this.property1 = property1;
            this.property2 = property2;
        }

        @Override
        protected Object getProperty1() {
            return property1;
        }

        @Override
        protected Object getProperty2() {
            return property2;
        }

        @Override
        public void changed(ObservableValue<? extends Object> sourceProperty, Object oldValue, Object newValue) {
            throw new RuntimeException("Should not reach here");
        }
    }

    public abstract static class StringConversionBidirectionalBinding<T> extends BidirectionalBinding<Object> {

        private final WeakReference<swingfx.beans.property.Property<String>> stringPropertyRef;
        private final WeakReference<swingfx.beans.property.Property<T>> otherPropertyRef;
        private boolean updating;

        public StringConversionBidirectionalBinding(swingfx.beans.property.Property<String> stringProperty, swingfx.beans.property.Property<T> otherProperty) {
            super(stringProperty, otherProperty);
            stringPropertyRef = new WeakReference<swingfx.beans.property.Property<String>>(stringProperty);
            otherPropertyRef = new WeakReference<swingfx.beans.property.Property<T>>(otherProperty);
        }

        protected abstract String toString(T value);

        protected abstract T fromString(String value) throws ParseException;

        @Override
        protected Object getProperty1() {
            return stringPropertyRef.get();
        }

        @Override
        protected Object getProperty2() {
            return otherPropertyRef.get();
        }

        @Override
        public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
            if (!updating) {
                final swingfx.beans.property.Property<String> property1 = stringPropertyRef.get();
                final swingfx.beans.property.Property<T> property2 = otherPropertyRef.get();
                if ((property1 == null) || (property2 == null)) {
                    if (property1 != null) {
                        property1.removeListener(this);
                    }
                    if (property2 != null) {
                        property2.removeListener(this);
                    }
                } else {
                    try {
                        updating = true;
                        if (property1 == observable) {
                            try {
                                property2.setValue(fromString(property1.getValue()));
                            } catch (Exception e) {
                                Logging.getLogger().warning("Exception while parsing String in bidirectional binding");
                                property2.setValue(null);
                            }
                        } else {
                            try {
                                property1.setValue(toString(property2.getValue()));
                            } catch (Exception e) {
                                Logging.getLogger().warning("Exception while converting Object to String in bidirectional binding");
                                property1.setValue("");
                            }
                        }
                    } finally {
                        updating = false;
                    }
                }
            }
        }
    }

    private static class StringFormatBidirectionalBinding extends StringConversionBidirectionalBinding {

        private final Format format;

        @SuppressWarnings("unchecked")
        public StringFormatBidirectionalBinding(swingfx.beans.property.Property<String> stringProperty, swingfx.beans.property.Property<?> otherProperty, Format format) {
            super(stringProperty, otherProperty);
            this.format = format;
        }

        @Override
        protected String toString(Object value) {
            return format.format(value);
        }

        @Override
        protected Object fromString(String value) throws ParseException {
            return format.parseObject(value);
        }
    }

    private static class StringConverterBidirectionalBinding<T> extends StringConversionBidirectionalBinding<T> {

        private final StringConverter<T> converter;

        public StringConverterBidirectionalBinding(swingfx.beans.property.Property<String> stringProperty, swingfx.beans.property.Property<T> otherProperty, StringConverter<T> converter) {
            super(stringProperty, otherProperty);
            this.converter = converter;
        }

        @Override
        protected String toString(T value) {
            return converter.toString(value);
        }

        @Override
        protected T fromString(String value) throws ParseException {
            return converter.fromString(value);
        }
    }
}
