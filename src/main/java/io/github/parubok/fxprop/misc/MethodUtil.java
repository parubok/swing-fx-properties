package io.github.parubok.fxprop.misc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodUtil {
    public static Object invoke(Method method, Object bean, Object[] args) throws IllegalAccessException, InvocationTargetException {
        return method.invoke(bean, args);
    }
}
