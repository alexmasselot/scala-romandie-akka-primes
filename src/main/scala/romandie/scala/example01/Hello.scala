package romandie.scala.example01

import akka.actor.{Props, ActorSystem, ActorLogging, Actor}

/**
 * the created actor just receives a string, log it and shutdown the system
 */

class PingActor extends Actor with ActorLogging {
  def receive = {
    case name:String =>
      log.info(s"hello, my name is $name")
      context.system.shutdown()
  }
}

object HelloApp extends App{
  val system = ActorSystem("MyActorSystem")
  val pingActor = system.actorOf(Props[PingActor], "pingActor")

  pingActor ! "Bond"

  system.awaitTermination()
}