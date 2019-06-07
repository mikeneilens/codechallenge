def myFilter(anArray, filterRule)
	result = []
	anArray.each do |element| 
		if filterRule.(element) 
			result.push(element)
		end
	end
	result
end

