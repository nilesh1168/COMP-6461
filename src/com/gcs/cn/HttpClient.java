package com.gcs.cn;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.SocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpClient {

	private String[] headers;
	private boolean verbose;
	private String query;
	private String postBody;
	private String host;
	private String path;
	private int port;
	private String file;
	private String outputFile;
	private boolean isPost;
	private boolean isGet;
	private boolean redirectionAllowed;

	private static final int PORT = 80;

	private static final int BUFFER_SIZE = 1024;

	public HttpClient(String url, String[] headers, boolean verbose, String postBody, String oPFile, boolean redirectionAllowed, String file) {
		this.headers = headers;
		this.verbose = verbose;
		this.postBody = postBody;
		this.outputFile = oPFile;
		this.redirectionAllowed = redirectionAllowed;
		this.file = file;
		parseURL(url);
	}

	public HttpClient(String url, String[] headers, boolean verbose, String opFile, boolean redirectionAllowed) {
		this(url, headers, verbose, null, opFile, redirectionAllowed, null);
	}
	public HttpClient(String url, String[] headers, boolean verbose, String opFile, boolean redirectionAllowed, String file) {
		this(url, headers, verbose, null, opFile, redirectionAllowed, file);
	}
	
	private void parseURL(String url) {
		try {
			URL strURL = new URL(url);
			this.host = strURL.getHost();
			this.query = strURL.getQuery();
			this.path = strURL.getPath();
			this.port = strURL.getPort();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}


	public void get() {
		isGet = true;
		StringBuilder requestBuilder = new StringBuilder();
		if (this.query == null)
			this.query = "";

		requestBuilder.append("GET ").append(path).append("?").append(query).append(" HTTP/1.1\r\n").append("Host: ")
				.append(host).append("\r\n").append("User-Agent: Concordia-HTTP/1.0\r\n");

		if (headers != null) {
			for (String header : headers) {
				requestBuilder.append(header).append("\r\n");
			}
		}

		requestBuilder.append("\r\n"); // ending the request
		String request = requestBuilder.toString();

		System.out.println("***********GET REQUEST***********");
		System.out.println(request);
		System.out.println("***********RESPONSE***********");
		sendRequestToSocket(request, host);
	}

	private void performRedirection(String[] response) {
		String[] headers = response[0].split("\r\n");
		String locationPrefix = "Location: ";
		for (String headerLine : headers) {
			if (headerLine.contains(locationPrefix)) {
				int locationIndex = headerLine.indexOf(locationPrefix) + locationPrefix.length();
				String newUrl = headerLine.substring(locationIndex);
				// Debug statements
				System.out.println("=============================================");
				System.out.println("Redirection Detected: " + newUrl);
				System.out.println();
				parseURL(newUrl);
				break;
			}
		}

		if (isGet) {
			get();
		} else if (isPost) {
			post();
		}

	}

	private void sendRequestToSocket(String request, String host) {
		if (port == -1) {
			port = PORT;
		}
		SocketAddress endpoint = new InetSocketAddress(host, port);
		try (SocketChannel socket = SocketChannel.open()) {
			socket.connect(endpoint);
			// write the request in
			byte[] bs = request.getBytes(StandardCharsets.UTF_8);
			ByteBuffer byteBuffer = ByteBuffer.wrap(bs);
			socket.write(byteBuffer);

			// read the response from the socket
			ByteBuffer responseBuff = ByteBuffer.allocate(BUFFER_SIZE);

			while (socket.read(responseBuff) > 0) {
				// We can trim the array before we decode
				byte[] responseBuffArr = responseBuff.array();

				// Split response for verbosity
				String line = new String(responseBuffArr, StandardCharsets.UTF_8).trim();
				String[] lines = line.split("\r\n\r\n");
				
				if (HttpcUtil.isRedirect(lines) && redirectionAllowed) {
                    performRedirection(lines);
                    return;
                }
				if (lines.length >= 2) {
					if (verbose && outputFile != null) {
						StringBuilder builder = new StringBuilder();
						builder.append(lines[0]);
						builder.append("\n").append(lines[1]);
						HttpcUtil.saveResponseIntoOutputFile(builder.toString(), outputFile);
					} else if (!verbose && outputFile != null) {
						StringBuilder builder = new StringBuilder();
						builder.append(lines[1]);
						HttpcUtil.saveResponseIntoOutputFile(builder.toString(), outputFile);
					} else if (verbose) {
						System.out.println(lines[0] + "\n" + lines[1]);
					} else
						System.out.println(lines[1]);
				}

			}

			socket.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	public void post() {
		isPost = true;
		StringBuilder reqBuilder = new StringBuilder();
		if (query == null) {
			query = "";
		}
		reqBuilder.append("POST ").append(path).append("?").append(query).append(" HTTP/1.1\r\n").append("Host: ")
				.append(host).append("\r\n").append("User-Agent: Concordia-HTTP/1.0\r\n");

		if (headers != null) {
			for (String header : headers) {
				reqBuilder.append(header.strip()).append("\r\n");
			}
		}

		if (file != null) {
			Path filePath = Path.of(file);
			try {
				// instead of file content save it back to file
				file = Files.readString(filePath).replace("\n", "");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (postBody != null) {
			reqBuilder.append("Content-Length: ").append(postBody.length()).append("\r\n");
		}
		if (file != null) {
			reqBuilder.append("Content-Length: ").append(file.length()).append("\r\n");
		}
		// Ending of the request header
		reqBuilder.append("\r\n");

		// Add the postBody
		if (postBody != null) {
			reqBuilder.append(postBody);
		}
		if (file != null) {
			reqBuilder.append(file);
		}

		System.out.println("***********POST REQUEST***********");
		String request = reqBuilder.toString();
		System.out.println(request);
		System.out.println();
		System.out.println("***********RESPONSE***********");
		sendRequestToSocket(request, host);

	}

}
