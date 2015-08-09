/**
 * The BitArray class.
 */
public class BitArray
{
    /**
     * The Bit array.
     */
    private char[] mBitArray;

    /**
     * Initializes the BitArray.
     *
     * @param aData : The data.
     */
    public BitArray(int[] aData)
    {
        mBitArray = new char[aData.length * 8];

        for (int i = 0; i < aData.length; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                int lValue = aData[i];
                mBitArray[(i * 8) + j] = (char) ((lValue >> j) & 1);
            }
        }
    }

    /**
     * Gets the bit at the index.
     *
     * @param aIndex : The index.
     * @return : The bit.
     */
    public char getBit(int aIndex)
    {
        return mBitArray[aIndex];
    }
}
