import java.io.*;
import java.util.Base64;

import com.whylogs.core.ColumnProfile;
import com.whylogs.core.message.DatasetProfileMessage;
import lombok.val;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/*
    usage:
        java
 */

public class Main {
    public static void main(String[] args) {
        // create the Options
        Options options = new Options();
        options.addOption(new Option("c", "column", true, "column feature to extract."));

        // create the command line parser
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            for (val fname : cmd.getArgList()) {
                Main.ingest(fname, cmd.getOptionValue("c"));
            }
        } catch (ParseException e) {
            throw new RuntimeException("parse exception", e);
        }
    }

    public static void ingest(String fname, String colName) {
        DatasetProfileMessage msg;
        try {
            msg = DatasetProfileMessage.parseDelimitedFrom(new FileInputStream(fname));
        } catch (IOException e) {
            System.out.println(e.toString());
            return;
        }
        val m = msg.getColumnsMap();
        if (colName == null || colName.equals("")) {
            for (val e : m.entrySet()) {
                val colProfile = ColumnProfile.fromProtobuf(e.getValue());
                val theta = colProfile.getNumberTracker().getThetaSketch();
                val encoded = Base64.getEncoder().encodeToString(theta.toByteArray());
                System.out.printf("%s\t%s\n", e.getKey(), encoded);
            }
        } else {
            val colmsg = m.get(colName);
            if (colmsg == null) {
                return;
            }
            val colProfile = ColumnProfile.fromProtobuf(colmsg);
            val theta = colProfile.getNumberTracker().getThetaSketch();
            val encoded = Base64.getEncoder().encodeToString(theta.toByteArray());
            System.out.printf("%s\t%s\n", colName, encoded);
        }
    }
}

