package com.github.bigwheel.thpy

import org.scalatest.FunSpec
import org.scalatest.Matchers
import scala.tools.nsc.interpreter.NamedParam

class MacroTest extends FunSpec with Matchers {

  it ("can convert NamedParam.Typed implicitly") {
    import Macro.anyToTyped
    val subject: NamedParam.Typed[Int] = 1
    val expect = new NamedParam.Typed[Int]("1", 1)
    println(subject.equals(expect))
    subject should equal(expect)
  }

}
