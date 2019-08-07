package com.example.testinject;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by AndroidXJ on 2019/8/7.
 */

public class InjectUtil {
    public static void inject(Activity activity) {
        findViewById(activity);
        onClick(activity);
    }

    private static void findViewById(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            FindViewById annotation = field.getAnnotation(FindViewById.class);
            if (annotation != null) {
                int viewId = annotation.value();
                View view = activity.findViewById(viewId);
                try {
                    field.setAccessible(true);
                    field.set(activity, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void onClick(final Activity activity) {
        Class<?> clazz = activity.getClass();
        try {
            final Method method = clazz.getMethod("onClick", View.class);
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                int[] viewIds = onClick.value();
                for (int viewId : viewIds) {
                    final View view = activity.findViewById(viewId);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                method.setAccessible(true);
                                method.invoke(activity);
                            } catch (Exception e) {
                                e.printStackTrace();
                                try {
                                    method.invoke(activity, view);
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
