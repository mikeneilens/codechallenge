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