## Challenge 31

For the first part I used a linked list with each node representing part of the expression.

```
CalcNode
   value:Double       
   operator:String            // "+","-","/","X","*", or "" if the last item
   nextCalcNode:CalcNode?     // Null if the last item
 ```

So "3 + 4" would have a structure of:
```
CalcNode(3.0,"+", CalcNode(4.0,"") )
```

To calculate the expression the nodes would then be traversed. In the simple example above, the root node value would be added to the second node value and the operator and nextNode of the root replaced by the values from the second node.

I return the result using a Kotlin Result type. This seems to be in some kind of experimental state as I had to add compiler options to allow a Result Type to be returned by a function.