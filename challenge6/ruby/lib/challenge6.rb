def myFilter(anArray, filterRule)
	return anArray if !anArray.kind_of?(Array) 

	result = []
	anArray.each do |element| 
		if filterRule.(element) 
			result.push(element)
		end
	end
	result
end

