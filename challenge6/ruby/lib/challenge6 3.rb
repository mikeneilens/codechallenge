def myFilter(anArray, filterRule)

	return anArray unless anArray.kind_of?(Array) and filterRule.methods.include?(:call) 

	def recursiveFilter(result, anArray, filterRule)
		if anArray.empty? 
			return result
		else
			if filterRule.(anArray.first) 
				recursiveFilter(result.push(anArray.first),anArray.drop(1),filterRule)
			else
				recursiveFilter(result,anArray.drop(1),filterRule)
			end
		end
	end

	recursiveFilter([],anArray,filterRule)
end

