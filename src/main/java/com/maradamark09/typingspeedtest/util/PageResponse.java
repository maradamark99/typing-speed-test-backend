package com.maradamark09.typingspeedtest.util;

import java.util.Collection;

public record PageResponse<T>(
		Collection<T> content,
		int totalPages,
		int pageSize,
		int currentPage,
		boolean isLastPage,
		boolean isFirstPage) {
}
