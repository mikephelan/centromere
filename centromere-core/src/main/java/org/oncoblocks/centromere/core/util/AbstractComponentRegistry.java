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

package org.oncoblocks.centromere.core.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract {@link ComponentRegistry} implementation that uses a {@link HashMap} to register and 
 *   reference target objects.
 * 
 * @author woemler
 * @since 0.4.1
 */
public abstract class AbstractComponentRegistry<T> implements ComponentRegistry<T> {
	
	private Map<String, T> componentMap = new HashMap<>();

	@Override 
	public T find(String keyword) {
		return componentMap.get(keyword);
	}

	@Override 
	public boolean exists(String keyword) {
		return componentMap.containsKey(keyword);
	}

	@Override 
	public boolean exists(T object) {
		return componentMap.containsValue(object);
	}

	@Override 
	public void add(String keyword, T object) {
		componentMap.put(keyword, object);
	}
	
	@Override
	public Map<String, T> getRegistry(){
		return componentMap;
	}

	@Override 
	public Iterable<T> getRegisteredComponents() {
		return componentMap.values();
	}

	@Override 
	public void setRegistry(Map<String, T> registry) {
		this.componentMap = registry;
	}
}
