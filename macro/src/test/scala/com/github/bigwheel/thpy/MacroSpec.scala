package com.github.bigwheel.thpy

import org.scalatest.FunSpec
import org.scalatest.Matchers
import scala.tools.nsc.interpreter.NamedParam

class MacroSpec extends FunSpec with Matchers {

  it ("can convert NamedParam.Typed implicitly") {
    import Macro.anyToTyped
    val a = 10
    val subject: NamedParam.Typed[Int] = a

    val expect = new NamedParam.Typed[Int]("a", 10)

    subject.name should be(expect.name)
    subject.value should be(expect.value)
    subject.tpe should be(expect.tpe)
  }

}
