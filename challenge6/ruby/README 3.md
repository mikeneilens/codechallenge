## Challenge 6  

#### Design decisions.
* I've done this in ruby to have a go using a dynamic language
* I'm using Rspec for the first time, so not totally sure what I'm doing
* I've passed the function into myFilter using a lambda function as otherwise you need to wrap the function to pass it as a parameter.
* I converted my original .each loop into a recursive function, although most Ruby implementations don't optimise for tail recursion so this is bad.

You will need to install rspec using  __bundle install --path .bundle__

If you haven't installed ruby bundler you will need to install that first.

To run the tests type __bundle exec rspec__ from the ruby directory