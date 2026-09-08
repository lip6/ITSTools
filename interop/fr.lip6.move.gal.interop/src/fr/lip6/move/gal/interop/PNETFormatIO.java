package fr.lip6.move.gal.interop;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import fr.lip6.move.gal.structural.NetBlock;
import fr.lip6.move.gal.structural.SparsePetriNet;
import java.util.Map.Entry;
import java.util.Map;
import java.nio.charset.StandardCharsets;
import java.io.ByteArrayOutputStream;
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
		if (net instanceof SparsePetriNet spn) {
			writeBlocks(spn.getBlocks(), out);
		}
	}

	/**
	 * The optional named blocks that follow the three mandatory ones: an 8-byte
	 * zero-padded ASCII name, the payload's byte length, then an ordinary KERS
	 * payload (PetriSpot INTEROP.md section 3). A reader that does not know a
	 * name skips it by its length, so nothing here needs a version or a flag;
	 * a net with nothing to declare writes nothing.
	 */
	public static void writeBlocks(Map<NetBlock, IntMatrixCol> blocks, DataOutputStream out) throws IOException {
		for (Entry<NetBlock, IntMatrixCol> block : blocks.entrySet()) {
			byte[] name = block.getKey().blockName().getBytes(StandardCharsets.US_ASCII);
			if (name.length == 0 || name.length > 8) {
				throw new IOException("A PNET block name is 1 to 8 characters: " + block.getKey().blockName());
			}
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			try (DataOutputStream payload = new DataOutputStream(buffer)) {
				KERSFormatIO.write(block.getValue(), payload);
			}
			byte[] bytes = buffer.toByteArray();
			out.write(name);
			for (int i = name.length; i < 8; i++) {
				out.writeByte(0);
			}
			KERSFormatIO.writeIntLE(out, bytes.length);
			out.write(bytes);
		}
	}
}
