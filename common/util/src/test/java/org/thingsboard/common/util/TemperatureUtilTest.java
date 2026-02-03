/**
 * Copyright © 2016-2026 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsboard.common.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureUtilTest {

    private static final double DELTA = 0.01;

    // ==================== Celsius to Fahrenheit ====================

    @Test
    void testCelsiusToFahrenheit_freezingPoint() {
        // 0°C = 32°F
        assertEquals(32.0, TemperatureUtil.celsiusToFahrenheit(0), DELTA);
    }

    @Test
    void testCelsiusToFahrenheit_boilingPoint() {
        // 100°C = 212°F
        assertEquals(212.0, TemperatureUtil.celsiusToFahrenheit(100), DELTA);
    }

    @Test
    void testCelsiusToFahrenheit_negativeTemperature() {
        // -40°C = -40°F (intersection point)
        assertEquals(-40.0, TemperatureUtil.celsiusToFahrenheit(-40), DELTA);
    }

    @Test
    void testCelsiusToFahrenheit_roomTemperature() {
        // 20°C = 68°F
        assertEquals(68.0, TemperatureUtil.celsiusToFahrenheit(20), DELTA);
    }

    // ==================== Fahrenheit to Celsius ====================

    @Test
    void testFahrenheitToCelsius_freezingPoint() {
        // 32°F = 0°C
        assertEquals(0.0, TemperatureUtil.fahrenheitToCelsius(32), DELTA);
    }

    @Test
    void testFahrenheitToCelsius_boilingPoint() {
        // 212°F = 100°C
        assertEquals(100.0, TemperatureUtil.fahrenheitToCelsius(212), DELTA);
    }

    @Test
    void testFahrenheitToCelsius_negativeTemperature() {
        // -40°F = -40°C (intersection point)
        assertEquals(-40.0, TemperatureUtil.fahrenheitToCelsius(-40), DELTA);
    }

    @Test
    void testFahrenheitToCelsius_bodyTemperature() {
        // 98.6°F ≈ 37°C
        assertEquals(37.0, TemperatureUtil.fahrenheitToCelsius(98.6), DELTA);
    }

    // ==================== Celsius to Kelvin ====================

    @Test
    void testCelsiusToKelvin_absoluteZero() {
        // -273.15°C = 0K
        assertEquals(0.0, TemperatureUtil.celsiusToKelvin(-273.15), DELTA);
    }

    @Test
    void testCelsiusToKelvin_freezingPoint() {
        // 0°C = 273.15K
        assertEquals(273.15, TemperatureUtil.celsiusToKelvin(0), DELTA);
    }

    @Test
    void testCelsiusToKelvin_boilingPoint() {
        // 100°C = 373.15K
        assertEquals(373.15, TemperatureUtil.celsiusToKelvin(100), DELTA);
    }

    // ==================== Kelvin to Celsius ====================

    @Test
    void testKelvinToCelsius_absoluteZero() {
        // 0K = -273.15°C
        assertEquals(-273.15, TemperatureUtil.kelvinToCelsius(0), DELTA);
    }

    @Test
    void testKelvinToCelsius_freezingPoint() {
        // 273.15K = 0°C
        assertEquals(0.0, TemperatureUtil.kelvinToCelsius(273.15), DELTA);
    }

    // ==================== Fahrenheit to Kelvin ====================

    @Test
    void testFahrenheitToKelvin_freezingPoint() {
        // 32°F = 273.15K
        assertEquals(273.15, TemperatureUtil.fahrenheitToKelvin(32), DELTA);
    }

    // ==================== Kelvin to Fahrenheit ====================

    @Test
    void testKelvinToFahrenheit_freezingPoint() {
        // 273.15K = 32°F
        assertEquals(32.0, TemperatureUtil.kelvinToFahrenheit(273.15), DELTA);
    }

    // ==================== Validation ====================

    @Test
    void testIsValidTemperature_validTemperature() {
        assertTrue(TemperatureUtil.isValidTemperature(0));
        assertTrue(TemperatureUtil.isValidTemperature(100));
        assertTrue(TemperatureUtil.isValidTemperature(-273.15));
    }

    @Test
    void testIsValidTemperature_invalidTemperature() {
        // Below absolute zero is physically impossible
        assertFalse(TemperatureUtil.isValidTemperature(-300));
        assertFalse(TemperatureUtil.isValidTemperature(-500));
    }

    // ==================== Round-trip conversions ====================

    @Test
    void testRoundTripConversion_celsiusFahrenheit() {
        double original = 25.0;
        double converted = TemperatureUtil.fahrenheitToCelsius(
                TemperatureUtil.celsiusToFahrenheit(original));
        assertEquals(original, converted, DELTA);
    }

    @Test
    void testRoundTripConversion_celsiusKelvin() {
        double original = 25.0;
        double converted = TemperatureUtil.kelvinToCelsius(
                TemperatureUtil.celsiusToKelvin(original));
        assertEquals(original, converted, DELTA);
    }
}
