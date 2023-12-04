/*
** Authored by Timothy Gerard Endres
** <mailto:time@gjt.org>  <http://www.trustice.com>
** 
** This work has been placed into the public domain.
** You may use this work in any way and for any purpose you wish.
**
** THIS SOFTWARE IS PROVIDED AS-IS WITHOUT WARRANTY OF ANY KIND,
** NOT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY. THE AUTHOR
** OF THIS SOFTWARE, ASSUMES _NO_ RESPONSIBILITY FOR ANY
** CONSEQUENCE RESULTING FROM THE USE, MODIFICATION, OR
** REDISTRIBUTION OF THIS SOFTWARE. 
** 
*/

package p2Solution;

/**
 * This class encapsulates the Tar Entry Header used in Tar Archives. The class
 * also holds a number of tar constants, used mostly in headers.
 */

public class TarHeaderSol extends Object {
	/**
	 * The length of the name field in a header buffer.
	 */
	public static final int NAMELEN = 100;
	/**
	 * The length of the mode field in a header buffer.
	 */
	public static final int MODELEN = 8;
	/**
	 * The length of the user id field in a header buffer.
	 */
	public static final int UIDLEN = 8;
	/**
	 * The length of the group id field in a header buffer.
	 */
	public static final int GIDLEN = 8;
	/**
	 * The length of the checksum field in a header buffer.
	 */
	public static final int CHKSUMLEN = 8;
	/**
	 * The length of the size field in a header buffer.
	 */
	public static final int SIZELEN = 12;
	/**
	 * The length of the magic field in a header buffer.
	 */
	public static final int MAGICLEN = 8;
	/**
	 * The length of the modification time field in a header buffer.
	 */
	public static final int MODTIMELEN = 12;
	/**
	 * The length of the user name field in a header buffer.
	 */
	public static final int UNAMELEN = 32;
	/**
	 * The length of the group name field in a header buffer.
	 */
	public static final int GNAMELEN = 32;
	/**
	 * The length of the devices field in a header buffer.
	 */
	public static final int DEVLEN = 8;

	/**
	 * LF_ constants represent the "link flag" of an entry, or more commonly, the
	 * "entry type". This is the "old way" of indicating a normal file.
	 */
	public static final byte LF_OLDNORM = 0;
	/**
	 * Normal file type.
	 */
	public static final byte LF_NORMAL = (byte) '0';
	/**
	 * Link file type.
	 */
	public static final byte LF_LINK = (byte) '1';
	/**
	 * Symbolic link file type.
	 */
	public static final byte LF_SYMLINK = (byte) '2';
	/**
	 * Character device file type.
	 */
	public static final byte LF_CHR = (byte) '3';
	/**
	 * Block device file type.
	 */
	public static final byte LF_BLK = (byte) '4';
	/**
	 * Directory file type.
	 */
	public static final byte LF_DIR = (byte) '5';
	/**
	 * FIFO (pipe) file type.
	 */
	public static final byte LF_FIFO = (byte) '6';
	/**
	 * Contiguous file type.
	 */
	public static final byte LF_CONTIG = (byte) '7';

	/**
	 * The magic tag representing a POSIX tar archive.
	 */
	public static final String TMAGIC = "ustar";

	/**
	 * The magic tag representing a GNU tar archive.
	 */
	public static final String GNU_TMAGIC = "ustar  ";

	/**
	 * The entry's name.
	 */
	public StringBuffer name;
	/**
	 * The entry's permission mode.
	 */
	public int mode;
	/**
	 * The entry's user id.
	 */
	public int userId;
	/**
	 * The entry's group id.
	 */
	public int groupId;
	/**
	 * The entry's size.
	 */
	public long size;
	/**
	 * The entry's modification time.
	 */
	public long modTime;
	/**
	 * The entry's checksum.
	 */
	public int checkSum;
	/**
	 * The entry's link flag.
	 */
	public byte linkFlag;
	/**
	 * The entry's link name.
	 */
	public StringBuffer linkName;
	/**
	 * The entry's magic tag.
	 */
	public StringBuffer magic;
	/**
	 * The entry's user name.
	 */
	public StringBuffer userName;
	/**
	 * The entry's group name.
	 */
	public StringBuffer groupName;
	/**
	 * The entry's major device number.
	 */
	public int devMajor;
	/**
	 * The entry's minor device number.
	 */
	public int devMinor;

	public TarHeaderSol() {
		this.magic = new StringBuffer(TarHeaderSol.TMAGIC);

		this.name = new StringBuffer();
		this.linkName = new StringBuffer();

		String user = System.getProperty("user.name", "");

		if (user.length() > 31)
			user = user.substring(0, 31);

		this.userId = 0;
		this.groupId = 0;
		this.userName = new StringBuffer(user);
		this.groupName = new StringBuffer("");
	}

	public Object clone() {
		TarHeaderSol hdr = cloneHeader();
		hdr.name = copyStringBuffer(name);
		hdr.linkName = copyStringBuffer(linkName);
		hdr.magic = copyStringBuffer(magic);
		hdr.userName = copyStringBuffer(userName);
		hdr.groupName = copyStringBuffer(groupName);
		return hdr;
	}

	private TarHeaderSol cloneHeader() {
		TarHeaderSol hdr = null;
		try {
			hdr = (TarHeaderSol) super.clone();
			hdr.name = (this.name == null) ? null : new StringBuffer(this.name.toString());
			hdr.mode = this.mode;
			hdr.userId = this.userId;
			hdr.groupId = this.groupId;
			hdr.size = this.size;
			hdr.modTime = this.modTime;
			hdr.checkSum = this.checkSum;
			hdr.linkFlag = this.linkFlag;
			hdr.devMajor = this.devMajor;
			hdr.devMinor = this.devMinor;
		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		}
		return hdr;
	}

	private StringBuffer copyStringBuffer(StringBuffer buffer) {
		return (buffer == null) ? null : new StringBuffer(buffer.toString());
	}

	/**
	 * Get the name of this entry.
	 *
	 * @return Teh entry's name.
	 */
	public String getName() {
		return this.name.toString();
	}

	// REFACTORING BEGINS

	public static int getNameBytes(StringBuffer name, byte[] buf, int offset, int length) {
		return TarHeaderFormatter.getNameBytes(name, buf, offset, length);
	}

	public static int getOctalBytes(long value, byte[] buf, int offset, int length) {
		return TarHeaderFormatter.getOctalBytes(value, buf, offset, length); // Delegate to TarHeaderFormatter
	}

	public static StringBuffer parseName(byte[] header, int offset, int length) {
		return TarHeaderParser.parseName(header, offset, length); // Delegate to TarHeaderParser
	}

	public static int getCheckSumOctalBytes(long value, byte[] buf, int offset, int length) {
		return TarHeaderFormatter.getCheckSumOctalBytes(value, buf, offset, length);
	}

	public static long parseOctal(byte[] zeroBytes, int i, int length) {
		try {
			return TarHeaderParser.parseOctal(zeroBytes, i, length);
		} catch (InvalidHeaderException e) {
			throw new RuntimeException("Error parsing octal value", e);
		}
	}

	public static int getLongOctalBytes(long size2, byte[] outbuf, int offset, int sizelen2) {
		return TarHeaderFormatter.getLongOctalBytes(size2, outbuf, offset, sizelen2);
	}

}
