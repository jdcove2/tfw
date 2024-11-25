package tfw.immutable.iis;

public final class IisFromIlaUtil {
    private IisFromIlaUtil() {}

    public static int read(final long ilaLength, final long index, final int length) {
        if (index == ilaLength) {
            return -1;
        }

        return (int) Math.min(ilaLength - index, length);
    }

    public static long skip(final long ilaLength, final long index, final long n) {
        if (index == ilaLength) {
            return -1;
        }

        final long originalIndex = index;

        final long newIndex = Math.min(ilaLength, index + n);

        return newIndex - originalIndex;
    }
}
