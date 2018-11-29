package com.allaboutscala.learn.akka.fsm

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

import scala.io.StdIn


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
object Tutorial_11_AkkaScheduler extends App {

  println("Step 1. Create ActorSystem")
  val system = ActorSystem("ActorState")



  println("\nStep 3: Create DonutBakingActor")
  val bakingActor = system.actorOf(Props[DonutBakingActor], "donut-baking-actor")



  println("\nStep 4: Send events to actor to switch states and process events")
  bakingActor ! "BakeDonut"
  Thread.sleep(2000)



  println("\nStep 5: Using system.scheduler to send periodic events to actor")
  import system.dispatcher
  import scala.concurrent.duration._

  system.scheduler.schedule(1.seconds, 2.seconds) {
    bakingActor ! "BakeVanilla"
    Thread.sleep(1000)
  }

  StdIn.readLine()



  println("\nStep 2: Define DonutBakingActor with become() and unbecome() states")
  class DonutBakingActor extends Actor with ActorLogging {
    import context._

    def receive = {
      case "BakeDonut" =>
        log.info("becoming bake state")
        become {
          case "BakeVanilla" =>
            log.info("baking vanilla")

          case "BakePlain" =>
            log.info("baking plain")

          case "StopBaking" =>
            log.info("stopping to bake")
            unbecome()

          case event @ _ =>
            log.info(s"Allowed events [BakeVanilla, BakePlain, StopBaking], event = $event")
        }

      case event @ _ =>
        log.info(s"Allowed events [BakeDonut], event = $event")
    }
  }
}
