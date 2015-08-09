import org.apache.log4j.Logger;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * The Madden Database class.
 */
public class MaddenDatabase
{
    /**
     * The output logger.
     */
    private Logger mLogger = Logger.getLogger(MaddenDatabase.class.getName());

    /**
     * The file bytes.
     */
    private byte[] mFileBytes;

    /**
     * The type of roster file.
     */
    private MaddenFileType mType;

    /**
     * The list of Madden Database Tables.
     */
    private List<MaddenTable> mTables;

    /**
     * Initializes the Madden Database object using a file.
     *
     * @param aFile : The database file.
     */
    public MaddenDatabase(File aFile)
    {
        InputStream lInputStream;

        // Create a FileInputStream.
        try
        {
            lInputStream = new FileInputStream(aFile);
            mLogger.debug("FileInputStream created.");
        }
        catch (FileNotFoundException aException)
        {
            mLogger.fatal(aException.getMessage());
            return;
        }

        int lFileLength = (int) aFile.length();
        int lBytesRead;
        mFileBytes = new byte[lFileLength];

        // Read the file into memory.
        try
        {
            lBytesRead = lInputStream.read(mFileBytes, 0, mFileBytes.length);
            mLogger.debug("Read [" + lBytesRead + "] bytes from file. Expected [" + lFileLength + "] bytes");
        }
        catch (IOException aException)
        {
            mLogger.fatal(aException.getMessage());
            return;
        }

        // Validate the entire file was read.
        if (lBytesRead < lFileLength)
        {
            mLogger.fatal("File read did not return full file.");
            return;
        }

        // Check the type of madden file.
        mType = checkFileType();

        // Unknown file. Cannot process.
        if (mType == MaddenFileType.NONE)
        {
            mLogger.fatal("This is not a DB or MC02 file.");
            try
            {
                lInputStream.close();
            }
            catch (IOException aException)
            {
                mLogger.fatal(aException.getMessage());
            }
            return;
        }
        // CON file must be extracted.
        else if (mType == MaddenFileType.CON)
        {
            mLogger.fatal("The roster file must be extracted first.");
            try
            {
                lInputStream.close();
            }
            catch (IOException aException)
            {
                mLogger.fatal(aException.getMessage());
            }
            return;
        }
        // MC02 file
        else if (mType == MaddenFileType.MC02)
        {
            // TODO : Need to implement this.
            mLogger.fatal("Unimplemented MC02 roster");
            try
            {
                lInputStream.close();
            }
            catch (IOException aException)
            {
                mLogger.fatal(aException.getMessage());
            }
            return;
        }
        // DB file.
        else
        {
            mTables = new LinkedList<>();
            DatabaseFileInformation lDatabaseFileInformation = new DatabaseFileInformation(mFileBytes);
            for (DatabaseTable lDatabaseTable : lDatabaseFileInformation.getTables())
            {
                MaddenTable lMaddenTable = new MaddenTable(lDatabaseTable);
                lMaddenTable.readFields(mFileBytes);
                lMaddenTable.readRecords(mFileBytes);
                mTables.add(lMaddenTable);
            }
        }

        // Done using the file. Close it up.
        try
        {
            lInputStream.close();
        }
        catch (IOException aException)
        {
            mLogger.fatal(aException.getMessage());
        }
    }

    /**
     * Looks at the first 4 bytes of the file to determine the file type.
     *
     * @return : The MaddenFileType.
     */
    private MaddenFileType checkFileType()
    {
        // DB File.
        if (mFileBytes[0] == 'D' && mFileBytes[1] == 'B')
        {
            return MaddenFileType.DB;
        }

        // MC02 File.
        if (mFileBytes[0] == 'M' && mFileBytes[1] == 'C' && mFileBytes[2] == '0' && mFileBytes[3] == '2')
        {
            return MaddenFileType.MC02;
        }

        // CON File.
        if (mFileBytes[0] == 'C' && mFileBytes[1] == 'O' && mFileBytes[2] == 'N')
        {
            return MaddenFileType.CON;
        }

        // Unknown File.
        return MaddenFileType.NONE;
    }
}
