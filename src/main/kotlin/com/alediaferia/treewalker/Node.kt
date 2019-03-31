package com.alediaferia.treewalker

data class Node(val depth: Int = 0) {
    var children: List<Node> = listOf()
}