package org.chamgin.framework.util;

import org.apache.commons.lang3.StringUtils;

public final class CastUtil {
    public static String castString(Object object) {
        return CastUtil.castString(object, "");
    }
    public static String castString(Object object, String defaultValue) {
        return object != null ? String.valueOf(object) : defaultValue;
    }

    public static double castDouble(Object object) {
        return CastUtil.castDouble(object, 0);
    }
    public static double castDouble(Object object, double defaultValue) {
        double result = defaultValue;
        if(object != null) {
            String strValue = castString(object);
            if(StringUtils.isNotEmpty(strValue)) {
                try {
                    result = Double.parseDouble(strValue);
                } catch ( NumberFormatException e) {
                    result = defaultValue;
                }
            }
        }
        return result;
    }

    public static long castLong(Object object) {
        return CastUtil.castLong(object, 0L);
    }
    public static long castLong(Object object, long defaultValue) {
        long result = defaultValue;
        if(object != null) {
            String strValue = CastUtil.castString(object);
            if(StringUtils.isNotEmpty(strValue)) {
                try{
                    result = Long.parseLong(strValue);
                } catch(NumberFormatException e) {
                    result = defaultValue;
                }
            }
        }
        return result;
    }

    public static int castInt(Object object) {
        return CastUtil.castInt(object, 0);
    }
    public static int castInt(Object object, int defaultValue) {
        int result = defaultValue;
        if(object != null) {
            String strValue = CastUtil.castString(object);
            if(StringUtils.isNotEmpty(strValue)) {
                try {
                    result = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    result = defaultValue;
                }
            }
        }
        return result;
    }

    public static boolean castBoolean(Object object) {
        return CastUtil.castBoolean(object, false);
    }
    public static boolean castBoolean(Object object, boolean defaultValue) {
        boolean result = defaultValue;
        if(object != null) {
            result = Boolean.parseBoolean(castString(object));
        }
        return result;
    }
}
