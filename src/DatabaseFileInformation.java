import java.util.LinkedList;
import java.util.List;

/**
 * The database file information.
 */
public class DatabaseFileInformation
{
    /**
     * The file header.
     */
    private short mHeader;

    /**
     * The file version.
     */
    private short mVersion;

    /**
     * Not sure what this is.
     */
    private int mUnknown1;

    /**
     * The database size.
     */
    private int mDatabaseSize;

    /**
     * The zero.
     */
    private int mZero;

    /**
     * The number of tables in the database.
     */
    private int mTableCount;

    /**
     * Not sure what this is.
     */
    private int mUnknown2;

    /**
     * The database tables.
     */
    private List<DatabaseTable> mTables;

    /**
     * Initializes the Database File Information object.
     *
     * @param aFileBytes : The roster file.
     */
    public DatabaseFileInformation(byte[] aFileBytes)
    {
        mTables = new LinkedList<>();

        mHeader = (short) (((0xFF) & aFileBytes[1]) | (((0xFF) & aFileBytes[0]) << 8));
        mVersion = (short) (((0xFF) & aFileBytes[3]) | (((0xFF) & aFileBytes[2]) << 8));
        mUnknown1 = ((0xFF) & aFileBytes[7]) | (((0xFF) & aFileBytes[6]) << 8) | (((0xFF) & aFileBytes[5]) <<
                16) | (((0xFF) & aFileBytes[4]) << 24);
        mDatabaseSize = ((0xFF) & aFileBytes[11]) | (((0xFF) & aFileBytes[10]) << 8) | (((0xFF) & aFileBytes[9]) <<
                16) | (((0xFF) & aFileBytes[8]) << 24);
        mZero = ((0xFF) & aFileBytes[15]) | (((0xFF) & aFileBytes[14]) << 8) | (((0xFF) & aFileBytes[13]) <<
                16) | (((0xFF) & aFileBytes[12]) << 24);
        mTableCount = ((0xFF) & aFileBytes[19]) | (((0xFF) & aFileBytes[18]) << 8) | (((0xFF) & aFileBytes[17]) <<
                16) | (((0xFF) & aFileBytes[16]) << 24);
        mUnknown2 = ((0xFF) & aFileBytes[23]) | (((0xFF) & aFileBytes[22]) << 8) | (((0xFF) & aFileBytes[21]) <<
                16) | (((0xFF) & aFileBytes[20]) << 24);

        int lTableDefinitionPosition = 24;
        int lTableDataStart = lTableDefinitionPosition + (mTableCount * 8);

        // Read the table definitions.
        for (int i = 0; i < mTableCount; i++)
        {
            DatabaseTable lDatabaseTable = new DatabaseTable();
            lDatabaseTable.readTableDefinition(aFileBytes, lTableDefinitionPosition);
            lDatabaseTable.readTableHeader(aFileBytes, lTableDataStart);
            mTables.add(lDatabaseTable);
            lTableDefinitionPosition += 8;
        }
    }

    /**
     * Gets the DatabaseTables.
     *
     * @return : The list of DatabaseTables.
     */
    public List<DatabaseTable> getTables()
    {
        return mTables;
    }
}
