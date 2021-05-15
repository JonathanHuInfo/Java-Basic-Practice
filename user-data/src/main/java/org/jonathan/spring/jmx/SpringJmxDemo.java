/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jonathan.spring.jmx;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.LiveBeansView;
import org.springframework.jmx.export.annotation.AnnotationMBeanExporter;

import java.io.IOException;

/**
 * Spring JMX 示例
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 * Date : 2021-04-17
 */
@Configuration
public class SpringJmxDemo {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.register(SpringJmxDemo.class);

        context.refresh();
        System.in.read();
        context.close();
    }

    @Bean
    public AnnotationMBeanExporter beanExporter() {
        return new AnnotationMBeanExporter();
    }

    @Bean
    public User user() {
        return new User();
    }

    // 未来 Spring Boot /actuator/beans 来替代它
    @Bean
    public LiveBeansView liveBeansView() {
        return new LiveBeansView();
    }
}

