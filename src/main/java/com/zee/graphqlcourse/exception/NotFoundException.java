package com.zee.graphqlcourse.exception;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 03 Nov, 2024
 */

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }
}
