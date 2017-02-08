package com.github.bigwheel.thpy

import java.io.BufferedReader
import scala.tools.nsc.interpreter._

/**
  * Add bind function to ILoop
  *
  * I don't like class extends,
  * but no another way to inject NamedParam to intp smartly.
  *
  * Other way I have are only two:
  * 1. copy ILoop code itself and add argument
  * 2. modernize scala.tools.nsc.interpreter._ and send patch to scala/scala
  *
  * based on https://github.com/scala/scala/blob/a346b77/src/repl/scala/tools/nsc/interpreter/IMain.scala#L621
  * based on https://github.com/scala/scala/blob/a346b77/src/repl/scala/tools/nsc/interpreter/IMain.scala#L660-L661
  */
class ImprovedILoop
  (in0: Option[BufferedReader] = None, out: JPrintWriter = new JPrintWriter(Console.out, true))
  (namedParams: NamedParam*) extends ILoop(in0, out) {

  override def createInterpreter(): Unit = {
    super.createInterpreter()
    namedParams.foreach(intp.quietBind)
  }

}
