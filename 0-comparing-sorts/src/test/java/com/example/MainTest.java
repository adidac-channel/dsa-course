package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for Main application.
 */
class MainTest {
    
    @Test
    @DisplayName("Sample test to verify testing framework is working")
    void sampleTest() {
        // Given
        String expected = "Hello from Java 21!";
        
        // When
        String actual = "Hello from Java 21!";
        
        // Then
        assertThat(actual).isEqualTo(expected);
    }
}
