# Source Code For Scala Tutorials From http://allaboutscala.com/scala-frameworks/akka/
## Introduction
The Scala programming language is rapidly growing in popularity! Sadly, most of the online tutorials do not provide a step-by-step guide :(
 
At www.allaboutscala.com, we provide a complete beginner's tutorial to help you learn Scala in **small**, **simple** and **easy steps**.

- Tutorials are organized such that they allow you to **learn gradually**.
- Tutorials are written by taking into account the **complete beginner**.
- Tutorials will make you proficient with the same professional **tools used by the Scala experts**.

# Learn Akka
- Code snippets for [Learn Akka](http://allaboutscala.com/scala-frameworks/akka/)
- For additional details, please visit www.allaboutscala.com

The examples below are the source code for Akka Tutorials from [allaboutscala.com](http://allaboutscala.com/scala-frameworks/akka/)

# Introduction
- [What is Akka](http://allaboutscala.com/scala-frameworks/akka/#what-is-akka)
- [Project setup build.sbt](http://allaboutscala.com/scala-frameworks/akka/#project-setup-build-sbt)

# Akka Actors
- [Actor System Introduction](http://allaboutscala.com/scala-frameworks/akka/#actor-system-introduction)
- [Tell Pattern](http://allaboutscala.com/scala-frameworks/akka/#tell-pattern)
- [Ask Pattern](http://allaboutscala.com/scala-frameworks/akka/#ask-pattern)
- [Ask Pattern mapTo](http://allaboutscala.com/scala-frameworks/akka/#ask-pattern-mapto)
- [Ask Pattern pipeTo](http://allaboutscala.com/scala-frameworks/akka/#ask-pattern-pipeto)
- [Actor Hierarchy](http://allaboutscala.com/scala-frameworks/akka/#actor-hierarchy)
- [Actor Lookup](http://allaboutscala.com/scala-frameworks/akka/#actor-lookup)
- [Child Actors](http://allaboutscala.com/scala-frameworks/akka/#child-actors)
- [Actor Lifecycle](http://allaboutscala.com/scala-frameworks/akka/#actor-lifecycle)
- [Actor PoisonPill](http://allaboutscala.com/scala-frameworks/akka/#actor-poisonpill)
- [Error Kernel Supervision](http://allaboutscala.com/scala-frameworks/akka/#error-kernel-supervision)

# Akka Routers
- [RoundRobinPool](http://allaboutscala.com/scala-frameworks/akka/#roundrobinpool-router)
- [ScatterGatherFirstCompletedPool](http://allaboutscala.com/scala-frameworks/akka/#scattergatherfirstcompletedpool-router)
- [TailChoppingPool](http://allaboutscala.com/scala-frameworks/akka/#tailchoppingpool-router)
- [BroadcastPool](http://allaboutscala.com/scala-frameworks/akka/#broadcastpool-router)

# Akka Dispatchers
- [Akka Default Dispatcher](http://allaboutscala.com/scala-frameworks/akka/#akka-default-dispatcher)
- [Akka Lookup Dispatcher](http://allaboutscala.com/scala-frameworks/akka/#akka-lookup-dispatcher)
- [Fixed Thread Pool Dispatcher](http://allaboutscala.com/scala-frameworks/akka/#fixed-thread-pool-dispatcher)
- [Resizable Thread Pool Dispatcher](http://allaboutscala.com/scala-frameworks/akka/#resizable-thread-pool)
- [Pinned Thread Pool Dispathcer](http://allaboutscala.com/scala-frameworks/akka/#pinned-thread-pool-dispatcher)

# Akka FSM
- [Actor FSM become()](http://allaboutscala.com/scala-frameworks/akka/#actor-fsm-become)
- [Actor FSM unbecome()](http://allaboutscala.com/scala-frameworks/akka/#actor-fsm-unbecome)
- [Actor FSM protocol](http://allaboutscala.com/scala-frameworks/akka/#actor-fsm-protocol)
- [Actor LoggingFSM](http://allaboutscala.com/scala-frameworks/akka/#actor-fsm-loggingfsm)
- [Actor LoggingFSM Part Two](http://allaboutscala.com/scala-frameworks/akka/#actor-fsm-loggingfsm-part-two)
- [Actor LoggingFSM Part Three](http://allaboutscala.com/scala-frameworks/akka/#actor-fsm-loggingfsm-part-three)
- [Actor LoggingFSM Part Four](http://allaboutscala.com/scala-frameworks/akka/#actor-fsm-loggingfsm-part-four)
- [Actor LoggingFSM Part Five](http://allaboutscala.com/scala-frameworks/akka/#actor-fsm-loggingfsm-part-five)
- [Actor LoggingFSM Part Six](http://allaboutscala.com/scala-frameworks/akka/#actor-fsm-loggingfsm-part-six)
- [Actor FSM Scheduler](http://allaboutscala.com/scala-frameworks/akka/#actor-fsm-scheduler)

# Akka TestKit
- [Testing Actor FSM](http://allaboutscala.com/scala-frameworks/akka/#akka-testkit-test-actor-fsm)
- [Testing Actor](http://allaboutscala.com/scala-frameworks/akka/#akka-testkit-test-actor)
- [Testing Akka HTTP POST](http://allaboutscala.com/scala-frameworks/akka/#akka-testkit-http-post)
- [Testing Query Parameter](http://allaboutscala.com/scala-frameworks/akka/#akka-testkit-query-parameter)
- [Testing Required Query Parameter](http://allaboutscala.com/scala-frameworks/akka/#akka-testkit-required-query-parameter)
- [Testing Optional Query Parameter](http://allaboutscala.com/scala-frameworks/akka/#akka-testkit-optional-query-parameter)
- [Testing Typed Query Parameter](http://allaboutscala.com/scala-frameworks/akka/#akka-testkit-typed-query-parameter)
- [Testing CSV Query Parameter](http://allaboutscala.com/scala-frameworks/akka/#akka-testkit-csv-query-parameter)

# Akka HTTP
- [Akka HTTP project setup build.sbt](http://allaboutscala.com/scala-frameworks/akka/#akka-http-project-setup-build-sbt)
- [Start Akka HTTP Server](http://allaboutscala.com/scala-frameworks/akka/#akka-http-start-server)
- [HTTP GET plain text](http://allaboutscala.com/scala-frameworks/akka/#akka-http-get-plain-text)
- [HTTP GET JSON response](http://allaboutscala.com/scala-frameworks/akka/#akka-http-get-json-response)
- [JSON encoding](http://allaboutscala.com/scala-frameworks/akka/#akka-http-json-encoding)
- [JSON pretty print](http://allaboutscala.com/scala-frameworks/akka/#akka-http-json-pretty-print)
- [HTTP POST JSON payload](http://allaboutscala.com/scala-frameworks/akka/#akka-http-post-json)
- [Could not find implicit value](http://allaboutscala.com/scala-frameworks/akka/#akka-http-could-not-find-implicit-value) 
- [HTTP DELETE restriction](http://allaboutscala.com/scala-frameworks/akka/#akka-http-delete-method-restriction)
- [Future onSuccess](http://allaboutscala.com/scala-frameworks/akka/#akka-http-future-onsuccess)
- [Future onComplete](http://allaboutscala.com/scala-frameworks/akka/#akka-http-future-oncomplete)
- [Complete with an HttpResponse](http://allaboutscala.com/scala-frameworks/akka/#akka-http-complete-httpresponse)
- [Try failure using an HttpResponse](http://allaboutscala.com/scala-frameworks/akka/#akka-http-try-failure-httpresponse)
- [Global rejection handler](http://allaboutscala.com/scala-frameworks/akka/#akka-http-global-rejection-handler)
- [Global exception handler](http://allaboutscala.com/scala-frameworks/akka/#akka-http-global-exception-handler)
- [Load HTML from resources](http://allaboutscala.com/scala-frameworks/akka/#akka-http-load-html-resource)
- [RESTful URLs with segment](http://allaboutscala.com/scala-frameworks/akka/#akka-http-rest-url-segment)
- [RESTful URLS with regex](http://allaboutscala.com/scala-frameworks/akka/#akka-http-rest-url-regex)
- [RESTful URLS with multiple segments](http://allaboutscala.com/scala-frameworks/akka/#akka-http-multiple-segments)
- [Query parameter](http://allaboutscala.com/scala-frameworks/akka/#akka-http-query-parameter)
- [Optional query parameter](http://allaboutscala.com/scala-frameworks/akka/#akka-http-optional-query-parameter)
- [Typed query parameters](http://allaboutscala.com/scala-frameworks/akka/#akka-http-typed-query-parameters)
- [CSV query parameter](http://allaboutscala.com/scala-frameworks/akka/#akka-http-csv-query-parameter)
- [Query parameter to case class](http://allaboutscala.com/scala-frameworks/akka/#akka-http-query-parameter-to-case-class)
- [HTTP request headers](http://allaboutscala.com/scala-frameworks/akka/#akka-http-request-headers)
- [HTTP client GET](http://allaboutscala.com/scala-frameworks/akka/#akka-http-client-get)
- [Unmarshal HttpResponse to case class](http://allaboutscala.com/scala-frameworks/akka/#akka-http-unmarshal-case-class)
- [HTTP client POST JSON](http://allaboutscala.com/scala-frameworks/akka/#akka-http-client-post-json)

Stay in touch via [LinkedIn](https://linkedin.com/in/nadimbahadoor/), [Facebook](http://www.facebook.com/allaboutscala) and [Twitter](https://twitter.com/NadimBahadoor) for upcoming tutorials!

## Contact
Nadim Bahadoor at http://allaboutscala.com/contact/

## License
Apache 2.0 License - see the LICENSE.TXT file 
