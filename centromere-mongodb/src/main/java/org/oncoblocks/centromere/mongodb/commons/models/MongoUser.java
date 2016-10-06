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

package org.oncoblocks.centromere.mongodb.commons.models;

import org.oncoblocks.centromere.core.commons.models.User;
import org.oncoblocks.centromere.core.model.Alias;
import org.oncoblocks.centromere.core.model.ModelAttributes;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author woemler
 */
@ModelAttributes(uri = "users")
@Document(collection = "api_users")
public class MongoUser extends User<String> {
	
	@Id @Alias("userId")
	private String id;

	@Override 
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}