package Models.ABC;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Target(ElementType.TYPE_PARAMETER)
public @interface FirstAnnotation {

    String tableName() default "first";

}
