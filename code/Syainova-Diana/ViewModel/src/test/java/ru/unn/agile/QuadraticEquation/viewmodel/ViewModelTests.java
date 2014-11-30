package ru.unn.agile.QuadraticEquation.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new ViewModel();
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.firstCoefficientProperty().get());
        assertEquals("", viewModel.secondCoefficientProperty().get());
        assertEquals("", viewModel.firstRootResultProperty().get());
        assertEquals("", viewModel.secondRootResultProperty().get());
        assertEquals(systemStatus.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingWhenSolveWithEmptyFields() {
        viewModel.solve();

        assertEquals(systemStatus.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsReadyWhenFieldsAreFill() {
        setEquationData();

        assertEquals(systemStatus.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsIncorrectWhenFirstCoefficientIsNull() {
        viewModel.firstCoefficientProperty().set("0");

        assertEquals(systemStatus.INCORRECT_COEF.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void solveIsIncorrectWhenFirstCoefficientIsNullOtherKind() {
        viewModel.firstCoefficientProperty().set("00.0");

        assertEquals(systemStatus.INCORRECT_COEF.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canReportWhenTheInputCoefficientHasBadFormat() {
        viewModel.firstCoefficientProperty().set("/");

        assertEquals(systemStatus.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingWhenNotAllCoefficientsIntroduced() {
        viewModel.firstCoefficientProperty().set("2");
        viewModel.secondCoefficientProperty().set("1");

        assertEquals(systemStatus.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void solveButtonIsDisabledInitially() {
        assertTrue(viewModel.solvingDisabledProperty().get());
    }

    @Test
    public void solveButtonIsDisabledWhenFormatIsBad() {
        setEquationData();
        viewModel.freeTermProperty().set("ttt");

        assertTrue(viewModel.solvingDisabledProperty().get());
    }

    @Test
    public void solveButtonIsDisabledWhenFirstCoefficientIsNull() {
        setEquationData();
        viewModel.firstCoefficientProperty().set("0");

        assertTrue(viewModel.solvingDisabledProperty().get());
    }

    @Test
    public void solveButtonIsDisabledWheNotAllCoefficientsIntroduced() {
        viewModel.firstCoefficientProperty().set("4");
        viewModel.freeTermProperty().set("2");

        assertTrue(viewModel.solvingDisabledProperty().get());
    }

    @Test
    public void solveButtonIsEnabledWhenAllCorrectCoefficientIntroduced() {
        setEquationData();

        assertFalse(viewModel.solvingDisabledProperty().get());
    }

    @Test
    public void solvingHasTwoDifferentRoots() {
        viewModel.firstCoefficientProperty().set("1");
        viewModel.secondCoefficientProperty().set("1");
        viewModel.freeTermProperty().set("-2");

        viewModel.solve();

        assertEquals("x = 1.0", viewModel.firstRootResultProperty().get());
        assertEquals("x = -2.0", viewModel.secondRootResultProperty().get());
    }

    @Test
    public void statusIsSuccessWhenEquationCorrectlySolved() {
        setEquationData();

        viewModel.solve();

        assertEquals(systemStatus.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsReadyWhenEnterCorrectCoefficients() {
        setEquationData();

        assertEquals(systemStatus.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canReportBadFormatCoefficients() {
        viewModel.firstCoefficientProperty().set("incorrect coefficients");

        assertEquals(systemStatus.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void solvingHasTwoSameRoots() {
        viewModel.firstCoefficientProperty().set("1");
        viewModel.secondCoefficientProperty().set("-10");
        viewModel.freeTermProperty().set("25");

        viewModel.solve();

        assertEquals("x = 5.0", viewModel.firstRootResultProperty().get());
        assertEquals("x = 5.0", viewModel.secondRootResultProperty().get());
    }

    @Test
    public void solvingHasNotRealRoots() {
        viewModel.firstCoefficientProperty().set("1");
        viewModel.secondCoefficientProperty().set("1");
        viewModel.freeTermProperty().set("2");

        viewModel.solve();

        assertEquals(systemStatus.NO_ROOTS.toString(), viewModel.statusProperty().get());
    }

    public void setEquationData() {
        viewModel.firstCoefficientProperty().set("5");
        viewModel.secondCoefficientProperty().set("-4");
        viewModel.freeTermProperty().set("-3");
    }
}