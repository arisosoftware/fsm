package com.arisosoftware.vert;


import io.vertx.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start() {
    vertx.createHttpServer()
        .requestHandler(req -> req.response().end("Hello Vert.x!"))
        .listen(8080);
  }

}

//https://stackoverflow.com/questions/27075516/how-to-run-vert-x-verticle-on-eclipse
// Run -> run config, set run class as 
//io.vertx.core.Launcher
// then in argument  run com.arisosoftware.fsm.MainVerticle