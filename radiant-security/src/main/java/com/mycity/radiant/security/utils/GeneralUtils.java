package com.mycity.radiant.security.utils;

import com.mycity.radaint.domain.payload.request.SocialProvider;

public class GeneralUtils {
	
	public static SocialProvider toSocialProvider(String providerId) {
		for (SocialProvider socialProvider : SocialProvider.values()) {
			if (socialProvider.getProviderType().equals(providerId)) {
				return socialProvider;
			}
		}
		return SocialProvider.LOCAL;
	}

}
