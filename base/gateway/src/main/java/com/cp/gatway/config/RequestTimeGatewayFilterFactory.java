package com.cp.gatway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class RequestTimeGatewayFilterFactory extends AbstractGatewayFilterFactory<RequestTimeGatewayFilterFactory.Config> {

	private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";
	private static final String KEY = "withParams";

	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList(KEY);
	}

	public RequestTimeGatewayFilterFactory() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			exchange.getAttributes().put(REQUEST_TIME_BEGIN, System.currentTimeMillis());

			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
				if (startTime != null) {
					StringBuilder sb = new StringBuilder(exchange.getRequest().getURI().getRawPath()).append(": ").append(System.currentTimeMillis() - startTime).append("ms");
					if (config.isWithParams()) {
						sb.append(" params:").append(exchange.getRequest().getQueryParams());
					}
					System.out.println(sb);
				}
			}));
		};
	}

	public static class Config {
		private boolean withParams;

		public boolean isWithParams() {
			return withParams;
		}

		public void setWithParams(boolean withParams) {
			this.withParams = withParams;
		}
	}
}
