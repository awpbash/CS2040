# Python program to construct tree using inorder and 
# preorder traversals

# A binary tree node 
class Node:
	
	# Constructor to create a new node
	def __init__(self, data):
		self.data = data
		self.left = None
		self.right = None

"""Recursive function to construct binary of size len from
Inorder traversal in[] and Preorder traversal pre[]. Initial values
of inStrt and inEnd should be 0 and len -1. The function doesn't
do any error checking for cases where inorder and preorder
do not form a tree """
def buildTree(inOrder, preOrder, inStrt, inEnd):
	
	if (inStrt > inEnd):
		return None

	# Pick current node from Preorder traversal using
	# preIndex and increment preIndex
	tNode = Node(preOrder[buildTree.preIndex])
	buildTree.preIndex += 1

	# If this node has no children then return
	if inStrt == inEnd :
		return tNode

	# Else find the index of this node in Inorder traversal
	inIndex = search(inOrder, inStrt, inEnd, tNode.data)
	
	# Using index in Inorder Traversal, construct left 
	# and right subtrees
	tNode.left = buildTree(inOrder, preOrder, inStrt, inIndex-1)
	tNode.right = buildTree(inOrder, preOrder, inIndex + 1, inEnd)

	return tNode

# UTILITY FUNCTIONS
# Function to find index of value in arr[start...end]
# The function assumes that value is present in inOrder[]

def search(arr, start, end, value):
	for i in range(start, end + 1):
		if arr[i] == value:
			return i

def printInorder(node):
	if node is None:
		return
	
	# first recur on left child
	printInorder(node.left)
	
	# then print the data of node
	print (node.data,end=' ')

	# now recur on right child
	printInorder(node.right)
	
# Driver program to test above function
inOrder = ["a", "b", "a", "d", "e", "f","e","h","i","j"]
preOrder = ["a", "b", "a", "d", "e", "f","e","h","i","j"]
# Static variable preIndex
buildTree.preIndex = 0
root = buildTree(inOrder, preOrder, 0, len(inOrder)-1)

# Let us test the build tree by printing Inorder traversal
print ("Inorder traversal of the constructed tree is")
printInorder(root)

# This code is contributed by Nikhil Kumar Singh(nickzuck_007)
