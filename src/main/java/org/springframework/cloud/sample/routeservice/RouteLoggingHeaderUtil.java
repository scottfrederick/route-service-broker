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

package org.springframework.cloud.sample.routeservice;

import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.net.URISyntaxException;

public class RouteLoggingHeaderUtil {
	public static final String FORWARDED_URL = "X-CF-Forwarded-Url";
	public static final String PROXY_METADATA = "X-CF-Proxy-Metadata";
	public static final String PROXY_SIGNATURE = "X-CF-Proxy-Signature";

	public static URI getForwardedUrl(ServerWebExchange exchange) {
		String forwardedUrl = exchange.getRequest().getHeaders().get(FORWARDED_URL).get(0);
		try {
			return new URI(forwardedUrl);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("Invalid value for X-CF-Forwarded-Url: " + forwardedUrl);
		}
	}
}
