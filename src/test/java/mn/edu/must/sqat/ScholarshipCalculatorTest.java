package mn.edu.must.sqat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScholarshipCalculatorTest {

    private final ScholarshipCalculator calc = new ScholarshipCalculator();

    // ---- Part A: happy path ----

    @Test
    void letterGradeA() {
        assertEquals("A", calc.letterGrade(95));
    }

    @Test
    void letterGradeB() {
        assertEquals("B", calc.letterGrade(85));
    }

    @Test
    void gpaOfAAndB() {
        assertEquals(3.5, calc.gpa(new double[]{95, 85}), 1e-9);
    }

    @Test
    void scholarshipFull() {
        assertEquals(100, calc.scholarship(3.9, 16, false));
    }

    @Test
    void scholarshipSeventy() {
        assertEquals(70, calc.scholarship(3.5, 13, false));
    }

    // ---- Part B: letterGrade remaining branches ----

    @Test
    void letterGradeNegativeThrows() {
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(-1));
    }

    @Test
    void letterGradeAbove100Throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(101));
    }

    @Test
    void letterGradeC() {
        assertEquals("C", calc.letterGrade(75));
    }

    @Test
    void letterGradeD() {
        assertEquals("D", calc.letterGrade(65));
    }

    @Test
    void letterGradeF() {
        assertEquals("F", calc.letterGrade(50));
    }

    // ---- Part B: gpa remaining branches ----

    @Test
    void gpaNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(null));
    }

    @Test
    void gpaEmptyThrows() {
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(new double[]{}));
    }

    @Test
    void gpaAllGrades() {
        // A=4, B=3, C=2, D=1, F=0 (switch default) -> 10 / 5
        assertEquals(2.0, calc.gpa(new double[]{95, 85, 75, 65, 50}), 1e-9);
    }

    // ---- Part B: scholarship remaining branches ----

    @Test
    void scholarshipNegativeGpaThrows() {
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(-0.1, 15, false));
    }

    @Test
    void scholarshipGpaAbove4Throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(4.1, 15, false));
    }

    @Test
    void scholarshipNegativeCreditsThrows() {
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(3.5, -1, false));
    }

    @Test
    void scholarshipViolationZero() {
        assertEquals(0, calc.scholarship(3.9, 16, true));
    }

    @Test
    void scholarshipHighGpaLowCredits() {
        // gpa >= 3.8 but credits < 15 -> falls to the 70% rule
        assertEquals(70, calc.scholarship(3.9, 13, false));
    }

    @Test
    void scholarshipGoodGpaLowCredits() {
        // gpa >= 3.4 but credits < 12 -> falls to the 50% rule
        assertEquals(50, calc.scholarship(3.5, 10, false));
    }

    @Test
    void scholarshipFifty() {
        assertEquals(50, calc.scholarship(3.2, 20, false));
    }

    @Test
    void scholarshipBelowThreeZero() {
        assertEquals(0, calc.scholarship(2.5, 20, false));
    }

    // ---- Part C: loop boundaries in gpa() (0 / 1 / 2+ iterations) ----

    @Test
    void gpaLoopZeroEmptyThrows() {
        // 0 iterations: empty array must be rejected before the loop
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(new double[]{}));
    }

    @Test
    void gpaLoopZeroNullThrows() {
        // 0 iterations: null input must be rejected before the loop
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(null));
    }

    @Test
    void gpaLoopOne() {
        // 1 iteration
        assertEquals(4.0, calc.gpa(new double[]{95}), 1e-9);
    }

    @Test
    void gpaLoopMany() {
        // 2+ iterations: (4 + 3 + 2 + 1) / 4
        assertEquals(2.5, calc.gpa(new double[]{95, 85, 75, 65}), 1e-9);
    }
}
