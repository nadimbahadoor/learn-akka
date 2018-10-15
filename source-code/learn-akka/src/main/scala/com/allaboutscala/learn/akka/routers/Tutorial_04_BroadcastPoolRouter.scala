package com.allaboutscala.learn.akka.routers

import akka.actor.SupervisorStrategy.{Escalate, Restart}
import akka.actor._
import akka.routing.{BroadcastPool, DefaultResizer}
import akka.util.Timeout

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
object Tutorial_04_BroadcastPoolRouter extends App {

  println("Step 1: Create an actor system")
  val system = ActorSystem("DonutStoreActorSystem")



  println("\nStep 2: Define the message passing protocol for our DonutStoreActor")
  object DonutStoreProtocol {
    case class Info(name: String)

    case class CheckStock(name: String)

    case class WorkerFailedException(error: String) extends Exception(error)
  }



  println("\nStep 5: Define DonutStockActor")
  val donutStockActor = system.actorOf(Props[DonutStockActor], name = "DonutStockActor")



  println("\nStep 6: Use Akka Tell Pattern and send a single request to DonutStockActor")
  import DonutStoreProtocol._
  donutStockActor ! CheckStock("vanilla")

  Thread.sleep(5000)



  println("\nStep 7: Close the actor system")
  val isTerminated = system.terminate()



  println("\nStep 3: Create DonutStockActor")
  class DonutStockActor extends Actor with ActorLogging {
    import scala.concurrent.duration._
    implicit val timeout = Timeout(5 second)

    override def supervisorStrategy: SupervisorStrategy =
      OneForOneStrategy(maxNrOfRetries = 3, withinTimeRange = 5 seconds) {
        case _: WorkerFailedException =>
          log.error("Worker failed exception, will restart.")
          Restart

        case _: Exception =>
          log.error("Worker failed, will need to escalate up the hierarchy")
          Escalate
      }

    // We will not create a single worker actor.
    // val workerActor = context.actorOf(Props[DonutStockWorkerActor], name = "DonutStockWorkerActor")

    // We are using a resizable BroadcastPool.
    val workerName = "DonutStockWorkerActor"
    val resizer = DefaultResizer(lowerBound = 5, upperBound = 10)
    val props = BroadcastPool(
      nrOfInstances = 5,
      resizer = None,
      supervisorStrategy = supervisorStrategy
    ).props(Props[DonutStockWorkerActor])

    val donutStockWorkerRouterPool: ActorRef = context.actorOf(props, "DonutStockWorkerRouter")

    def receive = {
      case checkStock @ CheckStock(name) =>
        log.info(s"Checking stock for $name donut")
        donutStockWorkerRouterPool ! checkStock
    }
  }



  println("\ntep 4: Worker Actor called DonutStockWorkerActor")
  class DonutStockWorkerActor extends Actor with ActorLogging {

    override def postRestart(reason: Throwable): Unit = {
      log.info(s"restarting ${self.path.name} because of $reason")
    }

    def receive = {
      case CheckStock(name) =>
        sender ! findStock(name)
    }

    def findStock(name: String): Int = {
      log.info(s"Finding stock for donut = $name, thread = ${Thread.currentThread().getId}")
      100
    }
  }
}
