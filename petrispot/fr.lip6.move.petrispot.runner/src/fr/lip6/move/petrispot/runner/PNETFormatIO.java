package fr.lip6.move.petrispot.runner;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.util.IntMatrixCol;

/**
 * Writer for PNET, the binary net format read by PetriSpot's {@code --net}
 * option (PetriSpot INTEROP.md section 3, KERS.md).
 *
 * <p>A 16-byte little-endian header (magic "PNET", version 1, flags 0, place
 * count, transition count, 2 bytes padding) followed by three KERS blocks
 * written by {@link KERSFormatIO}: flowPT (places x transitions), flowTP and
 * the initial marking as a one-column matrix. Places and transitions are
 * identified by index on both sides; PetriSpot names them p&lt;i&gt; and t&lt;i&gt;.
 */
public class PNETFormatIO {

	private static final byte[] MAGIC = { 'P', 'N', 'E', 'T' };
	private static final byte VERSION = 1;

	public static void write(ISparsePetriNet net, Path path) throws IOException {
		try (DataOutputStream out = new DataOutputStream(
				new BufferedOutputStream(Files.newOutputStream(path)))) {
			write(net, out);
		}
	}

	public static void write(ISparsePetriNet net, DataOutputStream out) throws IOException {
		int places = net.getPlaceCount();
		int transitions = net.getTransitionCount();
		out.write(MAGIC);
		out.writeByte(VERSION);
		out.writeByte(0); // flags
		KERSFormatIO.writeIntLE(out, places);
		KERSFormatIO.writeIntLE(out, transitions);
		out.writeByte(0); // padding
		out.writeByte(0);
		KERSFormatIO.write(net.getFlowPT(), out);
		KERSFormatIO.write(net.getFlowTP(), out);
		IntMatrixCol marking = new IntMatrixCol(places, 0);
		marking.appendColumn(new SparseIntArray(net.getMarks()));
		KERSFormatIO.write(marking, out);
	}
}
