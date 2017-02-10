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

  override def printWelcome(): Unit = {
    val shellWellcomeString = """Welcome to Thpy %1$#s (%3$s, Java %2$s).
      |Type in expressions for evaluation. Or try :help.""".stripMargin

    echo(replProps.enversion(shellWellcomeString))
    replinfo("[info] started at " + new java.util.Date)
  }

  // copy https://github.com/scala/legacy-svn-scala/blob/ae19692/src/compiler/scala/tools/nsc/interpreter/ReplConfig.scala#L32 bacause of private[nsc]
  private def replinfo(msg: => String) = if (isReplInfo) echo(msg)
}
