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

package org.oncoblocks.centromere.dataimport.cli.test;

import org.oncoblocks.centromere.core.config.ModelScan;
import org.oncoblocks.centromere.dataimport.cli.DataImportConfigurer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author woemler
 */
@Configuration
@ComponentScan(basePackages = { "org.oncoblocks.centromere.dataimport.cli.test" })
@ModelScan({"org.oncoblocks.centromere.dataimport.cli.test.support"})
public class TestConfig extends DataImportConfigurer {
	
}
