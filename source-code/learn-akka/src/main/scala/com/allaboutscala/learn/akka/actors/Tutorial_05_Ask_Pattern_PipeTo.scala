package com.allaboutscala.learn.akka.actors

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.util.Timeout

import scala.concurrent.Future

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
object Tutorial_05_Ask_Pattern_PipeTo extends App {

  println("Step 1: Create an actor system")
  val system = ActorSystem("DonutStoreActorSystem")



  println("\nStep 4: Create DonutStockActor")
  val donutStockActor = system.actorOf(Props[DonutStockActor], name = "DonutStockActor")



  println("\nStep 5: Akka Ask Pattern using mapTo() method")
  import DonutStoreProtocol._
  import akka.pattern._

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._
  implicit val timeout = Timeout(5 second)

  val vanillaDonutStock: Future[Int] = (donutStockActor ? CheckStock("vanilla")).mapTo[Int]

  for {
    found <- vanillaDonutStock
  } yield println(s"Vanilla donut stock = $found")

  Thread.sleep(5000)



  println("\nStep 6: Close the actor system")
  val isTerminated = system.terminate()



  println("\nStep 2: Define the message passing protocol for our DonutStoreActor")
  object DonutStoreProtocol {
    case class Info(name: String)

    case class CheckStock(name: String)
  }



  println("\nStep 3: Create DonutStockActor")
  class DonutStockActor extends Actor with ActorLogging {
    import Tutorial_05_Ask_Pattern_PipeTo.DonutStoreProtocol._

    def receive = {
      case CheckStock(name) =>
        log.info(s"Checking stock for $name donut")
        findStock(name).pipeTo(sender)
    }

    def findStock(name: String): Future[Int] = Future {
      // assume a long running database operation to find stock for the given donut
      100
    }
  }
}
