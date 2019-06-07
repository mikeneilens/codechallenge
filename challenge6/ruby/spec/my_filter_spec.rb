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