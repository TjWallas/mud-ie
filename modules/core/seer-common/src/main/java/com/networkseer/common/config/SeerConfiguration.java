package com.networkseer.common.config;

import io.dropwizard.Configuration;

import java.util.List;

public class SeerConfiguration extends Configuration {

	private Controller controller;
	private String swagger;
	private List<String> switches;
	private List<MUDController> mudControllers;
	private boolean mudPacketLogging;

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public String getSwagger() {
		return swagger;
	}

	public void setSwagger(String swagger) {
		this.swagger = swagger;
	}

	public List<String> getSwitches() {
		return switches;
	}

	public void setSwitches(List<String> switches) {
		this.switches = switches;
	}

	public List<MUDController> getMudControllers() {
		return mudControllers;
	}

	public void setMudControllers(List<MUDController> mudControllers) {
		this.mudControllers = mudControllers;
	}

	public boolean isMudPacketLogging() {
		return mudPacketLogging;
	}

	public void setMudPacketLogging(boolean mudPacketLogging) {
		this.mudPacketLogging = mudPacketLogging;
	}
}
