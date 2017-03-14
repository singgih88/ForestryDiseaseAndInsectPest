package com.jecyhw.config.advice;

import com.jecyhw.core.response.Response;
import com.mongodb.DuplicateKeyException;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

/**
 * Created by jecyhw on 16-11-7.
 */
@ControllerAdvice
public class GlobalControllerAdvice {
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ResponseBody
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Response<?>> mongodbDuplicateKeyException(DuplicateKeyException e) {
        return new ResponseEntity<>(Response.fail(e.getErrorMessage()), HttpStatus.OK);
    }
}
