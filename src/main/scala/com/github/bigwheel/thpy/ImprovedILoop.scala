package com.github.bigwheel.thpy

import java.io.BufferedReader
import scala.tools.nsc.Settings
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
  (namedParams: NamedParam*)
  (implicit file: sourcecode.File, line: sourcecode.Line, enclosing: sourcecode.Enclosing)
  extends ILoop(in0, out) {
  settings = {
    val s = new Settings()

    def classLoaderURLs(cl: ClassLoader): Seq[java.net.URL] = cl match {
      case null => Nil
      case u: java.net.URLClassLoader => u.getURLs.toSeq ++ classLoaderURLs(cl.getParent)
      case _ => classLoaderURLs(cl.getParent)
    }

    // sorted is unnecessary, but it makes comparable and coherency of order
    s.classpath.value = classLoaderURLs(getClass.getClassLoader).map(_.getPath).distinct.sorted.mkString(":")
    s
  }

  override def createInterpreter(): Unit = {
    super.createInterpreter()
    namedParams.foreach(intp.quietBind)
  }

  // based on https://github.com/scala/scala/blob/05016d90/src/compiler/scala/tools/nsc/Properties.scala#L19
  override def prompt = replProps.encolor(replProps.enversion("%nthpy> "))

  override def printWelcome(): Unit = {
    // based on https://github.com/scala/scala/blob/05016d90/src/compiler/scala/tools/nsc/Properties.scala#L21-L22
    // link to source code gimmick https://intellij-support.jetbrains.com/hc/en-us/community/posts/206330369
    val shellWellcomeString =
      s"""Welcome to Thpy at ${enclosing.value}(${file.value}:${line.value.toString})
      |binded names: ${showBind()}
      |Type in expressions for evaluation. Or try :help.""".stripMargin

    echo(shellWellcomeString)
    replinfo("[info] started at " + new java.util.Date)
  }

  // copy https://github.com/scala/legacy-svn-scala/blob/ae19692/src/compiler/scala/tools/nsc/interpreter/ReplConfig.scala#L32 bacause of private[nsc]
  private[this] def replinfo(msg: => String) = if (isReplInfo) echo(msg)

  private[this] def showBind(): String = namedParams.map(_.name).mkString(" ")
  private[this] def showBindCommand(): Result = showBind

  override def commands: List[LoopCommand] =
    LoopCommand.nullary("showbind", "show binded names", () => showBindCommand) :: super.commands

  def process(): Boolean = process(this.settings)
}
