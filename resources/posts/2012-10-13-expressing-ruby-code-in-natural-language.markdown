---
layout: post
title: "Expressing Ruby code in natural language"
date: 2012-10-13 19:35
comments: true
categories: ruby
---

In my morning shower I'm normally still half asleep so my process of thought is
still pretty bizarre and dreamy. This explains why is then when I usually come
up with the weirdest ideas.

The other day I thought about the layer of translation that we apply when we
**read** code. Even with a language with a nice syntax like Ruby, we still
translate when reading.

Think of the typical situation: you're stuck with a bug. You've already spent 30
minutes staring at the code, uncapable of detecting what's wrong. In your
frustration, you ask a colleague to come and figure this out together. The first
thing you do is explain the code to your partner out loud: and then you
immediately realize where the problem is. This is called
[rubber duck debugging][rubberduck], because you could have solved the problem
by explaining it to a rubber duck on your desk.

Knowing this (because it happened to me seven thousand million times), and
thinking simultaneously about the [Isla programming language][isla] (an
educational programming language for young children), I thought about the
cognitive difficulty of learning to program. Harder and more complex syntax
equals more cognitive load, and that slows down people when learning to program.
That's why I think it's by far easier to start learning to program with a LISP,
or even with Ruby, rather than with Erlang.

So I coded this up:

## Explain, a Ruby source-to-natural-language compiler

[Explain][explain] is a special kind of a source-to-source compiler: it
translates Ruby code to English. This might be used by beginners to gain more
insight into what a given piece of code is doing. Let's see an example. Given
this Ruby code:

```ruby
class Person
  def walk(distance)
    @distance += distance
    @hunger += 2
  end

  def eat(food)
    @hunger -= food.nutritional_value
  end
end
```

When we run explain on it we get this:

    $ explain person.rb

    Let's describe the general attributes and behavior of any Person.

    A Person can **walk**, given a specific distance. This is described as
    follows: its distance will be its distance plus what we previously defined as
    `distance`. Finally we return its hunger will be its hunger plus the number
    2.. 

    A Person can **eat**, given a specific food. This is described as follows:
    Finally we return its hunger will be its hunger minus the result of calling
    **nutritional_value** on what we previously defined as `food`..
    And with this we're done describing a Person.

The quality of the translation is not very good yet, but it's a start.

In the future, **Explain** will also distinguish builtin Ruby methods (such as
**map**, **each**, **puts**) and explain them, so the description of the program
will be much more high level. Also, it will be able to output different formats,
and it might be a good idea to build a web service using it (so beginners can
access it even more easily).

If you're curious about the implementation, it uses the [Rubinius][rubinius]
builtin parser (Melbourne), which means that it runs only on Rubinius. You can
check the code at the [github repo][explain] and contribute with issues, ideas
or whatever! :)

For now it is pretty basic, but I think it's a good idea to build upon, and
might help people who are new to programming and to Ruby.

[rubberduck]: http://en.wikipedia.org/wiki/Rubber_duck_debugging
[isla]: http://islalanguage.org/
[rubinius]: http://rubini.us
[explain]: https://github.com/txus/explain
