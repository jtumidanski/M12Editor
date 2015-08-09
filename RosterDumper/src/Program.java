import org.apache.log4j.Logger;

import java.io.File;

/**
 * Dumps the Madden Roster file to a series of comma delimited files.
 */
public class Program
{
    /**
     * The output logger.
     */
    static Logger mLogger = Logger.getLogger(Program.class.getName());

    public static void main(String[] aArguments)
    {
        // Ensure the roster file is supplied.
        if (aArguments.length != 1)
        {
            mLogger.fatal("Invalid number of arguments. Expecting a path to the roster file.");
            return;
        }

        File lFile = new File(aArguments[0]);

        // Validate file exists.
        if (!lFile.exists())
        {
            mLogger.fatal("Supplied file does not exist at path. File=[" + aArguments[0] + "]");
            return;
        }

        // Validate file can be read.
        if (!lFile.canRead())
        {
            mLogger.fatal("Supplied file cannot be read. File=[" + aArguments[0] + "]");
            return;
        }

        // Roster file length must be less than 2,147,483,648. Anything larger is unsupported.
        if (lFile.length() > Integer.MAX_VALUE)
        {
            mLogger.fatal("Unsupported file size.");
            return;
        }

        MaddenDatabase lMaddenDatabase = new MaddenDatabase(lFile);
        mLogger.fatal("hi");
    }
}
