package com.dev.eduacademy.util;

/**
 * Utility class for grading.
 */
public class GradeUtil {

    /**
     * Returns the grade letter based on the score.
     * @param score the score to evaluate
     * @return the grade letter
     */
    public static String getGradeLetter(int score) {
        return switch (score / 10) {
            case 10, 9 -> "A";
            case 8 -> "B";
            case 7 -> "C";
            case 6 -> "D";
            default -> "F";
        };
    }

    /**
     * Returns the grade name based on the score.
     * @param score the score to evaluate
     * @return the grade name
     */
    public static String getGradeName(int score) {
        return switch (score / 10) {
            case 10, 9 -> "Excellent";
            case 8 -> "Very Good";
            case 7 -> "Good";
            case 6 -> "Accepted";
            default -> "Failure";
        };
    }


}
