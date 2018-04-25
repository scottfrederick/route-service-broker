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

package org.springframework.cloud.sample.routeservice.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.cloud.gateway.handler.predicate.CloudFoundryRouteServiceRoutePredicateFactory.X_CF_FORWARDED_URL;

@Component
public class LoggingGatewayFilterFactory extends AbstractGatewayFilterFactory {
	private final Logger log = LoggerFactory.getLogger(LoggingGatewayFilterFactory.class);

	@Override
	public GatewayFilter apply(Object config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			URI forwardedUrl = getForwardedUrl(request);

			log.info("Forwarding request: method={}, headers={}, url={}",
					request.getMethod(),
					request.getHeaders(),
					forwardedUrl);

			return chain.filter(exchange)
					.doOnSuccess(x -> log.info("Response: method={}, headers={}, url={}",
							request.getMethod(),
							request.getHeaders(),
							forwardedUrl))
					.doOnError(e -> log.error("Error: exception={}, method={}, headers={}, url={}",
							e,
							request.getMethod(),
							request.getHeaders(),
							forwardedUrl));
		};
	}

	private URI getForwardedUrl(ServerHttpRequest request) {
		String forwardedUrl = request.getHeaders().get(X_CF_FORWARDED_URL).get(0);
		try {
			return new URI(forwardedUrl);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("Invalid value for " + X_CF_FORWARDED_URL + " header: " + forwardedUrl);
		}
	}
}