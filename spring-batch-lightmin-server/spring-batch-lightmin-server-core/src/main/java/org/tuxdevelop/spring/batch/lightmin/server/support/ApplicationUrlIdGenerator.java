/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tuxdevelop.spring.batch.lightmin.server.support;

import org.tuxdevelop.spring.batch.lightmin.client.api.LightminClientApplication;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Marcel Becker
 * @since 0.3
 */
public final class ApplicationUrlIdGenerator {
    private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private ApplicationUrlIdGenerator(){}

    public static String generateId(final LightminClientApplication lightminClientApplication) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-1");
            final byte[] bytes = digest.digest(lightminClientApplication.getHealthUrl().getBytes(StandardCharsets.UTF_8));
            return new String(encodeHex(bytes, 0, 8));
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    private static char[] encodeHex(final byte[] bytes, final int offset, final int length) {
        final char[] chars = new char[length];
        for (int i = 0; i < length; i = i + 2) {
            final byte b = bytes[offset + (i / 2)];
            chars[i] = HEX_CHARS[(b >>> 0x4) & 0xf];
            chars[i + 1] = HEX_CHARS[b & 0xf];
        }
        return chars;
    }
}
