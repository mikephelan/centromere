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

package com.blueprint.centromere.core.config;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author woemler
 */
@Configuration
@PropertySource("classpath:database-defaults.properties")
@ConfigurationProperties("centromere.db")
@Data
public class DatabaseProperties {
  
  private String host;
  private String name;
  private String user;
  private String password;
  private Integer port;
  private Map<String, String> attributes = new HashMap<>();

  public boolean hasAttribute(String attribute){
    return this.attributes.containsKey(attribute);
  }

  public String getAttribute(String attribute){
    return this.attributes.getOrDefault(attribute, null);
  }
  
}
