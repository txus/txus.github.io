---
title: Stendhal 0.1.2 released
layout: post
categories: [ruby, bdd, stendhal]
---

A brief post about [stendhal][repo] latest updates! My little code kata
is growing slowly :)

Admittedly, the way in which I develop this gem is by forcing myself not
to write anything I don't need right now. Last version (0.1.0) introduced
nested example groups, which forced me to rewrite the way examples were ran.
I believe this made me rethink some stuff and come up with better code.

By 0.1.0, the only way to "assert" something inside an example was to
primitively do it like this:

``` ruby
describe "something" do
  it "does something" do
    assert(3 + 4)
  end
end
```

This little method was implemented in an Assertions module which I no longer
use: this new release introduces RSpec-like expectations and matchers. This
means you can do this kind of things now:

``` ruby
describe "something" do
  it "does something" do
    (3+4).must eq(7)
  end
  it "does something else" do
    "string".must_not be_frozen
  end
  it "does something else" do
    "string".must be_a(String)
  end
end
```

For now there are a limited number of matchers, of course.

### Where is this going then? What will 1.0 have?

My idea is to bring **dogfooding** to stendhal by 1.0: all stendhal code
should be tested with stendhal itself, rather than with RSpec.

This means that hopefully these features have to available by then:

* Test doubles
* Mocks
* Some more matchers
* A decent reporter with multiple formatters

And it would be nice to have these too:

* Support for 3rd party mocking libraries (mocha, rr, flexmock, rspec-mocks)
* Support for using stendhal with rspec-expectations

Wouldn't that be great? Check the [Github source][repo] and report issues if
you find them! This will speed up the development process a lot :)

[repo]: http://github.com/txus/stendhal
