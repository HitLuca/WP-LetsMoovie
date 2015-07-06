package types.annotations;

import types.InputValidator.Regex;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by marco on 06/07/15.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface toSanitize {
    String name();

    Regex reg();
}
