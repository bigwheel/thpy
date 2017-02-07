package com.github.bigwheel.thpy

import scala.reflect.ClassTag
import scala.reflect.macros.whitebox.Context
import scala.reflect.runtime
import scala.tools.nsc.interpreter.NamedParam

object Macro {
  implicit def anyToTyped[T](value: T)(implicit a: runtime.universe.TypeTag[T], b: ClassTag[T]):
    NamedParam.Typed[T] = macro anyToTypedImpl[T]

  def anyToTypedImpl[T: c.WeakTypeTag](c: Context)(value: c.Expr[T])
    (a: c.Expr[runtime.universe.TypeTag[T]], b: c.Expr[ClassTag[T]]) = {
    import c.universe._

    val paramRep = show(value.tree)
    val paramRepTree = Literal(Constant(paramRep))
    val paramRepExpr = c.Expr[String](paramRepTree)
    reify(new NamedParam.Typed[T](paramRepExpr.splice, value.splice)(a.splice, b.splice))
  }
}
