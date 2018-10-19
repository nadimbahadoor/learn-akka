package com.allaboutscala.learn.akka.dispatchers

import java.util.concurrent.TimeUnit

import akka.actor.{ActorLogging, Actor, Props, ActorSystem}
import akka.util.Timeout
import scala.concurrent.duration._

import scala.concurrent.{Future, Await}

/**
  * Created by Nadim Bahadoor on 28/06/2016.
  *
  *  Tutorial: Learn How To Use Akka
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
object Tutorial_03_AkkaFixedThreadPoolDispatcher extends App {
  println("Step 1: Create actor system")
  val system = ActorSystem("DonutStoreActorSystem")



  println("\nStep 2: Create fixed thread pool configuration in application.conf")



  println("Step 3: Lookup our fixed-thread-pool dispatcher from application.conf")
  import DonutStoreProtocol._
  import akka.pattern._
  implicit val timeout = Timeout(1, TimeUnit.MINUTES)
  implicit val executionContext = system.dispatchers.lookup("fixed-thread-pool")


  println("\nStep 6: Create 10 requests using pool-size = 10")
  println("\nStep 7: Create 10 requests using pool-size = 5")
  val clientRequests = (1 to 10).map(i => StockRequest(s"vanilla donut", i))
  val futures = clientRequests.map{ stock =>
    val actorRef = system
      .actorOf(Props[DonutStockRequestActor]
        .withDispatcher("fixed-thread-pool"))
    (actorRef ? stock).mapTo[DonutStockRequest]
  }
  val results = Await.result(Future.sequence(futures), 1 minute)
  results.foreach(println(_))



  println("\nStep 8: Close actor system")
  system.terminate()



  println("\nStep 4: Create protocol")
  object DonutStoreProtocol {
    case class StockRequest(name: String, clientId: Int)

    trait Result
    case class DonutStockRequest(quantity: Int) extends Result
    case class DonutFailure(msg: String) extends Result
  }



  println("\nStep 5: Create DonutStockRequestActor")
  class DonutStockRequestActor extends Actor with ActorLogging {
    val randomStock = scala.util.Random
    def receive = {
      case StockRequest(name, clientId) =>
        log.info(s"CHECKING: donut stock for name = $name, clientId = $clientId")
        Thread.sleep(5000)
        log.info(s"FINISHED: donut stock for name = $name, clientId = $clientId")
        sender() ! DonutStockRequest(randomStock.nextInt(100))
    }
  }
}
