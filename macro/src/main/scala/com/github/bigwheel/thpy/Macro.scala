package com.github.bigwheel.thpy

import scala.reflect.ClassTag
import scala.reflect.macros.whitebox.Context
import scala.reflect.runtime.{universe => ru}
import scala.tools.nsc.interpreter.NamedParam

object Macro {
  implicit def toNamedParamTyped[T](value: T)(implicit a: ru.TypeTag[T], b: ClassTag[T]):
  NamedParam.Typed[T] = macro toNamedParamTypedImpl[T]

  def toNamedParamTypedImpl[T: c.WeakTypeTag](c: Context)(value: c.Expr[T])(a: c.Expr[ru.TypeTag[T]], b: c.Expr[ClassTag[T]]) = {
    import c.universe._
    val paramRep = show(value.tree)
    val paramRepTree = Literal(Constant(paramRep))
    val paramRepExpr = c.Expr[String](paramRepTree)
    reify[NamedParam.Typed[T]](new NamedParam.Typed[T](paramRepExpr.splice, value.splice)(a.splice, b.splice))
  }
}
