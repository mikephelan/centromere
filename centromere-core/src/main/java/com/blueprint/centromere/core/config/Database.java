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
 * @author woemler
 * @since 0.5.0
 */
public enum Database {
	CUSTOM,
	MONGODB,
	GENERIC_JPA,
	MYSQL
	;

	public static final String CUSTOM_PROFILE = "db_custom";
	public static final String MONGODB_PROFILE = "db_mongodb";
	public static final String GENERIC_JPA_PROFILE = "db_jpa";
	public static final String MYSQL_PROFILE = "db_mysql";

	public static String getProfile(Database database){
		switch (database){
			case MONGODB:
				return MONGODB_PROFILE;
			case MYSQL:
				return MYSQL_PROFILE;
			case GENERIC_JPA:
				return GENERIC_JPA_PROFILE;
			default:
				return CUSTOM_PROFILE;
		}
	}
	
}