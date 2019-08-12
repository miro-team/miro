package miet.rooms.security;

class AuthenticationException extends RuntimeException {
    AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}

