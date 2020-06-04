package com.example.RateLimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class RateLimitingInterceptor extends HandlerInterceptorAdapter {

	private final Map<String, SimpleRateLimiter> limiters = new ConcurrentHashMap<>();

	@PreDestroy
	public void destroy() {
		// loop and finalize all limiters
	}

	private SimpleRateLimiter getRateLimiter(final String key) {
		if (limiters.containsKey(key)) {
			return limiters.get(key);
		} else {
			synchronized (key.intern()) {
				// double-checked locking to avoid multiple-reinitializations
				if (limiters.containsKey(key)) {
					return limiters.get(key);
				}

				final SimpleRateLimiter rateLimiter = SimpleRateLimiter.create(3, TimeUnit.SECONDS);

				limiters.put(key, rateLimiter);
				return rateLimiter;
			}
		}
	}

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws Exception {
		final SimpleRateLimiter rateLimiter = getRateLimiter(request.getRequestURI());
		final boolean allowRequest = rateLimiter.tryAcquire();

		if (!allowRequest) {
			response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
		}
		return allowRequest;
	}
}