def myFilter(anArray, filterRule)

	return anArray unless anArray.kind_of?(Array) and filterRule.methods.include?(:call) 

	result = []
	anArray.each do |element| 
		if filterRule.(element) 
			result.push(element)
		end
	end
	result

end

