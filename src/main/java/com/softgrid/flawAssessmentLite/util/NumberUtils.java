package com.softgrid.flawAssessmentLite.util;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;

public class NumberUtils {

    private static Logger logger = Logger.getRootLogger();

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

    public static double findClosestValue(ArrayList<Double> array, Double targetNum) {
        int left = 0, right = 0;
        for (right = array.size() - 1; left != right; ) {
            int midIndex = (right + left) / 2;
            double midValue = array.get(midIndex);

            if (targetNum == midValue) {
                break;
            }
            if (targetNum > midValue) {
                left = midIndex;
            } else {
                right = midIndex;
            }
            if ((right - left) <= 1) {
                break;
            }
        }
        double rightnum = array.get(right);
        double leftnum = array.get(left);
        double ret = Math.abs((rightnum - leftnum) / 2) > Math.abs(rightnum - targetNum) ? rightnum : leftnum;
        logger.info("和要查找的数：" + targetNum + " 最接近的数是：" + ret);
        return ret;
    }

    /**
     * Added on 2018-07-24 by Chongyu
     *
     * @param al
     * @param target
     * @return
     */
    public static String findClosestValueFromStringAL(ArrayList<String> al, Double target) {
        double distance = Math.abs(Double.parseDouble(al.get(0)) - target);

        int idx = 0;
        for (int c = 1; c < al.size(); c++) {
            double cdistance = Math.abs(Double.parseDouble(al.get(c)) - target);
            if (cdistance < distance) {
                idx = c;
                distance = cdistance;
            }
        }

        String result = al.get(idx);
        logger.info("The closet number to: " + target + " is: " + result);

        return result;
    }

    static class SortBySize implements Comparator<Double> {
        @Override
        public int compare(Double o1, Double o2) {
            if (o1 > o2)
                return 1;
            return -1;
        }
    }

    public static double formatTwoData(double d) {
        try {
            BigDecimal bg = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP);

            return bg.doubleValue();
        } catch (Exception e) {
            return 0;
        }
    }

}
