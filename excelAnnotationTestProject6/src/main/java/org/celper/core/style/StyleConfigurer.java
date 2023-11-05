package org.celper.core.style;

/**
 * The interface Style configurer.
 *
 * @param <T> the type parameter
 */
public interface StyleConfigurer<T> {
    /**
     * Config.
     *
     * @param builder the builder
     */
    void config(T builder);
}