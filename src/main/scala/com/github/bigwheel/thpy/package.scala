package com.github.bigwheel

import java.io.BufferedReader
import scala.tools.nsc._
import scala.tools.nsc.interpreter._

package object thpy {

  def break(br: BufferedReader, namedParams: NamedParam*) = {
    val repl = new ImprovedILoop(Some(br), new JPrintWriter(Console.out, true))(namedParams: _*)
    val s = new Settings()
    s.usejavacp.value = true

    repl.process(s)
  }

}
