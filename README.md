# thpy - ruby's pry like runtime developer console

## What's this

Debugging tool like [Ruby's pry](https://github.com/pry/pry).
Insert one themod call `break` cause interuption of program and setup scala interactive tool (Repl) with binded values.
User can show the value, call methods and change internal state of it.

## How to install

build.sbt
```scala
libraryDependencies += "com.github.bigwheel" %% "thpy" % "0.1.0"
```
## How to use

Write code as [sample Main.scala](src/main/scala/com/github/bigwheel/thpy/Main.scala),
```scala
import com.github.bigwheel.thpy.break
import com.github.bigwheel.thpy.Macro.anyToTyped

// for human interactive test purpose Main.
object Main {
  def main(args: Array[String]): Unit = {
    val a = 10
    break(a)
  }
}
```
then `sbt run` and interact with thpy console
```
$ sbt run
....
Welcome to Thpy at <empty>.Main.main(/home/kbigwheel/code/thpy-sample/src/main/scala/Main.scala:8)

    4:
    5:   def main(args: Array[String]): Unit = {
    6:     val a = 10
=>  7:     break(a)
    8:   }
    9:
   10: }

binded names: a
Type in expressions for evaluation. Or try :help.

thpy> a
res0: Int = 10

thpy> 1 + 1
res1: Int = 2

thpy> a + 2
res2: Int = 12

thpy> :quit
```

## Support scala version

Tested only in 2.12.
And this must not work well in other version because repl code has no compatibility.

## Known problem

Type of binded value must be visible in public scope.
There private class, enclosing class or annonymous class cannot be used in thpy (public class with private constructor is no problem).
