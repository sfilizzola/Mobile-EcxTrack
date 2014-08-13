
package dev.sfilizzola.data.Utilities;


import java.math.BigDecimal;

public class Math {

    public static double round(double d, int decimalPlace) {

        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();

    }

    public static double truncate(Double d) {

        if (d > 0)
            return java.lang.Math.floor(d);
        else
            return java.lang.Math.ceil(d);

    }

}
