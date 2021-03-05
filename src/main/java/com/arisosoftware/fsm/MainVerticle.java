package com.arisosoftware.fsm;


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
