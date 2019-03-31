package com.alediaferia.treewalker

import kotlin.random.Random

class RandomTreeGenerator {
    fun generate(maxDepth: Int, maxChildrenForBranch: Int): Node {
        if (maxDepth < 1) throw IllegalArgumentException("maxDepth cannot be lower than 1")

        val root = Node()
        var nodesToProcess = mutableListOf(root)
        while (nodesToProcess.isNotEmpty()) {
            val currentRoot = nodesToProcess.first()
            if (currentRoot.depth < maxDepth) {
                val childrenCount = Random.nextInt(1, maxChildrenForBranch)
                currentRoot.children = List(childrenCount) { Node(currentRoot.depth + 1) }

                nodesToProcess.addAll(currentRoot.children)
            } else break
            nodesToProcess = nodesToProcess.subList(1, nodesToProcess.size)
        }

//        println("Produced $totalNodes nodes")

        return root
    }
}

