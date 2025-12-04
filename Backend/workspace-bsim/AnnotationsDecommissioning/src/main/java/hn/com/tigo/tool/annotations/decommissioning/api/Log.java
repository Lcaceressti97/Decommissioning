package hn.com.tigo.tool.annotations.decommissioning.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Laurent G. Cáceres
 */

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
	public String value() default "Datasource";

	public String valueMethod() default "Method";

	public String project() default "project";

}