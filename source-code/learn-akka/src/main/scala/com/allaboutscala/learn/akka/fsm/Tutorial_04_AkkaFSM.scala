package com.allaboutscala.learn.akka.fsm

import akka.actor.{LoggingFSM, ActorSystem, Props}

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
object Tutorial_04_AkkaFSM extends App {

  println("Step 1: Create ActorSystem")
  val system = ActorSystem("DonutActorFSM")



  println("\nStep 6: Create DonutBakingActor")
  val bakingActor = system.actorOf(Props[DonutBakingActor], "donut-baking-actor")



  println("\nStep 7: Send events to actor to switch states and process events")
  bakingActor ! AddTopping
  Thread.sleep(2000)



  bakingActor ! BakeDonut
  Thread.sleep(2000)



  println("\nStep 8: Shutdown actor system")
  system.terminate()



  println("\nStep 2: Defining events")
  sealed trait BakingEvents
  case object BakeDonut extends BakingEvents
  case object AddTopping extends BakingEvents



  println("\nStep 3: Defining states")
  sealed trait BakingStates
  case object Start extends BakingStates
  case object Stop extends BakingStates



  println("\nStep 4: Defining mutatable data")
  case class BakingData(qty: Int) {
    def addOneDonut = copy(qty + 1)
  }

  object BakingData {
    def initialQuantity = BakingData(0)
  }


  println("\nStep 5: Define DonutBakingActor using LoggingFSM trait")
  class DonutBakingActor extends LoggingFSM[BakingStates, BakingData] {
    startWith(Stop, BakingData.initialQuantity)

    initialize()

    when(Stop) {
      case Event(BakeDonut, _) =>
        println("Current state is [Stop], switching to [Start]")
        goto(Start).using(stateData.addOneDonut)

      case Event(AddTopping, _) =>
        println("Current state is [Stop], you first need to move to [BakeDonut]")
        stay
    }

    when(Start) {
      case _ => throw new IllegalStateException("stop")
    }
  }
}
