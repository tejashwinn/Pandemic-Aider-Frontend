package pandemic.aider.server.service;

import java.nio.charset.StandardCharsets;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class HuffmanCoding {
	public static void main(String[] ags) {
		try {
			// Encode a String into bytes
			String inputString =
					"""
									"
									blafsadfjdsa;flkjdaflkjd
											l;k   fj ruei       rjf
											nds    afbavaiofuhejwa
											uhvjnmk       ddufidjhblahblahf"fdsafdsf
									"
							""";

//			inputString = inputString.strip();
			
			byte[] input = inputString.getBytes(StandardCharsets.UTF_8);
			// Compress the bytes
			byte[] output = new byte[1000];
			Deflater compressor = new Deflater();
			compressor.setInput(input);
			compressor.finish();
			int compressedDataLength = compressor.deflate(output);
			System.out.println(compressedDataLength);
			compressor.end();
			
			// Decompress the bytes
			Inflater deCompressor = new Inflater();
//			deCompressor.setInput();
			deCompressor.setInput(output, 0, compressedDataLength);
			byte[] result = new byte[1000];
			int resultLength = deCompressor.inflate(result);
			
			deCompressor.end();
			// Decode the bytes into a String
			String outputString = new String(result, 0, resultLength, StandardCharsets.UTF_8);
			
			System.out.println(inputString);
			System.out.println(outputString);
			
			System.out.println(inputString.length());
			System.out.println(outputString.length());
		} catch (DataFormatException ex) {
			// handle
		}
	}
}
