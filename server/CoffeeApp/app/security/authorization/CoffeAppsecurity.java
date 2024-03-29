package security.authorization;

    import play.mvc.Security;
    import play.mvc.With;

    import java.lang.annotation.ElementType;
    import java.lang.annotation.Retention;
    import java.lang.annotation.RetentionPolicy;
    import java.lang.annotation.Target;

    @Security.Authenticated
    @With(CoffeeSecurity.class)
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CoffeAppsecurity {
        String value() default "";
}
