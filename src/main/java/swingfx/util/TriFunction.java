package swingfx.util;

@FunctionalInterface
public interface TriFunction<D, F, G, R> {
    R apply(D a1, F a2, G a3);
}
