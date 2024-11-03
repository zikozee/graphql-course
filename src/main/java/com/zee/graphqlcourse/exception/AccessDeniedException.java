package com.zee.graphqlcourse.exception;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 03 Nov, 2024
 */

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException() {
        super();
    }

    public AccessDeniedException(String message) {
        super(message);
    }
}
