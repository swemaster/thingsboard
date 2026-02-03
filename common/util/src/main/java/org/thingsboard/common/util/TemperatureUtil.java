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

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class for temperature conversions between Celsius, Fahrenheit, and Kelvin.
 * Useful for IoT telemetry data normalization.
 */
public class TemperatureUtil {

    private static final double KELVIN_OFFSET = 273.15;
    private static final int DEFAULT_PRECISION = 2;

    /**
     * Converts Celsius to Fahrenheit.
     * Formula: °F = (°C × 9/5) + 32
     *
     * @param celsius temperature in Celsius
     * @return temperature in Fahrenheit
     */
    public static double celsiusToFahrenheit(double celsius) {
        return round((celsius * 9.0 / 5.0) + 32.0, DEFAULT_PRECISION);
    }

    /**
     * Converts Fahrenheit to Celsius.
     * Formula: °C = (°F − 32) × 5/9
     *
     * @param fahrenheit temperature in Fahrenheit
     * @return temperature in Celsius
     */
    public static double fahrenheitToCelsius(double fahrenheit) {
        return round((fahrenheit - 32.0) * 5.0 / 9.0, DEFAULT_PRECISION);
    }

    /**
     * Converts Celsius to Kelvin.
     * Formula: K = °C + 273.15
     *
     * @param celsius temperature in Celsius
     * @return temperature in Kelvin
     */
    public static double celsiusToKelvin(double celsius) {
        return round(celsius + KELVIN_OFFSET, DEFAULT_PRECISION);
    }

    /**
     * Converts Kelvin to Celsius.
     * Formula: °C = K − 273.15
     *
     * @param kelvin temperature in Kelvin
     * @return temperature in Celsius
     */
    public static double kelvinToCelsius(double kelvin) {
        return round(kelvin - KELVIN_OFFSET, DEFAULT_PRECISION);
    }

    /**
     * Converts Fahrenheit to Kelvin.
     *
     * @param fahrenheit temperature in Fahrenheit
     * @return temperature in Kelvin
     */
    public static double fahrenheitToKelvin(double fahrenheit) {
        return celsiusToKelvin(fahrenheitToCelsius(fahrenheit));
    }

    /**
     * Converts Kelvin to Fahrenheit.
     *
     * @param kelvin temperature in Kelvin
     * @return temperature in Fahrenheit
     */
    public static double kelvinToFahrenheit(double kelvin) {
        return celsiusToFahrenheit(kelvinToCelsius(kelvin));
    }

    /**
     * Checks if a temperature in Celsius is within a valid range.
     * Absolute zero is -273.15°C, so any temperature below this is invalid.
     *
     * @param celsius temperature in Celsius
     * @return true if the temperature is physically valid
     */
    public static boolean isValidTemperature(double celsius) {
        return celsius >= -KELVIN_OFFSET;
    }

    /**
     * Rounds a value to the specified precision.
     *
     * @param value     the value to round
     * @param precision number of decimal places
     * @return rounded value
     */
    private static double round(double value, int precision) {
        return BigDecimal.valueOf(value)
                .setScale(precision, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
