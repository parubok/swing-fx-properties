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

package swingfx.beans.value;

/**
 * A tagging interface that marks all sub-interfaces of {@link swingfx.beans.value.WritableValue}
 * that wrap a number.
 *
 * @see swingfx.beans.value.WritableValue
 * @see WritableDoubleValue
 * @see WritableFloatValue
 * @see WritableIntegerValue
 * @see WritableLongValue
 *
 *
 * @since JavaFX 2.0
 */
public interface WritableNumberValue extends WritableValue<Number> {

}
