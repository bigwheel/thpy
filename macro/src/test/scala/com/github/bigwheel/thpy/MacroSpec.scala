package com.github.bigwheel.thpy

import com.github.bigwheel.thpy.Macro.MacroExperiment
import org.scalatest.FunSpec
import org.scalatest.Matchers
import scala.tools.nsc.interpreter.NamedParam

class MacroSpec extends FunSpec with Matchers {

  it ("can convert NamedParam.Typed implicitly without bind name") {
    import Macro.anyToTyped
    val a = 10
    val subject: NamedParam.Typed[Int] = a

    subject.name should be("a")
    subject.value should be(10)
    subject.tpe should be("scala.Int")
  }

  it ("can convert NamedParam.Typed implicitly with bind name") {
    import NamedParam.tuple
    val subject: NamedParam = ("b", 10)

    subject.name should be("b")
    subject.value should be(10)
    subject.tpe should be("scala.Int")
  }

  val aaaaa = "test"
  it ("macro test") {
    val bbbbb = "test"
    println(implicitly[MacroExperiment].value)
  }
}
