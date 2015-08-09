/**
 * The Madden field.
 */
public class MaddenField
{
    /**
     * The field name.
     */
    private String mName;

    /**
     * The field type.
     */
    private long mType;

    /**
     * The field offset.
     */
    private long mOffset;

    /**
     * The field bits.
     */
    private long mBits;

    /**
     * Initializes the MaddenField.
     */
    public MaddenField()
    {
        mName = "";
        mType = 0;
        mOffset = 0;
        mBits = 0;
    }

    /**
     * Reads the field from the file.
     *
     * @param aFileBytes  : The file.
     * @param aFileOffset : The offset bytes.
     */
    public void readField(byte[] aFileBytes, int aFileOffset)
    {
        mType = ByteReader.readUInt(aFileBytes, aFileOffset);
        mOffset = ByteReader.readUInt(aFileBytes, aFileOffset + 4);

        mName = String.valueOf((char) aFileBytes[aFileOffset + 11]) + (char) aFileBytes[aFileOffset + 10] +
                (char) aFileBytes[aFileOffset + 9] + (char) aFileBytes[aFileOffset + 8];

        mBits = ByteReader.readUInt(aFileBytes, aFileOffset + 12);
    }

    /**
     * Gets the field name.
     *
     * @return : The field name.
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Gets the field type.
     *
     * @return : The field type.
     */
    public long getType()
    {
        return mType;
    }

    /**
     * Gets the field offset.
     *
     * @return : The field offset.
     */
    public long getOffset()
    {
        return mOffset;
    }

    /**
     * Gets the field bits.
     *
     * @return : The field bits.
     */
    public long getBits()
    {
        return mBits;
    }
}
