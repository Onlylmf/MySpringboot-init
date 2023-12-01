package com.murphy.config.interceptor;
import org.springframework.util.Assert;

public class MyContextHolder {

	private static final ThreadLocal<Mycontext> contextHolder = new ThreadLocal<>();

	// ~ Methods
	// ========================================================================================================

	public static void clearContext() {
		contextHolder.remove();
	}

	public static Mycontext getContext() {
		Mycontext ctx = contextHolder.get();

		if (ctx == null) {
			ctx = createEmptyContext();
			contextHolder.set(ctx);
		}

		return ctx;
	}

	public static void setContext(Mycontext context) {
		Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
		contextHolder.set(context);
	}

	public static Mycontext createEmptyContext() {
		return new Mycontext();
	}
}
