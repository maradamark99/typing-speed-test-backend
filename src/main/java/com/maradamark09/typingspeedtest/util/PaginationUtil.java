package com.maradamark09.typingspeedtest.util;

import java.util.ArrayList;
import java.util.List;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class PaginationUtil {

    public final static String DEFAULT_PAGE = "0";
    public final static String DEFAULT_AMOUNT = "10";

    private static long calculateMaxPageCount(Long totalAmount, Integer amount) {
        return (long) Math.ceil(totalAmount.doubleValue() / amount);
    }

    public static boolean isPageValid(Integer page, Long totalAmount, Integer amount) {
        return page >= 0 && page <= calculateMaxPageCount(totalAmount, amount);

    }

    public static boolean isAmountValid(Integer amount, Long totalAmount) {
        return amount >= 0 || amount <= totalAmount;
    }

    public static PageRequest convertPageableToPageRequest(Pageable pageable) {
        if (pageable.getSort() != null) {
            return PageRequest.of(pageable.getPage(), pageable.getSize(), Sort.by(getListOfOrders(pageable.getSort())));
        }
        return PageRequest.of(pageable.getPage(), pageable.getSize());
    }

    private static List<Order> getListOfOrders(List<String> sort) {
        var orders = new ArrayList<Order>();
        if (sort.size() == 2 && getSortDirection(sort.get(1)) != null) {
            orders.add(new Order(getSortDirection(sort.get(1)), sort.get(0)));
        } else {
            for (var s : sort) {
                if (s.contains(",")) {
                    var currentSort = s.split(",");
                    orders.add(new Order(getSortDirection(currentSort[1]), currentSort[0]));
                } else if (getSortDirection(s) == null) {
                    orders.add(new Order(Sort.Direction.ASC, s));
                }
            }
        }
        return orders;
    }

    private static Sort.Direction getSortDirection(String direction) {
        if (direction.equalsIgnoreCase("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equalsIgnoreCase("desc")) {
            return Sort.Direction.DESC;
        }
        return null;
    }

    private PaginationUtil() {

    }

}
