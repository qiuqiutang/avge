package com.example.lyfuelgas.common.adapter;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.LayoutRes;

/**
 * Created on 2018/3/19.
 *
 * @author QiuYan
 * @since 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Layout {
    /**
     * @return layout
     */
    @LayoutRes int value() default 0;

    /**
     * @return type
     */
    int type() default 0;

}