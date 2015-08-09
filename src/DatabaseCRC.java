/**
 * Calculates the Database CRC.
 */
public class DatabaseCRC
{
    /**
     * The polynomial.
     */
    private int mPolynomial = 0x04C11DB7;

    /**
     * The CRC table.
     */
    private int[] mCRCTable = new int[256];

    /**
     * Initializes the Database CRC.
     */
    public DatabaseCRC()
    {
        initialize();
    }

    /**
     * Initializes the Database CRC with a polynomial.
     *
     * @param aPolynomial : The polynomial.
     */
    public DatabaseCRC(int aPolynomial)
    {
        mPolynomial = aPolynomial;
        initialize();
    }

    /**
     * Creates the Database CRC.
     *
     * @param aFileBytes : The file.
     * @param aLength    : The length of file.
     * @param aStart     : The offset index.
     * @return : The CRC.
     */
    public long createCRC(byte[] aFileBytes, int aLength, int aStart)
    {
        int lIndex = aStart;
        int lCRC = 0;
        lCRC ^= 0xFFFFFFFF;

        while (aLength-- > 0)
        {
            lCRC = (lCRC) ^ (int) aFileBytes[lIndex++] << 24;
            lCRC = ((int) (Integer.toUnsignedLong(lCRC) << 4)) ^ mCRCTable[(int) (Integer.toUnsignedLong(lCRC) >> 28)];
            lCRC = ((int) (Integer.toUnsignedLong(lCRC) << 4)) ^ mCRCTable[(int) (Integer.toUnsignedLong(lCRC) >> 28)];
        }
        return ~lCRC;
    }

    /**
     * Initialize the Database CRC.
     */
    private void initialize()
    {
        int lCRC = 0x80000000;
        mCRCTable[0] = 0;

        for (int i = 1; i < 1 << 4; i <<= 1)
        {
            lCRC = (lCRC << 1) ^ (((lCRC & 0x80000000) != 0) ? mPolynomial : 0);
            for (int j = 0; j < i; j++)
            {
                mCRCTable[i + j] = lCRC ^ mCRCTable[j];
            }
        }
    }
}
