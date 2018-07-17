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

package org.springframework.cloud.sample.routeservice.servicebroker;

import org.springframework.cloud.servicebroker.model.instance.CreateServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.instance.CreateServiceInstanceResponse;
import org.springframework.cloud.servicebroker.model.instance.DeleteServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.instance.DeleteServiceInstanceResponse;
import org.springframework.cloud.servicebroker.service.ServiceInstanceService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RouteLoggingServiceInstanceService implements ServiceInstanceService {
	@Override
	public Mono<CreateServiceInstanceResponse> createServiceInstance(CreateServiceInstanceRequest request) {
		return Mono.just(CreateServiceInstanceResponse.builder()
				.instanceExisted(false)
				.build());
	}

	@Override
	public Mono<DeleteServiceInstanceResponse> deleteServiceInstance(DeleteServiceInstanceRequest request) {
		return Mono.just(DeleteServiceInstanceResponse.builder()
				.build());
	}
}
