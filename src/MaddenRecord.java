import java.util.LinkedList;
import java.util.List;

/**
 * The MaddenRecord class.
 */
public class MaddenRecord
{
    /**
     * The list of record data.
     */
    private List<DatabaseData> mEntries;

    /**
     * Initializes the MaddenRecord.
     */
    public MaddenRecord()
    {
        mEntries = new LinkedList<>();
    }

    /**
     * Reads the current record.
     *
     * @param aMaddenTable : The MaddenTable.
     * @param aFileBytes   : The file.
     * @param aRecord      : The record number.
     */
    public void readRecord(MaddenTable aMaddenTable, byte[] aFileBytes, int aRecord)
    {
        DatabaseTable lDatabaseTable = aMaddenTable.getDatabaseTable();
        long lFilePosition = lDatabaseTable.getDataStart() + (aRecord * lDatabaseTable.getLengthBytes());
        int[] lReverseBuffer = new int[(int) lDatabaseTable.getLengthBytes()];
        int[] lBuffer = new int[(int) lDatabaseTable.getLengthBytes()];

        // Read the record into lBuffer.
        for (int j = 0; j < lReverseBuffer.length; j++)
        {
            int lByte = Byte.toUnsignedInt(aFileBytes[(int) lFilePosition + j]);
            lBuffer[j] = lByte;
            lReverseBuffer[j] = Integer.reverse(lByte) >> 24;
        }

        BitArray lBitArray = new BitArray(lReverseBuffer);

        // Parse the entries.
        for (MaddenField lMaddenField : aMaddenTable.getFields())
        {
            DatabaseData lDatabaseData = new DatabaseData(lMaddenField, lBuffer, lBitArray);
            mEntries.add(lDatabaseData);
        }
    }
}
