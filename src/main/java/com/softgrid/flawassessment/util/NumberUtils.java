package com.softgrid.flawassessment.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {

    public static double stringToDouble(String s) {
        if (StringUtils.isEmpty(s)) {
            return 0;
        } else {
            try {
                return Double.parseDouble(s);
            } catch (Exception e) {
                return 0;
            }
        }
    }

    public static double formatDouble(double d) {
        try {
            BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);

            return bg.doubleValue();
        } catch (Exception e) {
            return 0;
        }
    }

}
