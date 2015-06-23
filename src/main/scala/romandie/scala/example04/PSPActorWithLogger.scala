package romandie.scala.example04

import java.io.FileWriter

import akka.actor.{ActorLogging, Actor}
import romandie.scala.example04.PSPActor._

/**
 * Created by amasselo on 6/23/15.
 */
class PSPActorLogger extends Actor with ActorLogging {
  val writer = new FileWriter("/tmp/psp.log")
  override def receive: Receive = {
    case PSPSingle(i) =>
      writer.write(s"$i\n")
  }
}


class PSPActorWithLogger extends Actor with ActorLogging {
  var actorLogger = context.actorSelection("/user/psp-logger")
  override def receive: Receive = {
    case FindListPSP(start, end) =>
      log.info(s"haha FindListPSP($start, $end)")
      val l = PrimeSumPrime.allPSP(start, end)
      l.foreach(x => actorLogger ! PSPSingle(x))
      sender ! PSPList(l)
  }


}
