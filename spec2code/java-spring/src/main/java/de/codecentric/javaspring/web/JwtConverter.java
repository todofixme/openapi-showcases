package de.codecentric.javaspring.web;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

@Component
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

	private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

	@Value("${app.jwt-converter.resource-id}")
	private String resourceId;

	@Value("${app.jwt-converter.principal-attribute}")
	private String principalAttribute;

	@Override
	public AbstractAuthenticationToken convert(Jwt jwt) {
		final Set<GrantedAuthority> grantedAuthorities = Sets
			.newHashSet(Iterables.concat(this.jwtGrantedAuthoritiesConverter.convert(jwt), extractResourceRoles(jwt)));
		return new JwtAuthenticationToken(jwt, grantedAuthorities, jwt.getClaim(this.principalAttribute));
	}

	private Collection<GrantedAuthority> extractResourceRoles(Jwt jwt) {
		final Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
		if (resourceAccess != null) {
			try {
				@SuppressWarnings("unchecked")
				final Map<String, Object> resource = (Map<String, Object>) resourceAccess.get(this.resourceId);

				if (resource != null) {
					@SuppressWarnings("unchecked")
					final Collection<String> resourceRoles = (Collection<String>) resource.get("roles");

					if (resourceRoles != null) {
						return resourceRoles.stream()
							.map((role) -> new SimpleGrantedAuthority("ROLE_" + role))
							.collect(Collectors.toSet());
					}
				}
			}
			catch (ClassCastException ex) {
				return Collections.emptySet();
			}
		}
		return Collections.emptySet();
	}

}
