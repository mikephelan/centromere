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

package com.blueprint.centromere.core.test.ws;

import com.blueprint.centromere.core.config.AutoConfigureCentromere;
import com.blueprint.centromere.core.config.Schema;
import com.blueprint.centromere.core.config.Security;
import com.blueprint.centromere.core.ws.CentromereWebInitializer;

/**
 * @author woemler
 */
public class SpringBootApplicationConfigurations {
	
	@AutoConfigureCentromere
	public static class DefaultSpringBootWebConfig extends CentromereWebInitializer {
		public static void main(String[] args) {
			CentromereWebInitializer.run(DefaultSpringBootWebConfig.class, args);
		}
	}

	@AutoConfigureCentromere(schema = Schema.DEFAULT, webSecurity = Security.SECURE_READ_WRITE)
	public static class SecuredSpringBootWebConfig extends CentromereWebInitializer {
		public static void main(String[] args) {
			CentromereWebInitializer.run(DefaultSpringBootWebConfig.class, args);
		}
	}
	
}