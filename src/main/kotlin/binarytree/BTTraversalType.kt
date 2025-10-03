package binarytree

sealed class BTTraversalType {
    data object PreOrder : BTTraversalType()
    data object PostOrder : BTTraversalType()
    data object InOrder : BTTraversalType()
    data object LevelOrder : BTTraversalType()
}