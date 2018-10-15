package com.allaboutscala.learn.akka.dispatchers

import akka.actor.ActorSystem

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
object Tutorial_01_AkkaDefaultDispatcher extends App {

  println("Step 1: Create an actor system")
  val system = ActorSystem("DonutStoreActorSystem")


  println("\nStep 2: Akka default dispatcher config")
  val defaultDispatcherConfig = system.settings.config.getConfig("akka.actor.default-dispatcher")
  println(s"akka.actor.default-dispatcher = $defaultDispatcherConfig")



  println("\nStep 3: Akka default dispatcher type")
  val dispatcherType = defaultDispatcherConfig.getString("type")
  println(s"$dispatcherType")



  println("\nStep 4: Akka default dispatcher throughput")
  val dispatcherThroughput = defaultDispatcherConfig.getString("throughput")
  println(s"$dispatcherThroughput")



  println("\nStep 5: Akka default dispatcher minimum parallelism")
  val dispatcherParallelismMin = defaultDispatcherConfig.getInt("fork-join-executor.parallelism-min")
  println(s"$dispatcherParallelismMin")


  println("\nStep 6: Akka default dispatcher maximum parallelism")
  val dispatcherParallelismMax = defaultDispatcherConfig.getInt("fork-join-executor.parallelism-max")
  println(s"$dispatcherParallelismMax")



  println("\nStep 7: Close the actor system")
  val isTerminated = system.terminate()
}
