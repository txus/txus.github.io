---
layout: post
title: "Version your Ruby objects with Aversion"
date: 2012-10-19 18:40
comments: true
categories: ruby
---

During the past few months I've been often daydreaming about functional
programming, persistent data structures, and so on. It's something that probably
came from learning a bit of Clojure and getting familiar with traditional
concepts of the functional paradigm.

One cool thing that I took from that is the concept of immutability. In
programs, mutable state is a rich source of all kinds of problems. For one, your
ability to reason about your program becomes impaired -- you cannot trust
values anymore. Variables are containers of ever-changing chaos, and especially
bad programmers seem to be always in to find new ways of enhancing the insanity
of any program through nonsensical mutation.

Now I've developed a bit of this sixth sense, or aversion towards mutation. When
I see mutation in code, my danger sense goes nuts. I might just accept it, but I
recognize it and question it.

What does all of this have to do with versioning objects? Well not much, apart
from the fact that versioning objects is a cool thing you can do when you're
objects are immutable. Of course after all these random thoughts I needed to
code something up to see it in action, and there you go!!

## Versioning with Aversion

When you include [Aversion][aversion] in your Ruby objects, every state mutation
is explicit and, instead of actually mutate the object, it returns a new
instance with the transformation, keeping a history of all the states it went
through.

Let's see how it works. Say we have a Person class:

```ruby
class Person
  include Aversion
  attr_reader :hunger

  def initialize(hunger)
    @hunger = hunger
  end

  def eat
    transform do
      @hunger -= 5
    end
  end
end
```

See the `transform` part? Here's an explicit change of state. Instead of
subtracting 5 from our current hunger, what it will do is return a new
**version** of the object where this transformation happened. The cool thing is,
you can go back too!

So, our Person instances will be immutable. Every mutation must be explicitly
wrapped within a `transform` block, and will return the new instance:

```ruby
john       = Person.new(100)

new_john   = john.eat
new_john.hunger # => 95

newer_john = new_john.eat
newer_john.hunger # => 90
```

Of course, you can roll back to a previous state:

```ruby
new_john_again = newer_john.rollback
new_john_again.hunger # => 95
```

And finally one of the nicest things is that you can also compute the difference
between two versions, expressed as an array of transformations, and apply it
onto an arbitrary object:

```ruby
difference = newer_john - john
newer_john_again = john.replay(difference)
newer_john_again.hunger # => 90
```

So, if you're curious, just [grab the github repo][aversion] and play with it!
You surely can find interesting use cases of immutability and versioning in your
own programs.

And also, if you're really curious about persistent data structures but you
don't want to learn Clojure just yet, try out [Hamster][hamster], a Ruby library
that implements a ton of persistent data structures.

[hamster]: https://github.com/harukizaemon/hamster
[aversion]: https://github.com/txus/aversion
