package com.allaboutscala.learn.akka.fsm

import akka.actor.{Props, ActorSystem, Actor}

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
object Tutorial_03_AkkaFSMProtocol extends App {

  println("Step 1. Create ActorSystem")
  val system = ActorSystem("ActorState")



  println("\nStep 4: Create DonutBakingActor")
  val bakingActor = system.actorOf(Props[DonutBakingActor], "donut-baking-actor")
  bakingActor ! "boom" // not valid
  Thread.sleep(2000)



  println("\nStep 5: Send events to actor to switch states and process events")
  bakingActor ! BakeDonut
  Thread.sleep(2000)



  bakingActor ! BakePlain
  Thread.sleep(2000)



  bakingActor ! BakeVanilla
  Thread.sleep(2000)



  bakingActor ! "Bake Chocolate"
  Thread.sleep(2000)



  bakingActor ! StopBaking
  Thread.sleep(2000)



  bakingActor ! BakeVanilla
  Thread.sleep(2000)



  println("\nStep 6: Shutdown actor system")
  system.terminate()



  println("\nStep 2: Define message passing protocol using sealed trait")
  sealed trait DonutProtocol
  case object BakeDonut extends DonutProtocol
  case object BakeVanilla extends DonutProtocol
  case object BakePlain extends DonutProtocol
  case object StopBaking extends DonutProtocol



  println("\nStep 2: Define DonutBakingActor with become() and unbecome() event")
  class DonutBakingActor extends Actor {
    import context._

    def startBaking: Receive = {
      case BakeDonut =>
        println("becoming bake state")
        become(bake)

      case event @ _ =>
        println(s"Allowed event [$BakeDonut], event = $event")
    }

    def bake: Receive = {
      case BakeVanilla =>
        println("baking vanilla")

      case BakePlain =>
        println("baking plain")

      case StopBaking =>
        println("stopping to bake")
        unbecome()

      case event @ _ =>
        println(s"Allowed event [$BakeVanilla, $BakePlain, $StopBaking], event = $event")
    }

    def receive = startBaking
  }
}
