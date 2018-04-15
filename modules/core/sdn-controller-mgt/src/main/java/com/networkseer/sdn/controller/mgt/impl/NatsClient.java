package com.networkseer.sdn.controller.mgt.impl;

import com.networkseer.sdn.controller.mgt.internal.SdnControllerDataHolder;
import io.nats.client.Connection;
import io.nats.client.Nats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class NatsClient implements Runnable {
	private String url;
	private static final String NATS_URL_PREFIX = "nats://";
	private static final String FAUCET_SOCKET_STREAM_SUBJECT = "faucet.sock.stream";
	private static final Logger log = LoggerFactory.getLogger(NatsClient.class);
	private static final int DEFAULT_RETRY_INTERVAL = 10000;
	private Connection subscriber;

	public NatsClient() {
		url = NATS_URL_PREFIX + SdnControllerDataHolder.getController().getHostname() + ":" +
				SdnControllerDataHolder.getController().getPort();

	}

	private void connectAndSubscribe() throws IOException {
		subscriber = Nats.connect(url);
		subscriber.subscribe(FAUCET_SOCKET_STREAM_SUBJECT, m -> {
			log.info("Received a message: " + new String(m.getData()));
		});
	}

	public void publish(String subject, String msg) throws IOException {
		Connection publisher = Nats.connect(url);
		publisher.publish(subject, msg.getBytes());
		publisher.close();
	}

	public void disconnect() {
		if (subscriber != null && !subscriber.isClosed()) {
			subscriber.close();
		}
	}

	@Override
	public void run() {
		boolean isConnected = false;
		while(!isConnected) {
			try {
				connectAndSubscribe();
				isConnected = true;
			} catch (IOException e) {
				log.error("Failed to subscribe... retrying", e);
				try {
					Thread.sleep(DEFAULT_RETRY_INTERVAL);
				} catch (InterruptedException e1) {
					Thread.interrupted();
				}
			}
		}
	}
}