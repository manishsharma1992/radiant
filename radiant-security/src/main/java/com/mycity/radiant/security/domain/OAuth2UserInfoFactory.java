package com.mycity.radiant.security.domain;

import java.util.Map;

import com.mycity.radaint.domain.payload.request.SocialProvider;

public class OAuth2UserInfoFactory {
	
	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) throws Exception {
		if (registrationId.equalsIgnoreCase(SocialProvider.GOOGLE.getProviderType())) {
			return new GoogleOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase(SocialProvider.FACEBOOK.getProviderType())) {
			return new FacebookOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase(SocialProvider.GITHUB.getProviderType())) {
			return new GithubOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase(SocialProvider.LINKEDIN.getProviderType())) {
			return new LinkedinOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase(SocialProvider.TWITTER.getProviderType())) {
			return new GithubOAuth2UserInfo(attributes);
		} else {
			throw new Exception("Sorry! Login with " + registrationId + " is not supported yet.");
		}
	}

}
