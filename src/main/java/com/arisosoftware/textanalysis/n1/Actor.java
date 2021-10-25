package com.arisosoftware.textanalysis.n1;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.vertx.core.AbstractVerticle;

public class Actor extends AbstractVerticle {
	public String Status;

	public int InnerStatus;

	@Override
	public void start() {
		vertx.createHttpServer().requestHandler(req -> req.response().end("Hello Vert.x!")).listen(8080);
	}

}      


