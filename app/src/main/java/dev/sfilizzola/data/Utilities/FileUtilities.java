
package dev.sfilizzola.data.Utilities;


public class FileUtilities {

    private static long kilobyte = 1024;
    private static long megabyte = 1024 * kilobyte;
    private static long gigabyte = 1024 * megabyte;
    private static long terabyte = 1024 * gigabyte;

    public static String ToByteString(long bytes) {

        if (bytes > terabyte)
            return String.format("%1$.2f TB", (float)bytes / (float)terabyte);
        else if (bytes > gigabyte)
            return String.format("%1$.2f GB", (float)bytes / (float)gigabyte);
        else if (bytes > megabyte)
            return String.format("%1$.2f MB", (float)bytes / (float)megabyte);
        else if (bytes > kilobyte)
            return String.format("%1$.2f KB", (float)bytes / (float)kilobyte);
        else
            return bytes + " Bytes";
    }
}
