# thpy - ruby's pry like runtime developer console

## What's this

Debugging tool like Ruby's pry.
Insert one themod call `break` cause interuption of program and setup scala interactive tool (Repl) with binded values.
User can show the value, call methods and change internal value of it.

## How to install

build.sbt
```scala
lazy val thpy = RootProject(uri("https://github.com/bigwheel/thpy.git"))
lazy val root = Project("root", file(".")) dependsOn thpy
```
## How to use

Write code as [sample Main.scala](src/main/scala/com/github/bigwheel/thpy/Main.scala),
```scala
import com.github.bigwheel.thpy.break
import com.github.bigwheel.thpy.Macro.anyToTyped

// for human interactive test purpose Main.
object Main {
  def main(args: Array[String]): Unit = {
    val a = 1
    break(a)
  }
}
```
then `sbt run` and interact with thpy console
```
$ sbt run
....
Welcome to Thpy at com.github.bigwheel.thpy.Main.main(/home/kbigwheel/code/thpy/src/main/scala/com/github/bigwheel/thpy/Main.scala:10)
binded names: a
Type in expressions for evaluation. Or try :help.

thpy> a
res0: Int = 1

thpy> 1 + 1
res1: Int = 2

thpy> :showbind
a

thpy> :quit
```

## Support scala version

Tested only in 2.12.
And this must not work well in other version because repl code has no compatibility.

## Known problem

Type of binded value must be visible in public scope.
There private class, enclosing class or annonymous class cannot be used in thpy (public class with private constructor is no problem).
