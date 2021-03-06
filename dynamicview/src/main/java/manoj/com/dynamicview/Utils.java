package manoj.com.dynamicview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.util.TypedValue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

import manoj.com.dynamicview.property.PropertyValueType;

/**
 * Created by manoj on 19/09/16.
 */
public class Utils {
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static float parseDimenValue(String dimen) {
        return Float.parseFloat(dimen.substring(0, dimen.length() - 2));
    }

    public static float parseSpValue(String sp) {
        return Float.parseFloat(sp.substring(0, sp.length() - 2));
    }

    public static float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static float spToPx(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().getDisplayMetrics());
    }

    public static int convertColor(String color) {
        return Color.parseColor(color);
    }

    public static Bitmap convertBase64ToBitmap(String data) {
        try {
            InputStream stream = new ByteArrayInputStream(Base64.decode(data, Base64.DEFAULT));
            return BitmapFactory.decodeStream(stream);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getDataWithoutPrefix(PropertyValueType propertyDataType, String data) {
        switch (propertyDataType) {
            case ID:
                return data.replaceFirst("^@id/", "");
            case BASE64:
                return data.replaceFirst("^@base64/", "");
            case REFERENCE:
                return data.replaceFirst("^@ref/", "");
        }
        return data;
    }

    public static Object getValueInt(Class clazz, String varName) {

        java.lang.reflect.Field fieldRequested = null;

        try {
            fieldRequested = clazz.getField(varName);
            if (fieldRequested != null) {
                return fieldRequested.get(clazz);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean convertStringToBoolean(String data) {
        return Boolean.valueOf(data.toLowerCase());
    }

    /**
     * return the id (from the R.java autogenerated class) of the drawable that pass its name as argument
     */
    public static int getDrawableId(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    /**
     * return the id (from the R.java autogenerated class) of the string that pass its name as argument
     */
    public static int getStringId(Context context, String name) {
        return context.getResources().getIdentifier(name, "string", context.getPackageName());
    }

    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    public static boolean classExists(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }
}
