package com.allaboutscala.learn.akka.http

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.allaboutscala.learn.akka.http.routes.DonutRoutes
import org.scalatest.{Matchers, WordSpec}

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
class DonutQueryParametersTest
  extends WordSpec
    with Matchers
    with ScalatestRouteTest {

  val donutRoutes = new DonutRoutes().route()

  "Query Parameters Tests" should {
    "match the output for the URL /donut/prices?donutName" in {
      Get("/donut/prices?donutName=plain%20donut") ~> donutRoutes ~> check {
        responseAs[String] shouldEqual "Received parameter: donutName=plain donut"
        status shouldEqual StatusCodes.OK
      }
    }



    "Check for required donutName query parameter at /donut/prices" in {
      Get("/donut/prices?") ~> Route.seal(donutRoutes) ~> check {
        responseAs[String] shouldEqual "Request is missing required query parameter 'donutName'"
        status shouldEqual StatusCodes.NotFound
      }
    }



    "Validate the pass-through of required and optional parameters in /donut/bake" in {
      Get("/donut/bake?donutName=plain%20donut&topping=chocolate") ~> donutRoutes ~> check {
        responseAs[String] shouldEqual "Received parameters: donutName=plain donut and topping=chocolate"
        status shouldEqual StatusCodes.OK
      }
    }



    "Verify the optional parameter topping for /donut/bake" in {
      Get("/donut/bake?donutName=plain%20donut") ~> donutRoutes ~> check {
        responseAs[String] shouldEqual "Received parameters: donutName=plain donut and topping=sprinkles"
        status shouldEqual StatusCodes.OK
      }
    }



    "Verify typed parameters for /ingredients" in {
      Get("/ingredients?donutName=plain%20donut&priceLevel=1.50") ~> donutRoutes ~> check {
        responseAs[String] shouldEqual "Received parameters: donutName=plain donut, priceLevel=1.5"
        status shouldEqual StatusCodes.OK
      }
    }



    "Check for wrong types being passed through to the priceLevel query param at /ingredients" in {
      Get("/ingredients?donutName=plain%20donut&priceLevel=cheap") ~> Route.seal(donutRoutes) ~> check {
        responseAs[String] shouldEqual """The query parameter 'priceLevel' was malformed:
                                         |'cheap' is not a valid 64-bit floating point value""".stripMargin
        status shouldEqual StatusCodes.BadRequest
      }
    }
  }
}
