package com.github.bigwheel

import java.io.BufferedReader
import scala.tools.nsc._
import scala.tools.nsc.interpreter._

package object thpy {

  private def breakImpl(iilCreater: (NamedParam*) => ImprovedILoop)(namedParams: NamedParam*) = {
    val repl = iilCreater(namedParams: _*)
    val s = new Settings()
    s.usejavacp.value = true

    repl.process(s)
  }

  def break(in: BufferedReader, out: JPrintWriter, namedParams: NamedParam*) =
    breakImpl(nps => new ImprovedILoop(Some(in), out)(nps: _*))(namedParams: _*)

  def break(in: BufferedReader, namedParams: NamedParam*) =
    breakImpl(nps => new ImprovedILoop(in0 = Some(in))(nps: _*))(namedParams: _*)

  def break(out: JPrintWriter, namedParams: NamedParam*) =
    breakImpl(nps => new ImprovedILoop(out = out)(nps: _*))(namedParams: _*)

  def break(namedParams: NamedParam*) =
    breakImpl(nps => new ImprovedILoop()(nps: _*))(namedParams: _*)

}
