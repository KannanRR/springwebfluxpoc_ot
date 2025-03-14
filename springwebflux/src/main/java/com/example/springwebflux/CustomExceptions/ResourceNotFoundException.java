package com.example.springwebflux.CustomExceptions;

public class  ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
}
