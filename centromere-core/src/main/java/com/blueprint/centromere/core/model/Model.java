/*
 * Copyright 2017 the original author or authors
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

package com.blueprint.centromere.core.model;

import java.io.Serializable;

/**
 * Basic entity interface to ensure that model objects have identifiable attributes.  {@code ID} is 
 *   intended to reflect database primary key identifiers, whether they be primitive types, or a 
 *   unique combination of fields.  
 * 
 * @author woemler
 */
@Filterable
public interface Model<ID extends Serializable> {
	
  ID getId();
  void setId(ID id);
  
}
