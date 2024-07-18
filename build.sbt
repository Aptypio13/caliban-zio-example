name := "caliban-zio-example"


version := "1.0"
scalaVersion := "2.13.12"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "2.1.0",
  "com.github.ghostdogpr" %% "caliban-zio-http" % "2.8.1",
  "com.github.ghostdogpr" %% "caliban" % "2.8.1",
  "com.github.ghostdogpr" %% "caliban-quick" % "2.8.1",
  "com.typesafe.slick" %% "slick" % "3.4.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.4.1",
  "org.postgresql" % "postgresql" % "42.7.3",
  "dev.zio" %% "zio-json" % "0.6.2",
  "com.github.tminglei" %% "slick-pg" % "0.21.0",
  "io.scalac" %% "zio-slick-interop" % "0.6.0"
)