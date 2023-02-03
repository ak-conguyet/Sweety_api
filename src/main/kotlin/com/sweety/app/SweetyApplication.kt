package com.sweety.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SweetyApplication

fun main(args: Array<String>) {
	runApplication<SweetyApplication>(*args)
}
