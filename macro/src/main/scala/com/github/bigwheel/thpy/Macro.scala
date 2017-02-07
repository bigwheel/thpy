package com.github.bigwheel.thpy

import scala.reflect.ClassTag
import scala.reflect.macros.whitebox.Context
import scala.reflect.runtime
import scala.tools.nsc.interpreter.NamedParam

object Macro {
  implicit def anyToTyped[T](value: T): NamedParam.Typed[T] = macro anyToTypedImpl[T]

  def anyToTypedImpl[T: c.WeakTypeTag](c: Context)(value: c.Tree): c.Tree = {
    import c.universe._
    val T = weakTypeOf[T]
    val paramRep = show(value)
    val paramRepTree = Literal(Constant(paramRep))
    val paramRepExpr = c.Expr[String](paramRepTree)
    q"new _root_.scala.tools.nsc.interpreter.NamedParam.Typed[$T]($paramRepExpr, $value)"
  }
}
