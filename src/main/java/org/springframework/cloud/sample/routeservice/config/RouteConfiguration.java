/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.sample.routeservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.sample.routeservice.RouteLoggingHeaderUtil;
import org.springframework.cloud.sample.routeservice.filter.ForwardingGatewayFilter;
import org.springframework.cloud.sample.routeservice.filter.LoggingGatewayFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.cloud.gateway.filter.RouteToRequestUrlFilter.ROUTE_TO_URL_FILTER_ORDER;

@Configuration
public class RouteConfiguration {

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder,
										   LoggingGatewayFilter loggingFilter,
										   ForwardingGatewayFilter forwardingFilter) {
		return builder.routes()
				.route(r ->
						r.header(RouteLoggingHeaderUtil.FORWARDED_URL, ".*")
								.and()
								.header(RouteLoggingHeaderUtil.PROXY_METADATA, ".*")
								.and()
								.header(RouteLoggingHeaderUtil.PROXY_SIGNATURE, ".*")
								.filters(f -> {
									f.filter(loggingFilter);
									f.filter(forwardingFilter, ROUTE_TO_URL_FILTER_ORDER + 1);
									return f;
								})
								.uri("https://cloud.spring.io"))
				.build();
	}
}
