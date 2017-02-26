package com.github.bigwheel.thpy

import scala.reflect.macros.blackbox.Context
import scala.tools.nsc.interpreter.NamedParam

object Macro {

  private class MacroImpl(val c: Context) {

    def apply[T: c.WeakTypeTag](value: c.Tree): c.Tree = {
      import c.universe._
      val T = weakTypeOf[T]
      val paramRep = show(value)
      val paramRepTree = Literal(Constant(paramRep))
      val paramRepExpr = c.Expr[String](paramRepTree)
      q"new _root_.scala.tools.nsc.interpreter.NamedParam.Typed[$T]($paramRepExpr, $value)"
    }

  }

  implicit def anyToTyped[T](value: T): NamedParam.Typed[T] = macro MacroImpl.apply[T]

  // same manner https://github.com/lihaoyi/sourcecode/blob/ef7642f/sourcecode/shared/src/main/scala/sourcecode/SourceContext.scala
  abstract class SourceValue[T]{
    def value: T
  }
  abstract class SourceCompanion[T, V <: SourceValue[T]](build: T => V){
    def apply()(implicit s: V): T = s.value
    implicit def wrap(s: T): V = build(s)
  }
  case class CodeContextWithIndex(value: Seq[(Int, String)]) extends SourceValue[Seq[(Int, String)]]
  object CodeContextWithIndex extends
    SourceCompanion[Seq[(Int, String)], CodeContextWithIndex](new CodeContextWithIndex(_)){
    implicit def generate: CodeContextWithIndex = macro impl

    def impl(c: Context): c.Expr[CodeContextWithIndex] = {
      import c.universe._
      val sourceFile = c.enclosingPosition.source
      val fileBeginLine = sourceFile.offsetToLine(0) // of cource must be 0
      val fileEndLine = sourceFile.offsetToLine(c.enclosingPosition.source.length - 1)
      val lineNumber = c.enclosingPosition.line - 1 // Line method value started by 1, then minus 1

      val contextLineLength = 3
      val contextStartLine = Math.max(fileBeginLine, lineNumber - contextLineLength)
      val contextEndLine = Math.min(fileEndLine, lineNumber + contextLineLength)

      val context: Seq[(Int, String)] = Range.inclusive(contextStartLine, contextEndLine).
        map { index => (index, sourceFile.lineToString(index)) }

      c.Expr[CodeContextWithIndex](q"""scala.collection.immutable.Seq(..$context)""")
    }
  }
  case class CodeContext(value: Seq[String]) extends SourceValue[Seq[String]]
  object CodeContext extends SourceCompanion[Seq[String], CodeContext](new CodeContext(_)){
    implicit def generate: CodeContext = macro impl

    def impl(c: Context): c.Expr[CodeContext] = {
      import c.universe._
      val sourceFile = c.enclosingPosition.source
      val fileBeginLine = sourceFile.offsetToLine(0) // of cource must be 0
      val fileEndLine = sourceFile.offsetToLine(c.enclosingPosition.source.length - 1)
      val lineNumber = c.enclosingPosition.line - 1 // Line method value started by 1, then minus 1

      val contextLineLength = 3
      val contextStartLine = Math.max(fileBeginLine, lineNumber - contextLineLength)
      val contextEndLine = Math.min(fileEndLine, lineNumber + contextLineLength)

      val context: Seq[String] = Range.inclusive(contextStartLine, contextEndLine).
        map(sourceFile.lineToString)

      c.Expr[CodeContext](q"""scala.collection.immutable.Seq(..$context)""")
    }
  }

}
