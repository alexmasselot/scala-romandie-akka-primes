package romandie.scala.example04

/**
 * create a MasterActor, that will create a PSP Actor and
 * * send request for a single number
 * * ask for a list of PSP number as a list
 * * get a stream of PSP numbers
 * * react to the ask pattern (pipe to the answer as a future
 */

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import romandie.scala.psp.PSPActor
import romandie.scala.psp.PSPActor._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._



object PSPSimpleApp extends App {
  val system = ActorSystem("MyActorSystem")

  class MasterActor extends Actor with ActorLogging {
    val pspActor = context.actorOf(Props[PSPActor], "pspActor")
    implicit val timeout = Timeout(100.days)

    pspActor ! FindNextPSP(1000)
    pspActor ! FindListPSP(1000000000, 1000010000)
    pspActor ! FindStreamPSP(1000000000, 1000000200)
    (pspActor ? FindListPSP(2000000000, 2000100000)).mapTo[PSPList].map(a => log.info(s"received from ask $a"))

    override def receive: Receive = {

      case PSPSingle(i) =>
        log.info(s"received PSP [$i]")
      case al: PSPList =>
        log.info(s"received PSP $al")
    }
  }

  val masterActor = system.actorOf(Props[MasterActor], "master")

  system.awaitTermination()
}