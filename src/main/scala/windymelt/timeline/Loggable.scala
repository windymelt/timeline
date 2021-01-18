package windymelt.timeline

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import windymelt.timeline.Types.{Tag, @@}

/**
  * Provides logging capability.
  */
trait Loggable {
  sealed trait Caller
  def logger(implicit caller: Class[_] @@ Caller) =
    LoggerFactory.getLogger(caller)
  private def asCaller[A](a: A): A @@ Caller = Tag[Caller](a)
  implicit lazy val caller: Class[_] @@ Caller = asCaller(this.getClass())
}
