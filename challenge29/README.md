### Challenge 29

For the merge sort I tried several versions to try and find the fastest.

#### Simple mergeSort that uses recursion:

- If the list has one or no elements then return the list.
- Otherwise apply **merge** to mergeSort of first half of the list and mergesort of second half of the list.   
- This is recursive, but not tail recursive so not scalable.

**merge** is given two sorted lists and compares each element at the start of each list to create a single sorted list.

#### mergeSort that uses coroutines:

- This is the same as the simple merge sort except the result of the two mergesorts are calculated asynchronously before being merged.
- This is recursive, but not tail recursive so not scalable.

#### mergeSort that uses a loop:

- First turn the list of n elements into a list containing n lists of 1 element. eg. [6,5,7,2] becomes [[6],[5],[7],[2]] 
- Read through each of the lists in the list of lists, two at a time.
- Apply **merge** to each pair of lists and concatonate the result of the merges to create a new list of lists. So first time around in this example this would give [[5,6],[2,7]]
- Repeat until the list of lists only contains one list. So next cycle in this example would reults in merge of [5,6] and [2,7] which is [[2,5,6,7]]. 
- This is not recursive but not easy to apply any concurrency to.

#### Hybrid of mergeSort using coroutines and mergeSort using aloop.

- This is the same as the merge sort using coroutines except the coroutines call merge sort using a loop. 
- In theory this should be approx twice as fast as using merge sort using a loop.
- It is not recurive so is scalable.
- Using simple mergeSort instead of a loop based mergeSort would probably be quicker but not scalable due to recursion.

Typical speeds (in ms) for sorts of array with 10,000 elements is:
```
Simple merge sort elapsed: 481 
Concurrent merge sort elapsed: 992 
Loop merge sort elapsed: 661 
Part concurrent, part Loop merge sort elapsed: 340 
```
