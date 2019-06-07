# spec/my_filter_spec.rb

require "challenge6"

is_less_than5 = ->(aNumber) { aNumber < 5 }

describe "myFilter" do
    context "given an empty array" do
      it "returns an empty array" do
        expect( myFilter([], is_less_than5 ) ).to eql([])
      end
    end
end

describe "myFilter" do
    context "given an array containing one number less than 5" do
      it "returns an array containing the number" do
        expect( myFilter([3], is_less_than5 ) ).to eql([3])
      end
    end
end

describe "myFilter" do
    context "given an array containing more than one number less than 5" do
      it "returns an array containing all the numbers" do
        expect( myFilter([3,2,4], is_less_than5 ) ).to eql([3,2,4])
      end
    end
end

describe "myFilter" do
    context "given an array containing one number which is 5" do
      it "returns an empty array" do
        expect( myFilter([5], is_less_than5 ) ).to eql([])
      end
    end
end

describe "myFilter" do
    context "given an array containing one number which is below 5 and one number bigger or equal to 5" do
      it "returns an empty array" do
        expect( myFilter([4,5], is_less_than5 ) ).to eql([4])
      end
    end
end

describe "myFilter" do
    context "given first parameter which is not an array" do
      it "returns the first parameter" do
        expect( myFilter(4, is_less_than5 ) ).to eql(4)
      end
    end
end
describe "myFilter" do
    context "given a second parameter which is not an anonymous function" do
      it "returns first parameter" do
        expect( myFilter([1,2,3,4,5], 999 ) ).to eql([1,2,3,4,5])
      end
    end
end
