package pl.edu.uj.tcs.quizhub.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edu.uj.tcs.quizhub.exception.UserAccountException;
import pl.edu.uj.tcs.quizhub.models.ExceptionModel;

@ControllerAdvice
public class UserAccountAdvice {
    @ResponseBody
    @ExceptionHandler(UserAccountException.class)
    public ResponseEntity<ExceptionModel> handleUserAccountException(ExceptionModel response) {
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}