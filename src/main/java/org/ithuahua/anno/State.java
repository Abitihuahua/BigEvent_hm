package org.ithuahua.anno;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.ithuahua.validation.StateValidation;

import java.lang.annotation.*;
//元注解1
@Documented
//元注解2标识可使用的范围
@Target({ElementType.FIELD})
//元注解3保留到什么时候
@Retention(RetentionPolicy.RUNTIME)
//元注解4提供校验规则
@Constraint(
        validatedBy = {StateValidation.class} //指定提供校验规则的类，最后写
)
public @interface State {
    //提供校验失败的信息
    String message() default "state参数的值只能是已发布或者草稿";
    //指定分组
    Class<?>[] groups() default {};
    //负载 获取到State注解的附加信息
    Class<? extends Payload>[] payload() default {};
}
