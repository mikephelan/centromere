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

package org.oncoblocks.centromere.web.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Performs full, automatic configuration for model schema selection, database integration, and 
 *   web services creation.  
 * 
 * @author woemler
 * @since 0.4.3
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@AutoConfigureWebServices
@AutoConfigureApiDocumentation
@AutoConfigureWebSecurity
@Import({ 
		ProfileConfiguration.class
})
public @interface AutoConfigureCentromere {
}
