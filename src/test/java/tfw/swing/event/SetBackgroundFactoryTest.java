package tfw.swing.event;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.swing.JButton;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import tfw.awt.ecd.ColorECD;
import tfw.swing.JButtonBBTestApplication;
import tfw.tsm.Branch;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
class SetBackgroundFactoryTest {
    private static final String TEST_NAME = "TestName";
    private static final String BUTTON_NAME = "TestButton";
    private static final String FRAME_NAME = "TestFrame";
    private static final ColorECD COLOR_ECD = new ColorECD("TestColor");

    private Branch branch;
    private JButtonBBTestApplication frame;
    private FrameFixture window;

    @BeforeAll
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeEach
    public void setUp() {
        branch = new Branch(FRAME_NAME);
        frame = GuiActionRunner.execute(() -> new JButtonBBTestApplication(branch));
        window = new FrameFixture(frame);
        window.show();
    }

    @Test
    void testArguments() {
        final JButton jButton = window.button(BUTTON_NAME).target();

        assertThrows(IllegalArgumentException.class, () -> SetBackgroundFactory.create(null, COLOR_ECD, jButton, null));
        assertThrows(IllegalArgumentException.class, () -> SetBackgroundFactory.create(TEST_NAME, null, jButton, null));
        assertThrows(
                IllegalArgumentException.class, () -> SetBackgroundFactory.create(TEST_NAME, COLOR_ECD, null, null));
    }

    @AfterEach
    public void tearDown() {
        window.cleanUp();
    }
}