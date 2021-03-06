/**************************************************************************
 *  Copyright (C) 2013 Atlas of Living Australia
 *  All Rights Reserved.
 * 
 *  The contents of this file are subject to the Mozilla Public
 *  License Version 1.1 (the "License"); you may not use this file
 *  except in compliance with the License. You may obtain a copy of
 *  the License at http://www.mozilla.org/MPL/
 * 
 *  Software distributed under the License is distributed on an "AS
 *  IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 *  implied. See the License for the specific language governing
 *  rights and limitations under the License.
 ***************************************************************************/
package au.org.ala.biocache.writer;

import au.org.ala.biocache.RecordWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.OutputStream;

/**
 * 
 * A Writer that outputs a record in CSV format
 * 
 * @author Natasha Carter
 */
public class TSVRecordWriter implements RecordWriter{
    private final static Logger logger = LoggerFactory.getLogger(TSVRecordWriter.class);

    private OutputStream outputStream;

    public TSVRecordWriter(OutputStream out, String[] header){
        this.outputStream = out;
        write(header);
    }
    
    /**
     * Writes the supplied record to output stream  
     */
    @Override
    public void write(String[] record) {
        StringBuilder line = new StringBuilder();

        //assume correct column count
        for (int i = 0; i < record.length; i++) {
            if (i > 0) line.append('\t');
            line.append(record[i].replace("\r","").replace("\n","").replace("\t",""));
        }

        line.append("\n");

        try {
            outputStream.write(line.toString().getBytes());
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
        }
    }

    @Override
    public void finalise() {
        try {
            outputStream.flush();
        } catch(java.io.IOException e){
            logger.debug(e.getMessage(), e);
        }
    }
}
