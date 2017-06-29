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

package com.blueprint.centromere.cli.config;

import com.blueprint.centromere.cli.CommandLineInputExecutor;
import com.blueprint.centromere.cli.DeleteCommandExecutor;
import com.blueprint.centromere.cli.FileImportExecutor;
import com.blueprint.centromere.cli.ListCommandExecutor;
import com.blueprint.centromere.cli.ManifestImportExecutor;
import com.blueprint.centromere.cli.ModelProcessorBeanRegistry;
import com.blueprint.centromere.core.config.Profiles;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.repository.support.Repositories;

/**
 * @author woemler
 */
@Configuration
@PropertySources({
		@PropertySource({"classpath:centromere-defaults.properties"}),
		@PropertySource(value = {"classpath:centromere.properties"}, ignoreResourceNotFound = true)
})
@Profile({ Profiles.CLI_PROFILE })
public class CommandLineInputConfiguration {
	
	@Bean
	public ModelProcessorBeanRegistry modelProcessorBeanRegistry(){
		return new ModelProcessorBeanRegistry();
	}
	
	@Bean
	public CommandLineInputExecutor commandLineInputExecutor(){
		return new CommandLineInputExecutor();
	}
	
	@Bean
	public FileImportExecutor fileImportExecutor(){
		return new FileImportExecutor();
	}
	
	@Bean
	public ManifestImportExecutor manifestImportExecutor(){
		return new ManifestImportExecutor();
	}
	
	@Bean
  public DeleteCommandExecutor deleteCommandExecutor(){
	  return new DeleteCommandExecutor();
  }

	@Bean
  public ListCommandExecutor listCommandExecutor(){
	  return new ListCommandExecutor();
  }

  @Bean
  public Repositories repositories(ApplicationContext context){
    return new Repositories(context);
  }

}