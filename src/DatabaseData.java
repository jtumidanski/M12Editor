/**
 * The DatabaseData class.
 */
public class DatabaseData
{
    /**
     * The string value.
     */
    private String mString;

    /**
     * The long value.
     */
    private long mLong;

    /**
     * The unsigned byte array.
     */
    private int[] mArray;

    /**
     * Initializes the DatabaseData.
     *
     * @param aMaddenField : The MaddenField.
     * @param aData        : The data.
     * @param aBitArray    : The BitArray.
     */
    public DatabaseData(MaddenField aMaddenField, int[] aData, BitArray aBitArray)
    {
        switch ((int) aMaddenField.getType())
        {
            case MaddenFieldType.STRING:
                mString = readString(aMaddenField, aData);
                break;
            case MaddenFieldType.BINARY:
                mArray = readBytes(aMaddenField, aData);
                break;
            case MaddenFieldType.SINT:
            case MaddenFieldType.UINT:
            case MaddenFieldType.FLOAT:
                mLong = readBits(aMaddenField.getOffset(), aMaddenField.getBits(), aBitArray);
                break;
        }
    }

    /**
     * Reads a string value.
     *
     * @param aMaddenField : The MaddenField.
     * @param aData        : The data.
     * @return : The string value.
     */
    private String readString(MaddenField aMaddenField, int[] aData)
    {
        StringBuilder lStringBuilder = new StringBuilder();
        for (int i = 0; i < (int) (aMaddenField.getBits() / 8); i++)
        {
            int lUnsignedByte = aData[(int) (aMaddenField.getOffset() / 8) + i];
            if (lUnsignedByte != 0)
            {
                lStringBuilder.append((char) lUnsignedByte);
            }
        }
        return lStringBuilder.toString();
    }

    /**
     * Reads a long value.
     *
     * @param aOffset     : The offset to read from.
     * @param aBitsToRead : The number of bytes to read.
     * @param aBitArray   : The bits to read.
     * @return : The long value.
     */
    private long readBits(long aOffset, long aBitsToRead, BitArray aBitArray)
    {
        long lReturn = 0;
        long lMask = 1;

        for (int i = (int) aBitsToRead; i > 0; i--)
        {
            if (aBitArray.getBit(i + (int) +aOffset - 1) == 1)
            {
                lReturn = lReturn | lMask;
            }
            lMask = lMask << 1;
        }

        return lReturn;
    }

    /**
     * Read unsigned byte array.
     *
     * @param aMaddenField : The MaddenField.
     * @param aData        : The data.
     * @return : An unsigned byte array.
     */
    private int[] readBytes(MaddenField aMaddenField, int[] aData)
    {
        int[] lReturnData = new int[(int) aMaddenField.getBits() / 8];
        System.arraycopy(aData, (int) (aMaddenField.getOffset() / 8), lReturnData, 0, lReturnData.length);
        return lReturnData;
    }
}
