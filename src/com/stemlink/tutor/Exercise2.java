package com.stemlink.tutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Exercise2 {
    public static void main(String[] args) {
        // Test BookingValidator
        System.out.println("=== Testing BookingValidator ===");
        LocalDateTime futureTime = LocalDateTime.now().plusDays(2);
        System.out.println("Future time valid? " +
                BookingValidator.isValidBookingTime(futureTime));
        System.out.println("3 hours valid? " +
                BookingValidator.isValidDuration(3));
        System.out.println("5 hours valid? " +
                BookingValidator.isValidDuration(5));

        // Test FeeCalculator
        System.out.println("\n=== Testing FeeCalculator ===");
        System.out.println("Standard 2h: LKR " +
                FeeCalculator.calculateTotal("STANDARD", 2));
        System.out.println("Urgent 3h: LKR " +
                FeeCalculator.calculateTotal("URGENT", 3));

        // Test DateTimeFormatter
        System.out.println("\n=== Testing DateTimeFormatter ===");
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Standard: " +
                DateTimeFormatter1.formatDateTime(now));
        System.out.println("Long: " +
                DateTimeFormatter1.formatDateLong(now));
        System.out.println("Time: " +
                DateTimeFormatter1.formatTime(now));
    }
}

// 1. BookingValidator Utility Class
class BookingValidator {
    // Private constructor to prevent instantiation
    private BookingValidator() {}

    // Validates if booking time is in the future
    public static boolean isValidBookingTime(LocalDateTime bookingTime) {
        return bookingTime.isAfter(LocalDateTime.now());
    }

    // Validates if duration is between 1-4 hours
    public static boolean isValidDuration(int hours) {
        return hours >= 1 && hours <= 4;
    }

    // Validates if time slot doesn't overlap with existing booking
    public static boolean isSlotAvailable(
            LocalDateTime newStart,
            LocalDateTime newEnd,
            LocalDateTime existingStart,
            LocalDateTime existingEnd
    ) {
        // Slots overlap if: newStart < existingEnd AND newEnd > existingStart
        return !(newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart));
    }
}

// 2. FeeCalculator Utility Class
class FeeCalculator {
    // Constants
    private static final double STANDARD_RATE = 1500.0;
    private static final double URGENT_RATE = 2500.0;
    private static final double GROUP_RATE = 1200.0;
    private static final double PLATFORM_FEE_PERCENTAGE = 0.15;

    // Private constructor to prevent instantiation
    private FeeCalculator() {}

    // Calculates base fee based on booking type
    public static double calculateBaseFee(String bookingType, int hours) {
        double rate;
        switch (bookingType.toUpperCase()) {
            case "STANDARD":
                rate = STANDARD_RATE;
                break;
            case "URGENT":
                rate = URGENT_RATE;
                break;
            case "GROUP":
                rate = GROUP_RATE;
                break;
            default:
                rate = STANDARD_RATE;
        }
        return rate * hours;
    }

    // Calculates platform fee (15% of base fee)
    public static double calculatePlatformFee(double baseFee) {
        return baseFee * PLATFORM_FEE_PERCENTAGE;
    }

    // Calculates total (base + platform fee)
    public static double calculateTotal(String bookingType, int hours) {
        double baseFee = calculateBaseFee(bookingType, hours);
        double platformFee = calculatePlatformFee(baseFee);
        return baseFee + platformFee;
    }
}

// 3. DateTimeFormatter Utility Class (renamed to avoid conflict)
class DateTimeFormatter1 {
    // Private constructor to prevent instantiation
    private DateTimeFormatter1() {}

    // Formats to: "2025-11-16 14:30"
    public static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }

    // Formats to: "November 16, 2025"
    public static String formatDateLong(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        return dateTime.format(formatter);
    }

    // Formats to: "2:30 PM"
    public static String formatTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        return dateTime.format(formatter);
    }
}