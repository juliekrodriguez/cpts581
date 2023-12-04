package p2Solution;

import java.io.File;

import p2.InvalidHeaderException;
import p2.TarEntry;

public class FileHandler {

	private File file;
	protected TarHeaderSol header;

	public FileHandler(File file) {
		this.file = file;
	}

	/**
	 * Fill in a TarHeader with information from a File.
	 *
	 * @param hdr  The TarHeader to fill in.
	 * @param file The file from which to get the header information.
	 */
	public static void getFileTarHeader(TarHeaderSol hdr, File file) throws InvalidHeaderException {
		file = file;

		// Normalize the file path
		String name = normalizeFilePath(file.getPath());

		// Set file attributes
		setFileAttributes(hdr, file, name);
	}

	public static String normalizeFilePath(String filePath) {
		String osName = System.getProperty("os.name");
		if (osName != null) {
			String win32Prefix = "windows";
			if (osName.toLowerCase().startsWith(win32Prefix)) {
				if (filePath.length() > 2) {
					char ch1 = filePath.charAt(0);
					char ch2 = filePath.charAt(1);
					if (ch2 == ':' && ((ch1 >= 'a' && ch1 <= 'z') || (ch1 >= 'A' && ch1 <= 'Z'))) {
						filePath = filePath.substring(2);
					}
				}
			}
		}
		return filePath.replace(File.separatorChar, '/');
	}

	/**
	 * Return whether or not this entry represents a directory.
	 *
	 * @return True if this entry is a directory.
	 */
	public boolean isDirectory() {
		if (this.file != null)
			return this.file.isDirectory();

		if (this.header != null) {
			if (this.header.linkFlag == TarHeaderSol.LF_DIR)
				return true;

			if (this.header.name.toString().endsWith("/"))
				return true;
		}

		return false;
	}

	private static void setFileAttributes(TarHeaderSol hdr, File file, String name) {
		hdr.linkName = new StringBuffer("");
		hdr.name = new StringBuffer(name);

		if (file.isDirectory()) {
			hdr.mode = 040755;
			hdr.linkFlag = TarHeaderSol.LF_DIR;
			if (hdr.name.charAt(hdr.name.length() - 1) != '/') {
				hdr.name.append("/");
			}
		} else {
			hdr.mode = 0100644;
			hdr.linkFlag = TarHeaderSol.LF_NORMAL;
		}

		hdr.size = file.length();
		hdr.modTime = file.lastModified() / 1000;
		hdr.checkSum = 0;
		hdr.devMajor = 0;
		hdr.devMinor = 0;
	}

	/**
	 * If this entry represents a file, and the file is a directory, return an array
	 * of TarEntries for this entry's children.
	 *
	 * @return An array of TarEntry's for this entry's children.
	 */
	public TarEntry[] getDirectoryEntries() throws InvalidHeaderException {
		if (this.file == null || !this.file.isDirectory()) {
			return new TarEntry[0];
		}

		String[] list = this.file.list();

		TarEntry[] result = new TarEntry[list.length];

		for (int i = 0; i < list.length; ++i) {
			result[i] = new TarEntry(new File(this.file, list[i]));
		}

		return result;
	}

}
