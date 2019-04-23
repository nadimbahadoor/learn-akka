package com.allaboutscala.learn.akka.client

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{MediaTypes, HttpEntity, HttpMethods, HttpRequest}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import akka.util.ByteString
import com.allaboutscala.learn.akka.http.jsonsupport.{Donuts, JsonSupport}

import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
import scala.concurrent.duration._

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
object AkkaHttpClient extends App with JsonSupport {

  implicit val system = ActorSystem("akka-http-donuts-client")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  // HTTP GET request
  val donutsUri = "http://localhost:8080/donuts"
  val donutsHttpRequest = HttpRequest(
    uri = donutsUri,
    method = HttpMethods.GET
  )

  val donutsResponse = Http().singleRequest(donutsHttpRequest)
  donutsResponse
    .onComplete {
      case Success(donutsResponse) =>
        println(s"Raw HttpResponse = $donutsResponse")

        // You obviously should not block using Await.result(...) and use flatMap or other similar future sequencing mechanics
        val donutsF: Future[Donuts] = Unmarshal(donutsResponse).to[Donuts]
        val donuts: Donuts = Await.result(donutsF, 5.second)
        println(s"Unmarshalled HttpResponse to Case Class = $donuts")


      case Failure(e) => println(s"Failed to HTTP GET $donutsUri, error = ${e.getMessage}")
    }

  Thread.sleep(3000)



  // HTTP POST request
  val jsonDonutInput = ByteString("""{"name":"plain donut", "price":1.50}""")
  val httpPostCreateDonut = HttpRequest(
    uri = "http://localhost:8080/create-donut",
    method = HttpMethods.POST,
    entity = HttpEntity(MediaTypes.`application/json`, jsonDonutInput))


  val createDonutF = for {
    response <- Http().singleRequest(httpPostCreateDonut)
    _        = println(s"Akka HTTP request status = ${response.status}")
    if response.status.isSuccess()
    output   <- Unmarshal(response).to[String]
  } yield println(s"HTTP POST request output = $output")

  Await.result(createDonutF, 5.second)


  system.terminate()
}