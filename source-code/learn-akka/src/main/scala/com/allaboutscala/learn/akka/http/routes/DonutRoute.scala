package com.allaboutscala.learn.akka.http.routes

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.allaboutscala.learn.akka.http.jsonsupport.{Donut, Donuts, JsonSupport}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future
import scala.util.{Try, Success, Failure}

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
class DonutRoutes extends JsonSupport with LazyLogging {

  val donutDao = new DonutDao()

  def route(): Route = {
    path("create-donut") {
      post {
        entity(as[Donut]) { donut =>
          logger.info(s"creating donut = $donut")
          complete(StatusCodes.Created, s"Created donut = $donut")
        }
      } ~ delete {
          complete(StatusCodes.MethodNotAllowed, "The HTTP DELETE operation is not allowed for the create-donut path.")
        }
      } ~ path("donuts") {
        get {
          onSuccess(donutDao.fetchDonuts()){ donuts =>
            complete(StatusCodes.OK, donuts)
          }
        }
      } ~ path("donuts-with-future-success-failure") {
        get {
          onComplete(donutDao.fetchDonuts()) {
            case Success(donuts) => complete(StatusCodes.OK, donuts)
            case Failure(ex) => complete(s"Failed to fetch donuts = ${ex.getMessage}")
          }
        }
      } ~ path("complete-with-http-response") {
        get {
          complete(HttpResponse(status = StatusCodes.OK, entity = "Using an HttpResponse object"))
        }
      } ~ path("donut-with-try-httpresponse") {
        get {
          val result: HttpResponse = donutDao.tryFetchDonuts().getOrElse(donutDao.defaultResponse())
          complete(result)
        }
      } ~ path("akka-http-failwith") {
        get {
          failWith(new RuntimeException("Boom"))
        }
    }
  }
}

class DonutDao {
  import scala.concurrent.ExecutionContext.Implicits.global

  val donutsFromDb = Vector(
    Donut("Plain Donut", 1.50),
    Donut("Chocolate Donut", 2),
    Donut("Glazed Donut", 2.50)
  )

  def fetchDonuts(): Future[Donuts] = Future {
    Donuts(donutsFromDb)
  }

  def tryFetchDonuts(): Try[HttpResponse] = Try {
    throw new IllegalStateException("Boom!")
  }

  def defaultResponse(): HttpResponse =
    HttpResponse(
      status = StatusCodes.NotFound,
      entity = "An unexpected error occurred. Please try again.")
}