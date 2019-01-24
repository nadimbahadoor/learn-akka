package com.allaboutscala.learn.akka.fsm

import akka.actor.{Actor, ActorLogging, ActorSystem}
import akka.testkit.{TestActorRef, DefaultTimeout, ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.util.Success

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
class DonutInfoActorTests
  extends TestKit(ActorSystem("DonutActorTests"))
    with ImplicitSender
    with DefaultTimeout
    with WordSpecLike
    with BeforeAndAfterAll
    with Matchers {

  import DonutStoreProtocol._
  "Sending Tell Pattern Info(vanilla) message to DonutInfoActor" should {
    "reply back with true" in {
      val testActor = TestActorRef[DonutInfoActor]
      testActor ! Info("vanilla")
      expectMsg(true)
    }
  }



  import scala.concurrent.duration._
  "DonutInfoActor" should {
    "respond back within 100 millis" in {
      within(100 millis) {
        val testActor = TestActorRef[DonutInfoActor]
        testActor ! Info("vanilla")
        Thread.sleep(500)
        expectMsg(true)
      }
    }
  }



  "Sending Ask Pattern Info(plain) message to DonutInfoActor" should {
    "reply back with false" in {
      import akka.pattern._
      val testActor = TestActorRef[DonutInfoActor]
      val result = testActor ? Info("plain")
      val Success(reply: Boolean) = result.value.get
      reply shouldBe false
    }
  }



  "Sending a Random Donut message to DonutInfoActor" should {
    "throw IllegalStateException" in {
      val testActor = TestActorRef[DonutInfoActor]
      intercept[IllegalStateException] {
        testActor.receive("Random Donut")
      }
    }
  }



  "The exception message when sending a Random Donut to DonutInfoActor" should {
    "include the words: is not allowed" in {
      val testActor = TestActorRef[DonutInfoActor]
      val exception = the [IllegalStateException] thrownBy {
        testActor.receive("Random Donut")
      }
      exception.getClass shouldEqual classOf[java.lang.IllegalStateException]
      exception.getMessage should be ("Event Random Donut is not allowed")
    }
  }



  override protected def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }
}

object DonutStoreProtocol {
  case class Info(name: String)
}

class DonutInfoActor extends Actor with ActorLogging {
  import DonutStoreProtocol._

  def receive = {
    case Info(name) if name == "vanilla" =>
      log.info(s"Found valid $name donut")
      sender ! true

    case Info(name) =>
      log.info(s"$name donut is not supported")
      sender ! false

    case event @ _ =>
      log.info(s"Event $event is not allowed.")
      throw new IllegalStateException(s"Event $event is not allowed")
  }
}