import static java.lang.String.format;
import java.io.ByteArrayOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

 public class MINSTReader
 {
        public static final int LABEL_FILE_MAGIC_NUMBER = 2049;
        public static final int IMAGE_FILE_MAGIC_NUMBER = 2051;

        public static int columns,rows;

        public static int[] getLabels(String infile) {

            ByteBuffer bb = loadFileToByteBuffer(infile);

            assertMagicNumber(LABEL_FILE_MAGIC_NUMBER, bb.getInt());

            int numLabels = bb.getInt();
            int[] labels = new int[numLabels];

            for (int i = 0; i < numLabels; ++i)
                labels[i] = bb.get() & 0xFF; // To unsigned

            return labels;
        }

        public static List<int[]> getImages(String infile) {
            ByteBuffer bb = loadFileToByteBuffer(infile);

            assertMagicNumber(IMAGE_FILE_MAGIC_NUMBER, bb.getInt());

            int numImages = bb.getInt();
            int numRows = bb.getInt();
            rows=numRows;
            int numColumns = bb.getInt();
            columns=numColumns;
            List<int[]> images = new ArrayList<>();

            for (int i = 0; i < numImages; i++)
                images.add(readImage(numRows, numColumns, bb));

            return images;
        }

        private static int[] readImage(int numRows, int numCols, ByteBuffer bb) {
            int[] image = new int[numRows*numCols];
            for (int img = 0; img < numRows*numCols; img++)
                image[img] = bb.get() & 0xFF;
            return image;
        }

        public static void assertMagicNumber(int expectedMagicNumber, int magicNumber) {
            if (expectedMagicNumber != magicNumber) {
                switch (expectedMagicNumber) {
                    case LABEL_FILE_MAGIC_NUMBER:
                        throw new RuntimeException("This is not a label file.");
                    case IMAGE_FILE_MAGIC_NUMBER:
                        throw new RuntimeException("This is not an image file.");
                    default:
                        throw new RuntimeException(
                                format("Expected magic number %d, found %d", expectedMagicNumber, magicNumber));
                }
            }
        }

        public static ByteBuffer loadFileToByteBuffer(String infile) {
            return ByteBuffer.wrap(loadFile(infile));
        }

        public static byte[] loadFile(String infile) {
            try {
                RandomAccessFile f = new RandomAccessFile(infile, "r");
                FileChannel chan = f.getChannel();
                long fileSize = chan.size();
                ByteBuffer bb = ByteBuffer.allocate((int) fileSize);
                chan.read(bb);
                bb.flip();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                for (int i = 0; i < fileSize; i++)
                    baos.write(bb.get());
                chan.close();
                f.close();
                return baos.toByteArray();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

