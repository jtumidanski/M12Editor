/**
 * The Database table.
 */
public class DatabaseTable
{
    /**
     * The number of bytes in the table header.
     */
    private final int mTableHeaderSize = 40;
    /**
     * The table name.
     */
    private String mTableName;
    /**
     * The offset from end of table index.
     */
    private long mOffsetFromIndex;
    /**
     * The check sum.
     */
    private long mPriorCRC;
    /**
     * Not sure what this is.
     */
    private long mUnknown1;
    /**
     * The length of each record in bytes.
     */
    private long mLengthBytes;
    /**
     * The length of each record in bits.
     */
    private long mLengthBits;
    /**
     * The zero.
     */
    private long mZero;
    /**
     * The maximum number of records.
     */
    private int mMaxRecords;
    /**
     * The current number of records.
     */
    private int mCurrentRecords;
    /**
     * Not sure what this is.
     */
    private long mUnknown2;
    /**
     * The number of fields.
     */
    private int mNumberOfFields;
    /**
     * The index count.
     */
    private int mIndexCount;
    /**
     * The Zero2.
     */
    private int mZero2;
    /**
     * The Zero3.
     */
    private long mZero3;
    /**
     * The header check sum.
     */
    private long mHeaderCRC;
    /**
     * The table field start offset.
     */
    private long mFieldStart;
    /**
     * The table data start offset.
     */
    private long mDataStart;
    /**
     * The calculated header CRC.
     */
    private long mCalculatedHeaderCRC = 0;

    /**
     * Initializes the Database table.
     */
    public DatabaseTable()
    {
        mTableName = "";
        mOffsetFromIndex = 0;
        mPriorCRC = 0;
        mUnknown1 = 0;
        mLengthBytes = 0;
        mLengthBits = 0;
        mZero = 0;
        mMaxRecords = 0;
        mCurrentRecords = 0;
        mUnknown2 = 0;
        mNumberOfFields = 0;
        mIndexCount = 0;
        mZero2 = 0;
        mZero3 = 0;
        mHeaderCRC = 0;
    }

    /**
     * Reads the table definition.
     *
     * @param aFileBytes    : The file.
     * @param aFilePosition : The position to read from.
     */
    public void readTableDefinition(byte[] aFileBytes, int aFilePosition)
    {
        mTableName = String.valueOf((char) aFileBytes[aFilePosition + 3]) + (char) aFileBytes[aFilePosition + 2] +
                (char) aFileBytes[aFilePosition + 1] + (char) aFileBytes[aFilePosition];
        mOffsetFromIndex = ByteReader.readUInt(aFileBytes, aFilePosition + 4);
    }

    /**
     * Reads the table header.
     *
     * @param aFileBytes    : The file.
     * @param aFilePosition : The position to read from.
     */
    public void readTableHeader(byte[] aFileBytes, int aFilePosition)
    {
        int lFilePosition = aFilePosition + ((int) mOffsetFromIndex);

        mPriorCRC = ByteReader.readUInt(aFileBytes, lFilePosition);
        mUnknown1 = ByteReader.readUInt(aFileBytes, lFilePosition + 4);
        mLengthBytes = ByteReader.readUInt(aFileBytes, lFilePosition + 8);
        mLengthBits = ByteReader.readUInt(aFileBytes, lFilePosition + 12);
        mZero = ByteReader.readUInt(aFileBytes, lFilePosition + 16);
        mMaxRecords = (int) ByteReader.readUShort(aFileBytes, lFilePosition + 20);
        mCurrentRecords = (int) ByteReader.readUShort(aFileBytes, lFilePosition + 22);
        mUnknown2 = ByteReader.readUInt(aFileBytes, lFilePosition + 24);
        mNumberOfFields = ByteReader.readUByte(aFileBytes, lFilePosition + 28);
        mIndexCount = ByteReader.readUByte(aFileBytes, lFilePosition + 29);
        mZero2 = (int) ByteReader.readUShort(aFileBytes, lFilePosition + 30);
        mZero3 = ByteReader.readUShort(aFileBytes, lFilePosition + 32);
        mHeaderCRC = ByteReader.readUInt(aFileBytes, lFilePosition + 36);

        mFieldStart = aFilePosition + mOffsetFromIndex + mTableHeaderSize;
        mDataStart = mFieldStart + (mNumberOfFields * 16);

        DatabaseCRC lDatabaseCRC = new DatabaseCRC();
        mCalculatedHeaderCRC = Integer.toUnsignedLong((int) ~lDatabaseCRC.createCRC(aFileBytes, mTableHeaderSize - 8,
                lFilePosition + 4));
    }

    /**
     * Gets the table name.
     *
     * @return : The table name.
     */
    public String getTableName()
    {
        return mTableName;
    }

    /**
     * Gets the table header offset.
     *
     * @return : The offset.
     */
    public long getOffsetFromIndex()
    {
        return mOffsetFromIndex;
    }

    /**
     * Gets the byte length for each record.
     *
     * @return : The byte length.
     */
    public long getLengthBytes()
    {
        return mLengthBytes;
    }

    /**
     * Gets the number of records in this table.
     *
     * @return : The number of records.
     */
    public long getCurrentRecords()
    {
        return mCurrentRecords;
    }

    /**
     * Gets the number of fields in this table.
     *
     * @return : The number of fields.
     */
    public int getNumberOfFields()
    {
        return mNumberOfFields;
    }

    /**
     * Gets the field start offset.
     *
     * @return : The field start offset.
     */
    public long getFieldStart()
    {
        return mFieldStart;
    }

    /**
     * Gets the data start offset.
     *
     * @return : The data start offset.
     */
    public long getDataStart()
    {
        return mDataStart;
    }
}
