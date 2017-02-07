package com.github.bigwheel.thpy

import scala.tools.nsc._
import scala.tools.nsc.interpreter._

object Main {

  def main(args: Array[String]) {
    val b = 3

    import Macro.anyToTyped
    // break(new NamedParam.Typed("a", a))
    break(b)
  }

  def break(namedParams: NamedParam*) = {
    val repl = new ImprovedILoop(namedParams: _*)
    val s = new Settings()
    s.usejavacp.value = true

    repl.process(s)
  }

}
