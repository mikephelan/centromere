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

package com.blueprint.centromere.ws.controller;

import com.blueprint.centromere.core.commons.model.User;
import com.blueprint.centromere.ws.security.BasicTokenUtils;
import com.blueprint.centromere.ws.security.TokenDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author woemler
 */
@RestController
public class UserAuthenticationController {

	@Autowired private BasicTokenUtils tokenUtils;

	private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationController.class);

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public TokenDetails createToken(@AuthenticationPrincipal User user){
		Assert.notNull(user, "Unable to authenticate user!");
		TokenDetails tokenDetails = tokenUtils.createTokenAndDetails(user);
		logger.info(String.format("Successfully generated authentication token for user: %s", user.getUsername()));
		return tokenDetails;
	}
	
}
