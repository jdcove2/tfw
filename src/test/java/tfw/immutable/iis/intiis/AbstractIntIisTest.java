package tfw.immutable.iis.intiis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.Test;

final class AbstractIntIisTest {
    @Test
    void argumentsTest() throws IOException {
        try (TestIntIis ti = new TestIntIis()) {
            final int[] array = new int[11];

            assertThatThrownBy(() -> ti.read(null, 0, 1)).isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> ti.read(array, -1, 1)).isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> ti.read(array, 0, -1)).isInstanceOf(IllegalArgumentException.class);
            assertThat(ti.read(array, 0, 0)).isEqualTo(0);
            assertThat(ti.read(array, 0, 1)).isEqualTo(1);
            assertThat(ti.skip(0)).isEqualTo(0);
            assertThat(ti.skip(1)).isEqualTo(1);
        }
    }

    @Test
    void closedTest() throws IOException {
        final TestIntIis ti = new TestIntIis();
        final int[] array = new int[11];

        ti.close();

        assertThat(ti.isCloseCalled()).isTrue();
        assertThat(ti.read(array, 0, array.length)).isEqualTo(-1);
        assertThat(ti.skip(10)).isEqualTo(-1);

        ti.setCloseCalled(false);
        ti.close();

        assertThat(ti.isCloseCalled()).isFalse();
    }

    private static class TestIntIis extends AbstractIntIis {
        private boolean closeCalled = false;

        public void setCloseCalled(final boolean closeCalled) {
            this.closeCalled = closeCalled;
        }

        public boolean isCloseCalled() {
            return closeCalled;
        }

        @Override
        protected void closeImpl() throws IOException {
            closeCalled = true;
        }

        @Override
        protected int readImpl(int[] array, int offset, int length) throws IOException {
            return length;
        }

        @Override
        protected long skipImpl(long n) throws IOException {
            return n;
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
