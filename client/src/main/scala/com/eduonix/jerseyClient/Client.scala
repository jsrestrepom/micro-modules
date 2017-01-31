package com.eduonix.jerseyClient

import javax.ws.rs.client.{ClientBuilder, Entity}
import javax.ws.rs.core.MediaType

class Client (baseUri: String) {
  val client = ClientBuilder.newClient();
  val webTarget = client.target(baseUri);
  val invocationBuilder = webTarget.request(MediaType.TEXT_HTML)
    .accept(MediaType.TEXT_HTML);
  val response = invocationBuilder.post(Entity.text(""))

  val serverResultCode = response.getStatus

  println(s"${serverResultCode}")

  val serverResult = response.readEntity(manifest[String].runtimeClass)
  println(s"${serverResult}")
}

object ClientRunner extends App {
  val client = new Client("http://google.com")
  //change it to http://localhost:8888 to point to the akka-micro
}
