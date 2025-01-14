package tfw.immutable.iis.longiis;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIla;

public final class LongIisFactoryFromLongIla {
    private LongIisFactoryFromLongIla() {}

    public static LongIisFactory create(final LongIla ila) {
        return new LongIisFactoryImpl(ila);
    }

    private static class LongIisFactoryImpl implements LongIisFactory {
        private final LongIla ila;

        public LongIisFactoryImpl(final LongIla ila) {
            Argument.assertNotNull(ila, "ila");

            this.ila = ila;
        }

        @Override
        public LongIis create() {
            return LongIisFromLongIla.create(ila);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
