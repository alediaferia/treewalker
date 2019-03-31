package com.alediaferia.treewalker

import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    println("Creating tree...")

    lateinit var root : Node
    val creationTook = measureTimeMillis {
        root = RandomTreeGenerator().generate(6, 15)
    }
    println("Tree creation completed. ($creationTook ms)")

    var tailrecCount = 0
    val tailrecTook = measureTimeMillis {
        tailrecCount = root.totalCountTailrec()
    }
    println("Total count (tailrec): $tailrecCount ($tailrecTook ms)")

    var recursiveCount: Int = 0
    val recursiveTook = measureTimeMillis {
        recursiveCount = root.totalCountRecursive()
    }
    println("Total count (recursive): $recursiveCount ($recursiveTook ms)")

//    println("${Node().apply { children.addAll(listOf(Node().apply { children.add(Node()) }, Node())) }.totalCountRecursive()}")
}

private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

fun Node.totalCountRecursive(): Int {
//    println("(recursive) Current stack depth: ${currentStackTrace().size}")
    return 1 + this.children.sumBy { it.totalCountRecursive() }
}

fun Node.totalCountTailrec(): Int {
    return totalChildrenCount(mutableListOf(this), 1)
}

private tailrec fun totalChildrenCount(nodes: MutableList<Node>, acc: Int = 0, start: Int = 0): Int {
//    println("(tailrec) Current stack depth: ${currentStackTrace().size}")
    return when {
        start >= nodes.size -> acc
        else -> {
            val current = nodes[start]
            nodes.addAll(current.children)
            totalChildrenCount(nodes, acc + current.children.size, start + 1)
        }
    }
}

fun String.Companion.random(length: Int): String =
    (1..length)
        .map { Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")