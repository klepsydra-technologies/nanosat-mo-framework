/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO TCP/IP Transport Framework
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
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
package esa.mo.mal.transport.tcpip;

import esa.mo.mal.encoder.tcpip.TCPIPFixedBinaryStreamFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;

import esa.mo.mal.transport.gen.GENMessage;
import esa.mo.mal.transport.gen.GENMessageHeader;
import static esa.mo.mal.transport.tcpip.TCPIPTransport.RLOGGER;

/**
 * This TCPIP implementation of MAL Message provides encoding methods for
 * encoding the MAL Message according to the TCPIP Transport Binding red book.
 * 
 * @author Rian van Gijlswijk <r.vangijlswijk@telespazio-vega.de>
 *
 */
public class TCPIPMessage extends GENMessage {
    
        private MALElementStreamFactory newHeaderStreamFactory = new TCPIPFixedBinaryStreamFactory();  // Header must be always Fixed Binary

        public TCPIPMessage(boolean wrapBodyParts,
			GENMessageHeader header, Map qosProperties, byte[] packet,
			MALElementStreamFactory encFactory) throws MALException {
		super(wrapBodyParts, true, header, qosProperties, packet, encFactory);
	}
	
	public TCPIPMessage(boolean wrapBodyParts, GENMessageHeader header, Map qosProperties, MALOperation operation,
	          MALElementStreamFactory encFactory, Object... body) throws MALInteractionException {
		super(wrapBodyParts, header, qosProperties, operation, encFactory, body);
	}
	
	/**
	 * Encode a MAL Message.
	 * 
	 * Header and body are encoded separately, each with their own separate
	 * stream Factory. This is done because the header needs to be encoded
	 * according to the specifications in the TCPIP Transport Binding red book,
	 * but the body can be whatever.
	 * 
	 * @param bodyStreamFactory
	 *            the stream factory to use for body encoding
	 * @param lowLevelOutputStream
	 *            the stream onto which both the encoded head and body will be written
	 * @throws MALException
	 *             if encoding failed
	 */
        public void encodeMessage(final MALElementStreamFactory bodyStreamFactory,
			final OutputStream lowLevelOutputStream)
			throws MALException {
		
		// encode header and body using TCPIPEncoder class
		ByteArrayOutputStream hdrBaos = new ByteArrayOutputStream();
		ByteArrayOutputStream bodyBaos = new ByteArrayOutputStream();
		MALElementOutputStream headerEncoder = newHeaderStreamFactory.createOutputStream(hdrBaos);
		MALElementOutputStream bodyEncoder = bodyStreamFactory.createOutputStream(bodyBaos);

		super.encodeMessage(newHeaderStreamFactory, headerEncoder, hdrBaos, true);
		super.encodeMessage(bodyStreamFactory, bodyEncoder, bodyBaos, false);
		
//		int hdrSize = hdrBaos.size();
		byte[] hdrBuf = hdrBaos.toByteArray();	
		byte[] bodyBuf = bodyBaos.toByteArray();			

		// overwrite bodysize parameter
		int totalMessageLength = hdrBaos.size() + bodyBaos.size();
		int bodySize = totalMessageLength - 23;
		ByteBuffer b = ByteBuffer.allocate(4);
		b.order(ByteOrder.BIG_ENDIAN);
		b.putInt(bodySize);
		byte[] bodySizeBuf = b.array();
		
		System.arraycopy(bodySizeBuf, 0, hdrBuf, 19, 4);
		
                /* For debug, if necessary
		StringBuilder sb = new StringBuilder();		
		sb.append("\nHeader: sz=" + hdrBuf.length + " contents=\n");
		for (byte b2 : hdrBuf) {
			sb.append(Integer.toString(b2 & 0xFF, 10) + " ");
		}
		sb.append("\nBody: sz=" + bodyBuf.length + " contents=\n");
		for (byte b2 : bodyBuf) {
			sb.append(Integer.toString(b2 & 0xFF, 10) + " ");
		}
		RLOGGER.log(Level.FINEST, sb.toString());
                */

		try {
			lowLevelOutputStream.write(hdrBuf);
			if (this.getBody() != null) { 
				lowLevelOutputStream.write(bodyBuf);
			}
		} catch (IOException e) {
			RLOGGER.warning("An IOException was thrown during message encoding! " + e.getMessage());
			throw new MALException(e.getMessage());
		}
	}

	public String toString() {
		return "TCPIPMessage {URIFrom:" 
			+ header.getURIFrom() 
			+ "URITo:" + header.getURITo()
			+ "}";		
	}
	
        /**
         * Ok for debugging but don't put it nowhere near the final code. Performance!
         * 
         * @return
         */
        public String bodytoString() {
		
		if (this.body != null) {
			StringBuilder output = new StringBuilder();
			output.append(this.body.getClass().getCanonicalName());
			for (int i=0; i < this.body.getElementCount(); i++) {
				try {
					if (this.body.getBodyElement(i, null) != null) {
						output.append(" | ");
						output.append(this.body.getBodyElement(i, null).toString());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return output.toString();
		}
		return " --no body--";
	}
}
