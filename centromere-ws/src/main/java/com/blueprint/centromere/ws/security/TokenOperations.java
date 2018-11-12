/*
 * Copyright 2016 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.blueprint.centromere.ws.security;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Standard method interface for creating and validating user {@link TokenDetails}
 * 
 * @author woemler
 */
public interface TokenOperations {

	/**
	 * Generates a string token from the submitted {@link UserDetails}
	 * 
	 * @param userDetails {@link UserDetails}
	 * @return string token representation
	 */
	String createToken(UserDetails userDetails);

  /**
   * Generates a user authentication token and user-visible token details.
   * 
   * @param userDetails
   * @return token details object
   */
	TokenDetails createTokenAndDetails(UserDetails userDetails);

	/**
	 * Returns the username portion of a submitted authentication token
	 * 
	 * @param authToken token generated by {@link com.blueprint.centromere.ws.security.TokenOperations#createToken}
	 * @return username
	 */
	String getUserNameFromToken(String authToken);

	/**
	 * Checks that the submitted token is valid and returns a {@code boolean} verdict
	 * 
	 * @param authToken token generated by {@link com.blueprint.centromere.ws.security.TokenOperations#createToken}
	 * @param userDetails {@link UserDetails}
	 * @return boolean result of validation
	 */
	boolean validateToken(String authToken, UserDetails userDetails);
}
