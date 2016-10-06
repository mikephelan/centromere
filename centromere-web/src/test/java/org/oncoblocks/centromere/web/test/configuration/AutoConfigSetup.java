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

package org.oncoblocks.centromere.web.test.configuration;

import org.oncoblocks.centromere.web.config.AutoConfigureCentromere;
import org.oncoblocks.centromere.web.config.Database;
import org.oncoblocks.centromere.web.config.Schema;
import org.oncoblocks.centromere.web.test.config.TestMongoConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author woemler
 */
public class AutoConfigSetup {
	
	@Configuration
	@AutoConfigureCentromere(database = Database.MONGODB, schema = Schema.DEFAULT)
	@Import({ TestMongoConfig.class })
	public static class DefaultAutoConfig {
		
	}
	
}
