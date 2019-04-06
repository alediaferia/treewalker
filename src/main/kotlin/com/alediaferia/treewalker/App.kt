package com.alediaferia.treewalker

import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    println("Creating tree...")

    lateinit var root: Node
    val creationTook = measureTimeMillis {
        root = RandomTreeGenerator().generate(15, 8)
    }
    println("Tree creation completed. ($creationTook ms)")

    var tailrecCount = 0
    val tailrecTook = measureTimeMillis {
        tailrecCount = root.totalCountTailrec()
    }
    println("Total count (tailrec): $tailrecCount nodes ($tailrecTook ms)")

    var score3Aggregate = 0L
    val score3Took = measureTimeMillis {
        score3Aggregate = root.totalScore3Count()
    }
    println("Total score3 (tailrec): $score3Aggregate score ($score3Took ms)")

    var recursiveCount: Int = 0
    val recursiveTook = measureTimeMillis {
        recursiveCount = root.totalCountRecursive()
    }
    println("Total count (recursive): $recursiveCount nodes ($recursiveTook ms)")

    var score3Recursive = 0L
    val score3RecursiveTook = measureTimeMillis {
        score3Recursive = root.totalScore3CountRecursive()
    }
    println("Total score3 (recursive): $score3Recursive score ($score3RecursiveTook ms)")

//    println("${Node().apply { children.addAll(listOf(Node().apply { children.add(Node()) }, Node())) }.totalCountRecursive()}")
}

private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

fun Node.totalCountRecursive(): Int {
//    println("(recursive) Current stack depth: ${currentStackTrace().size}")
    return 1 + this.children.sumBy { it.totalCountRecursive() }
}

fun Node.totalScore3CountRecursive(): Long {
    return score3.toLong() + this.children.sumByLong { it.totalScore3CountRecursive() }
}

fun Node.totalCountTailrec(): Int {
    return totalChildrenCount(listOf(this).iterator(), 1)
}

fun Node.totalScore3Count(): Long {
    return totalScore3Aggregate(listOf(this).iterator(), this.score3.toLong())
}

private tailrec fun totalChildrenCount(nodes: Iterator<Node>, acc: Int = 0): Int {
//    println("(tailrec) Current stack depth: ${currentStackTrace().size}")
    return when {
        !nodes.hasNext() -> acc
        else -> {
            val current = nodes.next()
            totalChildrenCount(nodes + current.children.iterator(), acc + current.children.size)
        }
    }
}

private tailrec fun totalScore3Aggregate(nodes: Iterator<Node>, acc: Long = 0): Long {
    return when {
        !nodes.hasNext() -> acc
        else -> {
            val current = nodes.next()
            totalScore3Aggregate(
                nodes + current.children.iterator(),
                acc + current.children.sumByLong { it.score3.toLong() })
        }
    }
}

fun String.Companion.random(length: Int): String =
    (1..length)
        .map { Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")

inline fun <T> Iterable<T>.sumByLong(selector: (T) -> Long): Long {
    var sum = 0L
    for (element in this) {
        sum += selector(element)
    }
    return sum
}