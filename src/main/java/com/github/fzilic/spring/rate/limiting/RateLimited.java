/*
 * Copyright (c) 2015 Franjo Žilić <frenky666@gmail.com>
 *
 * Permission to use, copy, modify, and distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 */

package com.github.fzilic.spring.rate.limiting;

import com.github.fzilic.spring.rate.limiting.key.DefaultKeyGenerator;
import com.github.fzilic.spring.rate.limiting.key.KeyGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Rate limiting marker processed by {@link com.github.fzilic.spring.rate.limiting.aspect.RateLimitingAdvice}.
 * <p/>
 * Allows limiting number of requests in configured interval for key.
 * <p/>
 * Key can be generated by {@link KeyGenerator} (see {@link DefaultKeyGenerator}), or entered statically to limit multiple calls to same rate.
 * <p/>
 * Retry can be configured using {@link RateLimitedRetry} or with ex
 *
 * @author franjozilic
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RateLimited {

  enum Configuration {
    DATABASE, ANNOTATION
  }

  /**
   * Use {@link RateLimited} properties for configuration ({@link RateLimited.Configuration#ANNOTATION}) or read them form external "database" ({@link RateLimited.Configuration#DATABASE}
   */
  Configuration configuration() default Configuration.ANNOTATION;

  /**
   * Used to disable rate limiting.
   * Useful when rate limiting is applied to entire class, but certain methods have to be excluded from rate limiting
   */
  boolean enabled() default true;

  /**
   * Interval for rate limiting
   * See {@link Interval}
   */
  Interval interval() default @Interval(interval = -1);

  /**
   * Rate limiting key.
   * Will be generated by {@link KeyGenerator} if empty.
   */
  String key() default "";

  /**
   * SpEL expression to generate key.
   */
  String keyExpression() default "";

  /**
   * Maximum number of requests allowed in one {@link #interval()}
   */
  long maxRequests() default -1;

}
