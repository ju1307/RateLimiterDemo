package com.example.RateLimiter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SimpleRateLimiter {

	public static SimpleRateLimiter create(final int permits, final TimeUnit timePeriod) {
		final SimpleRateLimiter limiter = new SimpleRateLimiter(permits, timePeriod);
		limiter.schedulePermitReplenishment();
		return limiter;
	}

	private Semaphore semaphore;
	private final int maxPermits;
	private final TimeUnit timePeriod;

	private ScheduledExecutorService scheduler;

	private SimpleRateLimiter(final int permits, final TimeUnit timePeriod) {
		semaphore = new Semaphore(permits);
		maxPermits = permits;
		this.timePeriod = timePeriod;
	}

	public void schedulePermitReplenishment() {
		scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleWithFixedDelay(() -> semaphore = new Semaphore(maxPermits), 60, 60, timePeriod);
	}

	public void stop() {
		scheduler.shutdownNow();
	}

	public boolean tryAcquire() {
		return semaphore.tryAcquire();
	}
}