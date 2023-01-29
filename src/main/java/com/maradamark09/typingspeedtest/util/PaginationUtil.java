package com.maradamark09.typingspeedtest.util;

public class PaginationUtil {

    public final static String DEFAULT_PAGE = "0";
    public final static String DEFAULT_AMOUNT = "10";

    private static Long calculateMaxPageCount(Long totalAmount, Integer amount) {
        return (long) Math.ceil(totalAmount.doubleValue() / amount);
    }

    public static boolean isPageValid(Integer page, Long totalAmount, Integer amount) {
        return page >= 0 && page <= calculateMaxPageCount(totalAmount, amount);

    }

    public static boolean isAmountValid(Integer amount, Long totalAmount) {
         return amount >= 0 || amount <= totalAmount;
    }


}
