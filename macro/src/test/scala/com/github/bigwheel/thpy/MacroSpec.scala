package com.github.bigwheel.thpy

import org.scalactic.Equality
import org.scalatest.FunSpec
import org.scalatest.Matchers
import scala.tools.nsc.interpreter.NamedParam

class MacroSpec extends FunSpec with Matchers {

  // Following code has type erasue problem then this Equality is not completed.
  private[this] implicit val _ = new Equality[NamedParam.Typed[Int]] {
    override def areEqual(lhs: NamedParam.Typed[Int], rhs: Any): Boolean = rhs match {
      case rhs2 : NamedParam.Typed[Int] =>
        lhs.tpe == rhs2.tpe && lhs.name == rhs2.name && lhs.value == rhs2.value
      case _ =>
        false
    }
  }

  it ("can convert NamedParam.Typed implicitly") {
    import Macro.anyToTyped
    val subject: NamedParam.Typed[Int] = 1
    subject should equal(new NamedParam.Typed[Int]("1", 1))
  }

}
