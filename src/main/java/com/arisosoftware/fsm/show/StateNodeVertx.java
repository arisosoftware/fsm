package com.arisosoftware.fsm.show;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class StateNodeVertx extends AbstractVerticle {
 
  @Override
  public void start() {
	  EventBus eb = vertx.eventBus();
	  eb.consumer("news.uk.sport", message -> {
	    System.out.println("I have received a message: " + message.body());
	  });
  }
}

//https://stackoverflow.com/questions/27075516/how-to-run-vert-x-verticle-on-eclipse
// Run -> run config, set run class as 
//io.vertx.core.Launcher
// then in argument  run com.arisosoftware.fsm.MainVerticle