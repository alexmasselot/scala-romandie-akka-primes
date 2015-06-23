package romandie.scala.example04

/**
 * The same as PSPRouterApp, but a logger actor is created with the 'psp-logger' name, that will simply write every number found in a file
 * this logger actor is discovered with all the PSPActor via it's name
 *
 */

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.routing.RoundRobinPool
import akka.util.Timeout
import romandie.scala.example04.PSPActor._
import akka.pattern.{ask, pipe}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class MasterActorWithLogger extends Actor with ActorLogging {

  val pspActor = context.actorOf(RoundRobinPool(5).props(Props[PSPActorWithLogger]), "pspactor-router")
  //val pspActor = context.actorOf(Props[PSPActor], "pspActor")

  pspActor ! FindListPSP(2000000000, 2000100000)
  pspActor ! FindListPSP(2000100000, 2000200000)
  pspActor ! FindListPSP(2000200000, 2000300000)
  pspActor ! FindListPSP(2000300000, 2000400000)

  override def receive: Receive = {
    case al: PSPList =>
      log.info(s"received PSP $al")
  }
}

object PSProuterWithLoggerApp extends App {
  val system = ActorSystem("MyActorSystem")

  val loggerActor  = system.actorOf(Props[PSPActorLogger], "psp-logger")
  val masterActor = system.actorOf(Props[MasterActorWithLogger], "master")

  system.awaitTermination()
}