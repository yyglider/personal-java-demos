package socket.client.sender;

import static socket.Commons.CHARSET_START;
import static socket.Commons.SEND_FILE;

import java.io.IOException;

import socket.Commons;
import socket.SocketWrapper;

public class FileSender extends BFileSender {

	private byte charsetByte;

	protected int minLength = 3;

	public FileSender(String[] tokens) throws IOException {
		super(tokens);
		this.charsetByte = Commons
				.getCharsetByteByName(getCharset(tokens[2]));
	}

	private String getCharset(String token) {
		token = token.toLowerCase();
		if (token.startsWith(CHARSET_START)) {
			return token.substring(CHARSET_START.length());
		} else {
			throw new RuntimeException("字符集部分不符合规范.");
		}
	}

	protected void sendCharset(SocketWrapper socketWrapper) throws IOException {
		socketWrapper.write(charsetByte);// 字符集
	}

	@Override
	public byte getSendType() {
		return SEND_FILE;
	}
}
