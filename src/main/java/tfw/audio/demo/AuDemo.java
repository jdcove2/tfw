package tfw.audio.demo;

import java.io.File;
import java.io.IOException;
import tfw.audio.au.Au;
import tfw.audio.au.NormalizedDoubleIlaFromAuAudioData;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromFile;
import tfw.immutable.ila.doubleila.DoubleIla;

public class AuDemo {

    public static void main(String[] args) throws DataInvalidException, IOException {
        File file = new File(args[0]);
        ByteIla byteIla = ByteIlaFromFile.create(file);
        Au auFileFormat = new Au(byteIla);

        System.out.println("filename = " + args[0]);
        System.out.println("file.length = " + file.length());
        System.out.println("magicNumber=" + Long.toHexString(auFileFormat.magicNumber));
        System.out.println("offset = " + auFileFormat.offset);
        System.out.println("data_size = " + auFileFormat.data_size);
        System.out.println("encoding = " + auFileFormat.encoding);
        System.out.println("sampleRate = " + auFileFormat.sampleRate);
        System.out.println("numberOfChannels = " + auFileFormat.numberOfChannels);
        System.out.println("annotation = " + new String(auFileFormat.annotation.toArray()));
        System.out.println("data.length = " + auFileFormat.audioData.length());

        DoubleIla normalizedData = NormalizedDoubleIlaFromAuAudioData.create(
                auFileFormat.audioData, auFileFormat.magicNumber, auFileFormat.encoding);

        System.out.println("normalizedData = " + normalizedData);

        if (normalizedData != null) {
            double[] d = normalizedData.toArray(0, 10);
            for (int i = 0; i < d.length; i++) {
                System.out.println("  nD[" + i + "]=" + d[i]);
            }
        }
    }
}
