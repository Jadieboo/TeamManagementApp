package com.sparta.tma.exceptions;

import com.sparta.tma.entities.AppUser;
import com.sparta.tma.repositories.AppUserRepository;
import com.sparta.tma.utils.PopulateModelAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private PopulateModelAttributes modelUtil;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFoundException(NoHandlerFoundException exception, Model model, Authentication authentication) {
        model.addAttribute("message", "The page you are looking for does not exist.");
        setRoleAttributes(model, authentication);

        logger.info("model attributes: {}", model);
        return "error";
    }
    @ExceptionHandler(EmployeeNotFoundException.class)
    public String handleEmployeeNotFound(EmployeeNotFoundException exception, Model model, Authentication authentication) {
        model.addAttribute("message", exception.getMessage());
        setRoleAttributes(model, authentication);

        logger.info("model attributes employee not found: {}", model);
        return "error";
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public String handleUnauthorizedAccessException(UnauthorizedAccessException exception, Model model, Authentication authentication) {
        model.addAttribute("message", exception.getMessage());
        setRoleAttributes(model, authentication);

        logger.info("model attributes: {}", model);
        return "error";
    }

    @ExceptionHandler(UnexpectedRollbackException.class)
    public String handleUnexpectedRollbackException(UnexpectedRollbackException exception, Model model, Authentication authentication) {
        model.addAttribute("message", "Oops, something went wrong with your request. Please try again later.");
        setRoleAttributes(model, authentication);

        logger.error("Unexpected rollback occurred: {}", exception.getMessage()); // Log for debugging

        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception exception, Model model, Authentication authentication) {
        logger.error("Caught exception: {}", exception.getClass().getName());
        model.addAttribute("message", "Sorry, an unexpected error occurred.");
        setRoleAttributes(model, authentication);

        logger.info("model attributes: {}", model);
        return "error";
    }


    private void setRoleAttributes(Model model, Authentication authentication) {
        if (authentication != null) {
            AppUser user = appUserRepository.findByUsername(authentication.getName()).orElse(null);
            if (user != null) {
                modelUtil.getAuthorityRoleModelAttribute(model, user);
            }
        } else {
            model.addAttribute("isAdmin", false);
            model.addAttribute("isManager", false);
            model.addAttribute("isEmployee", false);
        }
    }

}
