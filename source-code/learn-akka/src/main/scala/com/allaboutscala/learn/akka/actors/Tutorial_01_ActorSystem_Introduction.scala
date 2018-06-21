package com.allaboutscala.learn.akka.actors

import akka.actor.ActorSystem

import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

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
object Tutorial_01_ActorSystem_Introduction extends App {

  println("Step 1: create an actor system")
  val system = ActorSystem("DonutStoreActorSystem")



  println("\nStep 2: close the actor system")
  val isTerminated = system.terminate()



  println("\nStep 3: Check the status of the actor system")
  isTerminated.onComplete {
    case Success(result) => println("Successfully terminated actor system")
    case Failure(e)     => println("Failed to terminate actor system")
  }
  Thread.sleep(5000)
}
