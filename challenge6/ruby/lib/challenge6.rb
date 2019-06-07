def myFilter(anArray, filterRule) 
	anArray
end

is_less_than5 = ->(aNumber) { aNumber < 5 }
myFilter([], is_less_than5)