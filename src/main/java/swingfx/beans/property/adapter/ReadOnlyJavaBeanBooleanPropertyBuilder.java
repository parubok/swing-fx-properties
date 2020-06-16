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

package swingfx.beans.property.adapter;

import com.sun.swingfx.property.adapter.ReadOnlyJavaBeanPropertyBuilderHelper;
import com.sun.swingfx.property.adapter.ReadOnlyPropertyDescriptor;

import java.lang.reflect.Method;

/**
 * A {@code ReadOnlyJavaBeanBooleanPropertyBuilder} can be used to create
 * {@link swingfx.beans.property.adapter.ReadOnlyJavaBeanBooleanProperty ReadOnlyJavaBeanBooleanProperties}. To create
 * a {@code ReadOnlyJavaBeanBooleanProperty} one first has to call {@link #create()}
 * to generate a builder, set the required properties, and then one can
 * call {@link #build()} to generate the property.
 * <p>
 * Not all properties of a builder have to specified, there are several
 * combinations possible. As a minimum the {@link #name(java.lang.String)} of
 * the property and the {@link #bean(java.lang.Object)} have to be specified.
 * If the name of the getter follows the conventions, this is sufficient.
 * Otherwise it is possible to specify an alternative name for the getter
 * ({@link #getter(java.lang.String)}) or
 * the getter {@code Methods} directly ({@link #getter(java.lang.reflect.Method)}).
 * <p>
 * All methods to change properties return a reference to this builder, to enable
 * method chaining.
 * <p>
 * If you have to generate adapters for the same property of several instances
 * of the same class, you can reuse a {@code ReadOnlyJavaBeanBooleanPropertyBuilder}.
 * by switching the Java Bean instance (with {@link #bean(java.lang.Object)} and
 * calling {@link #build()}.
 *
 * @see swingfx.beans.property.adapter.ReadOnlyJavaBeanBooleanProperty
 * @since JavaFX 2.1
 */
public final class ReadOnlyJavaBeanBooleanPropertyBuilder {

    private final ReadOnlyJavaBeanPropertyBuilderHelper helper = new ReadOnlyJavaBeanPropertyBuilderHelper();

    /**
     * Create a new instance of {@code ReadOnlyJavaBeanBooleanPropertyBuilder}
     *
     * @return the new {@code ReadOnlyJavaBeanBooleanPropertyBuilder}
     */
    public static ReadOnlyJavaBeanBooleanPropertyBuilder create() {
        return new ReadOnlyJavaBeanBooleanPropertyBuilder();
    }

    /**
     * Generate a new {@link swingfx.beans.property.adapter.ReadOnlyJavaBeanBooleanProperty} with the current settings.
     *
     * @return the new {@code ReadOnlyJavaBeanBooleanProperty}
     * @throws NoSuchMethodException if the settings were not sufficient to find
     * the getter of the Java Bean property
     * @throws IllegalArgumentException if the Java Bean property is not of type
     * {@code boolean} or {@code Boolean}
     */
    public swingfx.beans.property.adapter.ReadOnlyJavaBeanBooleanProperty build() throws NoSuchMethodException {
        final ReadOnlyPropertyDescriptor descriptor = helper.getDescriptor();
        if (!boolean.class.equals(descriptor.getType()) && !Boolean.class.equals(descriptor.getType())) {
            throw new IllegalArgumentException("Not a boolean property");
        }
        return new ReadOnlyJavaBeanBooleanProperty(descriptor, helper.getBean());
    }

    /**
     * Set the name of the property
     *
     * @param name the name of the property
     * @return a reference to this builder to enable method chaining
     */
    public ReadOnlyJavaBeanBooleanPropertyBuilder name(String name) {
        helper.name(name);
        return this;
    }

    /**
     * Set the Java Bean instance the adapter should connect to
     *
     * @param bean the Java Bean instance
     * @return a reference to this builder to enable method chaining
     */
    public ReadOnlyJavaBeanBooleanPropertyBuilder bean(Object bean) {
        helper.bean(bean);
        return this;
    }

    /**
     * Set the Java Bean class in which the getter should be searched.
     * This can be useful, if the builder should generate adapters for several
     * Java Beans of different types.
     *
     * @param beanClass the Java Bean class
     * @return a reference to this builder to enable method chaining
     */
    public ReadOnlyJavaBeanBooleanPropertyBuilder beanClass(Class<?> beanClass) {
        helper.beanClass(beanClass);
        return this;
    }

    /**
     * Set an alternative name for the getter. This can be omitted, if the
     * name of the getter follows Java Bean naming conventions.
     *
     * @param getter the alternative name of the getter
     * @return a reference to this builder to enable method chaining
     */
    public ReadOnlyJavaBeanBooleanPropertyBuilder getter(String getter) {
        helper.getterName(getter);
        return this;
    }

    /**
     * Set the getter method directly. This can be omitted, if the
     * name of the getter follows Java Bean naming conventions.
     *
     * @param getter the getter
     * @return a reference to this builder to enable method chaining
     */
    public ReadOnlyJavaBeanBooleanPropertyBuilder getter(Method getter) {
        helper.getter(getter);
        return this;
    }
}
