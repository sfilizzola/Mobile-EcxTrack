
package dev.sfilizzola.data.Utilities;


import java.util.Date;

import org.joda.time.DateTime;

public class Primitives {

    public static boolean IsNullOrEmpty(java.lang.String str) {

        return (str == null || str.length() == 0);
    }

    public static long GetTicks(Date pDate) {

        DateTime vBaseDate = new DateTime(pDate);
        return (long)(vBaseDate.getYear() * 1e4 + vBaseDate.getMonthOfYear() * 1e2 + vBaseDate.getDayOfMonth());

    }

}
