package com.alediaferia.treewalker

data class Node(val depth: Int = 0, var score1: Int = 0, var score2: Int = 0, var score3: Int = 0) {
    var children: List<Node> = listOf()
}