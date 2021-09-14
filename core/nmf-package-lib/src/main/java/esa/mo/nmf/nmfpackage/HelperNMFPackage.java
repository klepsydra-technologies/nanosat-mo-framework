/* ----------------------------------------------------------------------------
 * Copyright (C) 2021      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : ESA NanoSat MO Framework
 * ----------------------------------------------------------------------------
 * Licensed under European Space Agency Public License (ESA-PL) Weak Copyleft – v2.4
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package esa.mo.nmf.nmfpackage;

import esa.mo.helpertools.helpers.HelperMisc;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.CRC32;

/**
 *
 * @author Cesar Coelho
 */
public class HelperNMFPackage {

    public static final String RECEIPT_FILENAME = "nmfPackage.receipt";
    public static final String DS_FILENAME = "digitalSignature.key";
    public static final String PRIVATE_KEY_FILENAME = "privateKey.key";
    public static final String NMF_PACKAGE_DESCRIPTOR_VERSION = "NMFPackageDescriptorVersion=";

    public static long calculateCRCFromFile(final String filepath) throws IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(filepath));
        long crc = calculateCRCFromInputStream(inputStream);
        inputStream.close();
        return crc;
    }

    public static long calculateCRCFromInputStream(final InputStream inputStream) throws IOException {
        CRC32 crc = new CRC32();
        int cnt;

        while ((cnt = inputStream.read()) != -1) {
            crc.update(cnt);
        }

        return crc.getValue();
    }
    
    private static String getBanner(){
        StringBuilder str = new StringBuilder();
        str.append("#######################################################\n");
        str.append("#  This file was auto-generated by the NMF\n");
        str.append("#  during the installation of this App\n");
        str.append("#  We do not advise to change it directly!\n");
        str.append("#  The file is generated by the class: HelperNMFPackage\n");
        str.append("#######################################################\n");
        str.append("\n");
        return str.toString();
    }

    public static String generateLinuxStartAppScript(String mainclass, String jarFilename) throws IOException {
        StringBuilder str = new StringBuilder();
        str.append("#!/bin/sh\n");
        str.append(getBanner());
        str.append("cd ${0%/*}\n");
        str.append("\n");

        str.append("# Constants set at installation time:\n");
        str.append("JAVA_CMD=java\n");
        str.append("MAIN_JAR_NAME=").append(jarFilename).append("\n");
        str.append("MAIN_CLASS_NAME=").append(mainclass).append("\n");
        // The NMF_LIB must be also hard-coded! The following code must be changed:
        str.append("NMF_LIB=").append("`cd ../../libs > /dev/null; pwd`").append("\n");
        str.append("\n");

        str.append("if [ -z \"$JAVA_OPTS\" ] ; then\n");
        str.append("    JAVA_OPTS=\"-Xms32m -Xmx256m\"\n");
        str.append("fi\n");
        str.append("\n");

        str.append("export JAVA_OPTS\n");
        str.append("export NMF_LIB\n");
        str.append("\n");

        str.append("exec $JAVA_CMD $JAVA_OPTS \\\n");
        str.append("  -classpath \"$NMF_LIB/*:$MAIN_JAR_NAME\" \\\n");
        str.append("  \"$MAIN_CLASS_NAME\" \\\n");
        str.append("  \"$@\"\n");

        return str.toString();
    }
    
    public static String generateProviderProperties(String runAs) throws IOException {
        StringBuilder str = new StringBuilder();
        str.append(getBanner());
        str.append("# MO App configurations\n");
        str.append(HelperMisc.PROP_ORGANIZATION_NAME).append("=").append("esa\n");
        str.append(HelperMisc.APP_CATEGORY).append("=").append("NMF_App\n");
        if(runAs != null){
            str.append(HelperMisc.APP_USER).append("=").append(runAs).append("\n");
        }
        str.append("\n");

        str.append("# Enables the new Home directory mode: \n");
        str.append(HelperMisc.PROP_WORK_DIR_STORAGE_MODE + "=" + "2\n");
        str.append("\n");
        str.append("# NanoSat MO Framework transport configuration\n");
        str.append("helpertools.configurations.provider.transportfilepath=transport.properties\n");
        str.append("\n");
        str.append("# NanoSat MO Framework Settings\n");
        str.append("esa.mo.nanosatmoframework.provider.settings=settings.properties\n");
        str.append("\n");
        str.append("# NanoSat MO Framework dynamic configurations\n");
        str.append("esa.mo.nanosatmoframework.provider.dynamicchanges=true\n");
        str.append("\n");
        str.append("# Archive flag to drop the table\n");
        str.append("esa.mo.com.impl.provider.ArchiveManager.droptable=false\n");
        str.append("\n");
        str.append("# Store STDERR and STDOUT of apps in the COM Archive\n");
        str.append("esa.mo.nanosatmoframework.appslauncher.stdstore=true\n");
        str.append("\n");
        str.append("# Limit of the STDERR+STDOUT data per app, in kBytes\n");
        str.append("esa.mo.nanosatmoframework.appslauncher.stdlimit=2048\n");
        str.append("\n");
        str.append("# Cleanup space archive from synchronised objects\n");
        str.append("esa.mo.nanosatmoframework.archivesync.purgearchive=true\n");

        return str.toString();
    }

    public static String generateTransportProperties() throws IOException {
        StringBuilder str = new StringBuilder();
        str.append(getBanner());
        str.append("# The following property sets the protocol to be used:\n");
        str.append("org.ccsds.moims.mo.mal.transport.default.protocol = maltcp://\n");
        str.append("\n");

        str.append("# TCP/IP protocol properties:\n");
        str.append("org.ccsds.moims.mo.mal.transport.protocol.maltcp=esa.mo.mal.transport.tcpip.TCPIPTransportFactoryImpl\n");
        str.append("org.ccsds.moims.mo.mal.encoding.protocol.maltcp=esa.mo.mal.encoder.binary.fixed.FixedBinaryStreamFactory\n");
        
        // Bind to localhost
        str.append("org.ccsds.moims.mo.mal.transport.tcpip.autohost=false\n");
        str.append("org.ccsds.moims.mo.mal.transport.tcpip.host=localhost\n");
        str.append("org.ccsds.moims.mo.mal.transport.tcpip.port=1025\n");

        /*
        str.append("org.ccsds.moims.mo.mal.transport.tcpip.autohost=true\n");
        str.append("#org.ccsds.moims.mo.mal.transport.tcpip.host=xxx.xxx.xxx.xxx\n");
        str.append("#org.ccsds.moims.mo.mal.transport.tcpip.port=54321\n");
        */

        return str.toString();
    }

    public static File findAppJarInFolder(File folder) throws IOException {
        File[] fList = folder.listFiles();
        ArrayList<File> possibleOptions = new ArrayList();

        for (File file : fList) {
            if (file.isDirectory()) {
                continue; // Jump over if it is a directory
            }

            if (!file.getAbsolutePath().endsWith(".jar")) {
                continue; // It is not a Jar file
            }

            possibleOptions.add(file);
        }

        if (possibleOptions.isEmpty()) {
            throw new IOException("Not found!");
        }

        if (possibleOptions.size() == 1) {
            return possibleOptions.get(0);
        }

        if (possibleOptions.size() == 2) {
            for (File option : possibleOptions) {
                if (option.getName().contains("-jar-with-dependencies.jar")) {
                    return option;
                }
            }
        }

        throw new IOException("There are too many jars inside the target folder!");
    }    
}
