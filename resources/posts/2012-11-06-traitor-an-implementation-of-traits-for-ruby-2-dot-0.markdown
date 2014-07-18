---
layout: post
author: Txus
title: "Traitor - an implementation of traits for Ruby 2.0"
date: 2012-11-06 21:46
comments: true
categories: ruby
---

Refinements are **the** most buzzed new feature in Ruby 2.0. Admittedly, they're
probably a bad idea. But honestly I couldn't resist trying them to implement
traits!

## What are traits?

Traits are like Ruby modules in the sense that they can be used to define
composable units of behavior, but they are not included hierarchically. They
are truly composable, meaning that are pieces that *must* either fit
perfectly or the host object must provide a way for them to do it, normally
resolving conflicts by explicitly redefining the conflicting methods.

Since I first read about traits, I found them better than Ruby mixins, that's
why I implemented them natively in [Noscript][noscript], my programming language
running on the Rubinius VM. But having traits in our beloved Ruby turned out to
be less trivial than expected.

A while ago I tried to implement traits with pure Ruby and gave up. The problem
basically was the way in which a Ruby module is included in a class or extended
in an object. One of the power features of traits is the **explicit conflict
resolution** between conflicting implementations of the same method, and that
turned out to be a pain in the ass with modules, so I gave up for a while.

## Introducing Traitor

So when I heard that MRI 2.0 had a release candidate with refinements, I
thought: well let's give it a try. FUN!!!

And so I did! [Traitor][traitor] is the result. Let's see how it works:

Let's say we want to have `Rectangle` objects that have color and shape. Those
two behaviors will be composed as traits, let's see `Colorable`:

```ruby
Colorable = Trait.new do
  attr_accessor :color

  def ==(other)
    other.color == color
  end
end
```

Easy. For now, `Colorable` only knows how to compare itself to other
`Colorable` objects. Let's try and `use` it from `Rectangle`:

```ruby
class Rectangle
  uses Colorable
end

blue, red  = Rectangle.new, Rectangle.new
blue.color = :blue
red.color  = :red

blue == red
# => false
```

Now let's implement the `Shapeable` trait:

```ruby
Shapeable = Trait.new do
  attr_accessor :sides

  def ==(other)
    other.sides == sides
  end
end
```

`Shapeable` knows how to compare itself to other `Shapeable` objects, through
the number of sides that it has.

Our `Rectangle` needs to be both, the problem is that if we `use` both traits,
since they have no hierarchy, a rectangle won't know how to respond to `#==`.
What implementation should it use, the `Colorable` or the `Shapeable`? No way of
knowing. When in doubt, Rectangle will always raise a trait conflict error:

```ruby
class Rectangle
  uses Colorable
  uses Shapeable

  # A Rectangle has 4 sides, thank God.
  def sides
    4
  end
end

Rectangle.new == Rectangle.new
# TraitConflict: Conflicting methods: #==
```

### Resolving conflicts explicitly

We must provide a mechanism to resolve the conflict in Rectangle, our host
class. Fortunately, it is as easy as defining our own version of `#==`:

```ruby
class Rectangle
  uses Shapeable
  uses Colorable

  # A Rectangle has 4 sides, thank God.
  def sides
    4
  end

  def ==(other)
    colorable_equal = trait_send(Colorable, :==, other)
    shapeable_equal = trait_send(Shapeable, :==, other)
    colorable_equal && shapeable_equal
  end
end
```

Now a `Rectangle` knows how to compare itself to other rectangles, via both its
shape and color.

The cool thing is that we have granular access to any implementation of our
traits via `trait_send`. That allows us to compose all implementations, ignore
some, or do whatever we want with them.

[noscript]: https://github.com/txus/noscript
[traitor]: https://github.com/txus/traitor
