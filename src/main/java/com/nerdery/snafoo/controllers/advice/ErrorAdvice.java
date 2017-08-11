package com.nerdery.snafoo.controllers.advice;

import com.nerdery.snafoo.common.Logging;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller advice implementation, containing a simple exception handling strategy.
 */
@ControllerAdvice
public class ErrorAdvice implements Logging {

    @ExceptionHandler(value = RestClientException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleRuntimeException(RestClientException restException) {
        getLogger().warn("An error was encountered fetching rest data: ", restException);
        return new ModelAndView("error", "restException", restException);
    }
}
