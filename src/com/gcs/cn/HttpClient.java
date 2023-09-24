package com.gcs.cn;

import java.net.MalformedURLException;
import java.net.URL;

public class HttpClient {

	private String url;
	private String[] headers;
	private boolean verbose;
	private String query;
	private String postBody;
	private String host;
	private String path;

	public HttpClient() {
		// TODO Auto-generated constructor stub
	}
	
	public HttpClient(String url, String[] headers, boolean verbose, String postBody) {
		this.headers = headers;
		this.verbose = verbose;
		this.postBody = postBody;
		parseURL(url);
	}

	
	private void parseURL(String url) {
		try {
			URL strURL = new URL(url);
			this.host = strURL.getHost();
			this.query = strURL.getQuery();
			this.path = strURL.getPath();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public HttpClient(String url, String[] headers, boolean verbose) {
		this(url, headers, verbose,null);
	}

	public void get() {
		
		
	}

}
