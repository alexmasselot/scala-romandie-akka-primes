package romandie.scala.example05

/**
 * create a MasterActor, that will
 * * launch a PingActor
 * * send 4 message to get list of PSP number within intervals
 * * change the single PSPActor to a round robin pool for parallelisation
 *
 */

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.routing.RoundRobinPool
import romandie.scala.psp.PSPActor
import romandie.scala.psp.PSPActor._

class MasterActor extends Actor with ActorLogging {

  val pspActor = context.actorOf(RoundRobinPool(5).props(Props[PSPActor]), "pspactor-router")
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

object PSProuterApp extends App {
  val system = ActorSystem("MyActorSystem")

  val masterActor = system.actorOf(Props[MasterActor], "master")

  system.awaitTermination()
}