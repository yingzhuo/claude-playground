package io.github.yingzhuo.claude.jsr380;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.jspecify.annotations.Nullable;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

    @Override
    public boolean isValid(@Nullable Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            var password = getPropertyValue(value, "password");
            var confirmPassword = getPropertyValue(value, "confirmPassword");
            if (password == null || confirmPassword == null) {
                return true;
            }
            return password.equals(confirmPassword);
        } catch (Exception e) {
            return false;
        }
    }

    @Nullable
    private Object getPropertyValue(Object bean, String propertyName) throws Exception {
        for (PropertyDescriptor descriptor : Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors()) {
            if (descriptor.getName().equals(propertyName) && descriptor.getReadMethod() != null) {
                return descriptor.getReadMethod().invoke(bean);
            }
        }
        return null;
    }
}
