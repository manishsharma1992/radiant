package com.mycity.radiant.security.domain;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.mycity.radaint.domain.User;
import com.mycity.radaint.domain.utils.GeneralUtils;

public class LocalUser extends org.springframework.security.core.userdetails.User implements OAuth2User, OidcUser {

	private static final long serialVersionUID = 1L;
	private final OidcIdToken idToken;
	private final OidcUserInfo userInfo;
	private Map<String, Object> attributes;
	private User user;

	public LocalUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
			User user) {
		this(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities, user,
				null, null);
	}

	public LocalUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
			User user, OidcIdToken idToken, OidcUserInfo userInfo) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.user = user;
		this.idToken = idToken;
		this.userInfo = userInfo;
	}

	public static LocalUser create(User user, Map<String, Object> attributes, OidcIdToken idToken,
			OidcUserInfo userInfo) {
		LocalUser localUser = new LocalUser(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true,
				GeneralUtils.buildSimpleGrantedAuthorities(user.getRoles()), user, idToken, userInfo);
		localUser.setAttributes(attributes);
		return localUser;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	@Override
	public String getName() {
		return this.user.getDisplayName();
	}

	@Override
	public Map<String, Object> getClaims() {
		return this.attributes;
	}

	@Override
	public OidcUserInfo getUserInfo() {
		return this.userInfo;
	}

	@Override
	public OidcIdToken getIdToken() {
		return this.idToken;
	}

	public User getUser() {
		return user;
	}

}
