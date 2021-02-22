package com.wallester.backend.utils;

import com.wallester.backend.exception.ApiException;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

@NoArgsConstructor
public class ExceptionUtils {
    /**
     * Checks that {@link BindingResult} haven't errors. If has, method calls {@link #validationMessage(List)}.
     *
     * @param result    validation result
     */
    public static void checkValidationErrors(BindingResult result) {
        if (result.hasErrors()) {
            throw new ApiException(validationMessage(result.getAllErrors()), 400);
        }
    }

    /**
     * Generate validation error string for {@link ApiException} from {@link ObjectError}'s list.
     *
     * @param errors    list with validation errors
     * @return          message for {@link ApiException}
     */
    public static String validationMessage(List<ObjectError> errors) {
        StringBuilder message = new StringBuilder("Validation error(s): ");
        int i = 1;
        for (ObjectError error : errors) {
            message.append(i);
            message.append(") ");
            message.append(error.getDefaultMessage()).append("; ");
            i++;
        }
        return message.toString();
    }
}
