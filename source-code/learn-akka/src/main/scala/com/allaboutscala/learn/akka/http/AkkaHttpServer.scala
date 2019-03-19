package com.allaboutscala.learn.akka.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer
import com.allaboutscala.learn.akka.http.routes.{DonutRoutes, ServerVersion}
import com.typesafe.scalalogging.LazyLogging

import scala.io.StdIn
import scala.util.{Failure, Success}

/**
  * Created by Nadim Bahadoor on 28/06/2016.
  *
  *  Tutorial: Learn How To Use Akka HTTP
  *
  * [[http://allaboutscala.com/scala-frameworks/akka/]]
  *
  * Copyright 2016 Nadim Bahadoor (http://allaboutscala.com)
  *
  * Licensed under the Apache License, Version 2.0 (the "License"); you may not
  * use this file except in compliance with the License. You may obtain a copy of
  * the License at
  *
  *  [http://www.apache.org/licenses/LICENSE-2.0]
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
  * License for the specific language governing permissions and limitations under
  * the License.
  */
object AkkaHttpServer extends App with LazyLogging {

  implicit val system = ActorSystem("akka-http-rest-server")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  // these should ideally be in some configuration file
  val host = "127.0.0.1"
  val port = 8080

  implicit val globalRejectionHandler =
    RejectionHandler.newBuilder()
      .handle { case ValidationRejection(msg, route) =>
        complete(StatusCodes.InternalServerError, s"The operation is not supported, error = $msg")
      }
      .handleNotFound {
        complete(StatusCodes.NotFound, "The path is not supported.")
      }
      .result()


  implicit val globalExceptionHandler = ExceptionHandler {
    case e: RuntimeException => complete(s"A runtime exception occurred with, msg = ${e.getMessage}")
  }


//  // routes
//  val serverUpRoute: Route = get {
//    complete("Akka HTTP Server is UP.")
//  }

  val serverVersion = new ServerVersion()
  val serverVersionRoute = serverVersion.route()
  val serverVersionRouteAsJson = serverVersion.routeAsJson()
  val serverVersionJsonEncoding = serverVersion.routeAsJsonEncoding()
  val donutRoutes = new DonutRoutes().route()

  val routes: Route =  donutRoutes ~ serverVersionRoute ~ serverVersionRouteAsJson ~ serverVersionJsonEncoding//
  // ~ serverUpRoute

  val httpServerFuture = Http().bindAndHandle(routes, host, port)
  httpServerFuture.onComplete {
    case Success(binding) =>
      logger.info(s"Akka Http Server is UP and is bound to ${binding.localAddress}")

    case Failure(e) =>
      logger.error(s"Akka Http server failed to start", e)
      system.terminate()
  }

  StdIn.readLine() // let it run until user presses return
  httpServerFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
}