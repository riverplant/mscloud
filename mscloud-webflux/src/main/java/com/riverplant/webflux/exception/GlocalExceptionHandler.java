package com.riverplant.webflux.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@RestControllerAdvice
public class GlocalExceptionHandler {

    @ExceptionHandler(ArithmeticException.class)
    public String error(ArithmeticException exception) {

        System.out.println("發生了數學運算一場" + exception);
        return "發生了數學運算一場" + exception;
    }

    /**
     * 返回problemDetail
     * @param exception
     * @return
     */
    @ExceptionHandler(UserPrincipalNotFoundException.class)
    public ProblemDetail handleUserPrincipalNotFound(UserPrincipalNotFoundException exception) {

        /**
         * {
         *   "type": "about:blank",
         *   "title": "User not found",
         *   "status": 404,
         *   "detail": "User with ID 42 does not exist",
         *   "instance": "/user/42"
         * }
         */
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("User not found");
        problem.setDetail("User with name  " + exception.getName() + "does not exist. ");
        return problem;
    }
}
