package io.dsub.feedapispring.exceptions;

import javassist.NotFoundException;

public class CustomNotFoundException extends NotFoundException {

    public CustomNotFoundException(Long id, String className) {
        super(className + " of id " + id + " not found");
    }

    public CustomNotFoundException(Long id, String className, Exception e) {
        super(className + " of id " + id + " not found", e);
    }
}
