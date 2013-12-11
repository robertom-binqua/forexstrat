package com.binqua.forexstrat.feedreader.core.client;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;

public class HttpClientManager {

    private final PoolingClientConnectionManager httpConnectionManager = new PoolingClientConnectionManager();

    private final int httpConnectionTimeout;
    private final int socketTimeout;

    public HttpClientManager(int numberOfConnections, int httpConnectionTimeout, int socketTimeout) {
        this.httpConnectionTimeout = httpConnectionTimeout;
        this.socketTimeout = socketTimeout;
        
        httpConnectionManager.setMaxTotal(numberOfConnections);
        httpConnectionManager.setDefaultMaxPerRoute(numberOfConnections);
    }

    public HttpClient createAHttpClient() {
        final HttpParams params = new BasicHttpParams();
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, httpConnectionTimeout);
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, socketTimeout);
        return new DefaultHttpClient(httpConnectionManager, params);
    }

    public void shutdown() {
        httpConnectionManager.shutdown();
    }
}
