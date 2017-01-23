package com.eduonix

import java.util.Date

import akka.actor.{Actor, ActorSystem, Props}
import org.mashupbots.socko.events.HttpRequestEvent
import org.mashupbots.socko.infrastructure.Logger
import org.mashupbots.socko.routes.{GET, Routes}
import org.mashupbots.socko.webserver.{WebServer, WebServerConfig}

object SockoServerApp extends Logger {

  val actorSystem = ActorSystem("DateServiceActorSystem")
  val routes = Routes ({
    case GET(request) => {
      actorSystem.actorOf(Props[ServiceHandler]) ! request
    }
  })

  def main(args: Array[String]): Unit = {
    val webServer = new WebServer(WebServerConfig(), routes, actorSystem)
    webServer.start()

    Runtime.getRuntime.addShutdownHook(new Thread {
      override def run {webServer.stop()}
    })

    println("Open your browser and navigate to http://localhost:8888")
  }
}

class ServiceHandler extends Actor {
  def receive = {
    case event: HttpRequestEvent =>
      event.response.write("Date Service (" + new Date().toString + ")")
      context.stop((self))
  }
}