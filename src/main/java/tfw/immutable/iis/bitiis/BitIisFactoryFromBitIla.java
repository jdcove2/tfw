package tfw.immutable.iis.bitiis;

import tfw.check.Argument;
import tfw.immutable.ila.bitila.BitIla;

public final class BitIisFactoryFromBitIla {
    private BitIisFactoryFromBitIla() {}

    public static BitIisFactory create(final BitIla ila) {
        return new BitIisFactoryImpl(ila);
    }

    private static class BitIisFactoryImpl implements BitIisFactory {
        private final BitIla ila;

        public BitIisFactoryImpl(final BitIla ila) {
            Argument.assertNotNull(ila, "ila");

            this.ila = ila;
        }

        @Override
        public BitIis create() {
            return BitIisFromBitIla.create(ila);
        }
    }
}
