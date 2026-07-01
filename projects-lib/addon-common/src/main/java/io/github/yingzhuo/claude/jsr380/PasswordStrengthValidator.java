package io.github.yingzhuo.claude.jsr380;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.jspecify.annotations.Nullable;

import java.util.regex.Pattern;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordStrength, String> {

	private static final String PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,32}$";
	private static final Pattern REGEX = Pattern.compile(PATTERN);

	@Override
	public boolean isValid(@Nullable String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		return REGEX.matcher(value).matches();
	}
}
