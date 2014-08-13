
package dev.sfilizzola.data.Utilities;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BitConverter {

    public static byte[] getLengthBytes(int pLenght) {

        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(pLenght).array();
    }

    public static int getLenghtBytes(byte[] buffer) {

        ByteBuffer bf = ByteBuffer.allocate(4).put(buffer, 0, 4).order(ByteOrder.LITTLE_ENDIAN);
        bf.flip();
        return bf.getInt();
    }

}
