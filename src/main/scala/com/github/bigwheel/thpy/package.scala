package com.github.bigwheel

import com.github.bigwheel.thpy.Macro.CodeContextWithIndex
import java.io.BufferedReader
import scala.tools.nsc.interpreter._

package object thpy {

  private def breakImpl(iilCreater: (NamedParam*) => ImprovedILoop)(namedParams: NamedParam*) = {
    val repl = iilCreater(namedParams: _*)

    repl.process
  }

  def break(in: BufferedReader, out: JPrintWriter, namedParams: NamedParam*)
    (implicit file: sourcecode.File, line: sourcecode.Line, enclosing: sourcecode.Enclosing,
      codeContext: CodeContextWithIndex) =
    breakImpl(nps => new ImprovedILoop(Some(in), out)(codeContext, nps: _*))(namedParams: _*)

  def break(in: BufferedReader, namedParams: NamedParam*)
    (implicit file: sourcecode.File, line: sourcecode.Line, enclosing: sourcecode.Enclosing,
      codeContext: CodeContextWithIndex) =
    breakImpl(nps => new ImprovedILoop(in0 = Some(in))(codeContext, nps: _*))(namedParams: _*)

  def break(out: JPrintWriter, namedParams: NamedParam*)
    (implicit file: sourcecode.File, line: sourcecode.Line, enclosing: sourcecode.Enclosing,
      codeContext: CodeContextWithIndex) =
    breakImpl(nps => new ImprovedILoop(out = out)(codeContext, nps: _*))(namedParams: _*)

  def break(namedParams: NamedParam*)
    (implicit file: sourcecode.File, line: sourcecode.Line, enclosing: sourcecode.Enclosing,
      codeContext: CodeContextWithIndex) =
    breakImpl(nps => new ImprovedILoop()(codeContext, nps: _*))(namedParams: _*)

}
