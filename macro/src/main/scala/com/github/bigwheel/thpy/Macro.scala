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

}
