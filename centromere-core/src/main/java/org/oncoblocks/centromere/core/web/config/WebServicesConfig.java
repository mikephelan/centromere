/*
 * Copyright 2015 William Oemler, Blueprint Medicines
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

package org.oncoblocks.centromere.core.web.config;

import org.oncoblocks.centromere.core.web.util.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.FormatterRegistry;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * Default web configuration file for Centromere web services.  Adds the following functionality: 
 *   - Field-filtering via {@link org.oncoblocks.centromere.core.web.util.FilteringJackson2HttpMessageConverter}.
 *   - Default media type handling
 *   - CORS filter support
 *
 * @author woemler
 */

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@EnableHypermediaSupport(type = { EnableHypermediaSupport.HypermediaType.HAL })
@EnableEntityLinks
public class WebServicesConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		
		FilteringJackson2HttpMessageConverter jsonConverter = new FilteringJackson2HttpMessageConverter();
		jsonConverter.setPrettyPrint(true);
		converters.add(jsonConverter);
		
		List<MediaType> xmlMediaTypes = Arrays.asList(MediaType.APPLICATION_XML,
				HalMediaType.APPLICATION_HAL_XML);
		MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
		xmlConverter.setSupportedMediaTypes(xmlMediaTypes);
		XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
		xmlConverter.setMarshaller(xStreamMarshaller);
		xmlConverter.setUnmarshaller(xStreamMarshaller);
		converters.add(xmlConverter);
		
		TextMessageConverter textMessageConverter = 
				new TextMessageConverter(new MediaType("text", "plain", Charset.forName("utf-8")));
		textMessageConverter.setDelimiter("\t");
		converters.add(textMessageConverter);
		
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer){
		configurer.defaultContentType(MediaType.APPLICATION_JSON);
	}

	@Bean
	public CorsFilter corsFilter(){
		return new CorsFilter();
	}

	@Override 
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringToAttributeConverter());
		registry.addConverter(new StringToSourcedAliasConverter());
		super.addFormatters(registry);
	}
}
