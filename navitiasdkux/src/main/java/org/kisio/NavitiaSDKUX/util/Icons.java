package org.kisio.NavitiaSDKUX.util;

import org.kisio.NavitiaSDKUX.config.Configuration;

/**
 * NavitiaSDKUX_android
 *
 * Copyright \u00a9 2018 Kisio. All rights reserved.
 */

public class Icons {
    public static String fontString(String name) {
        if (Configuration.iconFontCodes.containsKey(name)) {
            return Configuration.iconFontCodes.get(name);
        } else {
            return "\uffff";
        }
    }
}
