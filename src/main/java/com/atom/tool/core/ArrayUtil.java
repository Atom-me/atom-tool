package com.atom.tool.core;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author Atom
 */
public class ArrayUtil {
    public static boolean isNotEmpty(Object[] objArray) {
        return ArrayUtils.isNotEmpty(objArray);
    }

    public static boolean isEmpty(Object[] objArray) {
        return ArrayUtils.isEmpty(objArray);
    }
}
