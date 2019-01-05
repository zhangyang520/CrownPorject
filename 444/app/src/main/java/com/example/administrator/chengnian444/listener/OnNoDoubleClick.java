package com.example.administrator.chengnian444.listener;

import android.view.View;
import butterknife.internal.ListenerClass;
import butterknife.internal.ListenerMethod;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * @author Administrator
 * @Title ${name}
 * @ProjectName 444
 * @Description: TODO
 * @date 2019/1/520:07
 */
@Target(METHOD)
@Retention(CLASS)
@ListenerClass(
        targetType = "android.view.View",
        setter = "setOnClickListener",
        type = "com.example.administrator.chengnian444.listener.NoDoubleClickListener",
        method = @ListenerMethod(
                name = "onNoDoubleClick",
                parameters = "android.view.View"
        )
)
@Inherited()
public @interface OnNoDoubleClick {
    int[] value() default { View.NO_ID };
}
