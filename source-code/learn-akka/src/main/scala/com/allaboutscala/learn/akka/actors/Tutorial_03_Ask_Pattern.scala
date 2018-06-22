package com.allaboutscala.learn.akka.actors

import akka.actor.{Props, Actor, ActorLogging, ActorSystem}
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
object Tutorial_03_Ask_Pattern extends App {

  println("Step 1: Create an actor system")
  val system = ActorSystem("DonutStoreActorSystem")



  println("\nStep 4: Create DonutInfoActor")
  val donutInfoActor = system.actorOf(Props[DonutInfoActor], name = "DonutInfoActor")



  println("\nStep 5: Akka Ask Pattern")
  import DonutStoreProtocol._
  import akka.pattern._
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._
  implicit val timeout = Timeout(5 second)

  val vanillaDonutFound = donutInfoActor ? Info("vanilla")
  for {
    found <- vanillaDonutFound
  } yield println(s"Vanilla donut found = $found")

  val glazedDonutFound = donutInfoActor ? Info("glazed")
  for {
    found <- glazedDonutFound
  } yield println(s"Glazed donut found = $found")

  Thread.sleep(5000)



  println("\nStep 6: Close the actor system")
  val isTerminated = system.terminate()


  println("\nStep 2: Define the message passing protocol for our DonutStoreActor")
  object DonutStoreProtocol {
    case class Info(name: String)
  }



  println("\nStep 3: Create DonutInfoActor")
  class DonutInfoActor extends Actor with ActorLogging {
    import Tutorial_03_Ask_Pattern.DonutStoreProtocol._

    def receive = {
      case Info(name) if name == "vanilla" =>
        log.info(s"Found valid $name donut")
        sender ! true

      case Info(name) =>
        log.info(s"$name donut is not supported")
        sender ! false
    }
  }

}
