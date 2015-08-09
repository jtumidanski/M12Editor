/**
 * The ByteReader class.
 */
public class ByteReader
{
    /**
     * Reads a UInt.
     *
     * @param aBytes  : The bytes
     * @param aOffset : The offset.
     * @return : The UInt.
     */
    public static long readUInt(byte[] aBytes, int aOffset)
    {
        return Integer.toUnsignedLong(readUByte(aBytes, aOffset + 3) | (readUByte(aBytes, aOffset + 2) << 8) |
                (readUByte(aBytes, aOffset + 1) << 16) | (readUByte(aBytes, aOffset) << 24));
    }

    /**
     * Reads a UShort.
     *
     * @param aBytes  : The bytes.
     * @param aOffset : The offset.
     * @return : The UShort.
     */
    public static long readUShort(byte[] aBytes, int aOffset)
    {
        return Integer.toUnsignedLong(readUByte(aBytes, aOffset + 1) | (readUByte(aBytes, aOffset) << 8));
    }

    /**
     * Reads a UByte.
     *
     * @param aBytes  : The bytes.
     * @param aOffset : The offset.
     * @return : The UByte.
     */
    public static int readUByte(byte[] aBytes, int aOffset)
    {
        return Byte.toUnsignedInt(aBytes[aOffset]);
    }
}
