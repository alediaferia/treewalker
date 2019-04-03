package com.alediaferia.treewalker

import kotlin.random.Random

class RandomTreeGenerator {
    fun generate(maxDepth: Int, maxChildrenForBranch: Int): Node {
        if (maxDepth < 1) throw IllegalArgumentException("maxDepth cannot be lower than 1")

        val root = Node()
        var nodesToProcess = mutableListOf(root)
        var startIndex = 0
        while (startIndex < nodesToProcess.size) {
            val currentRoot = nodesToProcess[startIndex]
            if (currentRoot.depth < maxDepth) {
                val childrenCount = Random.nextInt(0, maxChildrenForBranch)
                currentRoot.children = List(childrenCount) { Node(currentRoot.depth + 1) }

                if (currentRoot.children.isEmpty()) {
                    currentRoot.score1 = Random.nextInt(0, 6)
                    currentRoot.score2 = Random.nextInt(0, 6)
                    currentRoot.score3 = Random.nextInt(0, 6)
                }

                nodesToProcess.addAll(currentRoot.children)
            } else {
                currentRoot.score1 = Random.nextInt(0, 6)
                currentRoot.score2 = Random.nextInt(0, 6)
                currentRoot.score3 = Random.nextInt(0, 6)
                break
            }
            startIndex++
        }

//        println("Produced $totalNodes nodes")

        return root
    }
}

