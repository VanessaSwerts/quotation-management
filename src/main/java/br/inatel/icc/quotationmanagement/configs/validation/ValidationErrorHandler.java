package br.inatel.icc.quotationmanagement.configs.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationErrorHandler {

	private MessageSource messageSource;

	@Autowired
	public ValidationErrorHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<FormErrorDto> fieldErrorHandle(MethodArgumentNotValidException exception) {
		List<FormErrorDto> errors = new ArrayList<>();

		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(err -> {
			String field = err.getField();
			String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());

			FormErrorDto errorDto = new FormErrorDto(field, message);

			errors.add(errorDto);
		});

		return errors;
	}

}
