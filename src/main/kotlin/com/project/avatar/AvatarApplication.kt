package com.project.avatar

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@MapperScan("com.project.avatar.model.dao")
@SpringBootApplication
class AvatarApplication

fun main(args: Array<String>) {
    runApplication<AvatarApplication>(*args)
}
