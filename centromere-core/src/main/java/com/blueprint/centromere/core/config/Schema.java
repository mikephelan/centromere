/*
 * Copyright 2016 William Oemler, Blueprint Medicines
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

package com.blueprint.centromere.core.config;

/**
 *
 *
 * @author woemler
 * @since 0.5.0
 */
public enum Schema {
	CUSTOM,
	DEFAULT
	;

	public static final String CUSTOM_PROFILE = "schema_custom";
	public static final String DEFAULT_PROFILE = "schema_default";

	public static String getProfile(Schema schema){
		switch (schema){
			case DEFAULT:
				return DEFAULT_PROFILE;
			default:
				return CUSTOM_PROFILE;
		}
	}

}