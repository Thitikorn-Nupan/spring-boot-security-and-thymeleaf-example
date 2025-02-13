package guru.sfg.brewery.custom_annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('customer.create')") //** only admin,customer can do
public @interface CustomerCreatePermission {
}