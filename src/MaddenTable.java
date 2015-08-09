import java.util.LinkedList;
import java.util.List;

/**
 * The Madden Table class.
 */
public class MaddenTable
{
    /**
     * The database table.
     */
    private DatabaseTable mDatabaseTable;

    /**
     * The list of fields in this table.
     */
    private List<MaddenField> mFields;

    /**
     * The list of records in this table.
     */
    private List<MaddenRecord> mRecords;

    /**
     * Initializes the Madden table.
     *
     * @param aDatabaseTable : The DatabaseTable.
     */
    public MaddenTable(DatabaseTable aDatabaseTable)
    {
        mDatabaseTable = aDatabaseTable;
        mFields = new LinkedList<>();
        mRecords = new LinkedList<>();
    }

    /**
     * Reads the fields of the MaddenTable.
     *
     * @param aFileBytes : The file.
     */
    public void readFields(byte[] aFileBytes)
    {
        for (int i = 0; i < mDatabaseTable.getNumberOfFields(); i++)
        {
            MaddenField lMaddenField = new MaddenField();
            lMaddenField.readField(aFileBytes, (int) (mDatabaseTable.getFieldStart() + (i * 16)));
            mFields.add(lMaddenField);
        }
    }

    /**
     * Reads the records of the MaddenTable.
     *
     * @param aFileBytes : The file.
     */
    public void readRecords(byte[] aFileBytes)
    {
        for (int i = 0; i < mDatabaseTable.getCurrentRecords(); i++)
        {
            MaddenRecord lMaddenRecord = new MaddenRecord();
            lMaddenRecord.readRecord(this, aFileBytes, i);
            mRecords.add(lMaddenRecord);
        }
    }

    /**
     * Gets the DatabaseTable.
     *
     * @return : The DatabaseTable.
     */
    public DatabaseTable getDatabaseTable()
    {
        return mDatabaseTable;
    }

    /**
     * Gets the table fields.
     *
     * @return : The madden fields.
     */
    public List<MaddenField> getFields()
    {
        return mFields;
    }
}
