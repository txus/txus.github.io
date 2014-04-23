---
layout: post
title: "Learning with Terror... VM"
categories: vm
---

Hello! I promised to write about what I've been learning these past few
months, so here I am. Today I'm going to write about TerrorVM. Dragons ahead!
Not really. If you don't know the first thing about how Virtual Machines work,
then you're at the same point I was a few months ago, so continue reading.

## How did I get interested in this?

I started playing with language design last year, mostly thanks to
[Rubinius][rubinius] being such an easy platform to target. I implemented a
[Brainfuck][brainfuck] compiler and eventually I started building the
[Noscript][noscript] programming language, an object-oriented, class-less
programming language running on the Rubinius VM.

For the record, building a programming language is certainly one of the most
rewarding experiences I've had in the programming world. It's challenging,
creative, and extremely fun.

Eventually I wondered: targeting a Virtual Machine is fun, but how do Virtual
Machines work? What's all the low-level, dirty work they do underneath all
those colourful balloons of joy? So I decided to try and build something
simple, just to learn the basics. As a little detour, whenever I want to learn
something new in programming I follow these steps:

### My way of learning new things

1. Read about the problem, but just superficially. Imagine how it could work
   internally, even if you have no idea.
2. Try and build an extremely simple version of that yourself, intuitively.
3. Whenever you encounter a design or conception problem, go read some more or
   ask someone who knows their shit.
4. Shape your prototype accordingly and try to expand it with new features.
5. Go to step 3.

It works, trust me.

## TerrorVM

The first problem I encountered: I didn't know C. So I went and read
Zed Shaw's [Learn C the Hard Way][lcthw] book. I learned the basics and moved
on!

So I opened a text editor and started coding a simple C program with a while
loop and a switch statement. No memory management, no real objects (just
integers), no local variables, no literal pool, just basic arithmetic
operations on a simple stack data structure.

At first it was stack-based, but then I took the Lua Virtual Machine (a
register-based virtual machine) as a reference and start reading about it. I
read [The Implementation of Lua 5][luaimpl], and from there I just read [its
source code][luasource] directly to understand what was going on. The fun
thing is: I understood nothing at first, but progressively, and by repeatedly
bothering [Jeremy Tregunna][jer] (thanks Jeremy!!) and reading about the
things he'd tell me, I was able to understand a bit more every time I read it.

[TerrorVM][repo] had been born. Technically it is a register-based, (naively)
garbage-collected, stackless Virtual Machine aimed at running dynamic
languages compiled down to its own (relatively compact) bytecode format.

## A proof-of-concept Ruby-to-Terror compiler

Once I had basic functionality working, I wondered: but I want to see a real
language running on my VM! So I wrote a simple Terror compiler: a program
written Rubinius that compiles a subset of Ruby to my bytecode format. Its
design is heavily inspired by the Rubinius compiler itself. It's easy to use!

It basically takes this:

```ruby
a = 123
b = false
if b
  print 'Goodbye world!'
else
  print "Hello world!"
end
```

And outputs this:

    _main
    :10:2:4:17
    123
    "print
    "Goodbye world!
    "Hello world!
    0x1000000
    0x51000000
    0x9010000
    0x51010100
    0x50020100
    0x21060200
    0x30030000
    0x2040100
    0x2050200
    0x80030405
    0x20050000
    0x30060000
    0x2070100
    0x2080300
    0x80060708
    0x8090000
    0x90090000

Then you can put this into a `.tvm` file and run it with TerrorVM! (Spoiler
alert: it will print `Hello world!` to the standard output). Read the
[Readme in the Github repo][repo] to learn more about how to try it yourself.

## Future plans

My idea is that TerrorVM should be able to run on both desktop computers and
Android devices, since it would be really cool to have dynamic languages on
Android.

I'd also like to have a decent, generational Garbage Collector, but blah blah
now I'm just being boring. Whatever comes to my mind as I learn further!

Anyway, take a look at [the Github repo][repo] if you like!

## Conclusion

As you can see, nearly every topic you'd like to learn about is approachable
if you take it easy, aren't afraid of it, and just do it. TerrorVM might
certainly not be the Virtual Machine of tomorrow, but what I'm learning while
building it -- that I'll keep forever.

[rubinius]: http://rubini.us
[brainfuck]: https://github.com/txus/brainfuck
[noscript]: https://github.com/txus/noscript
[luasource]: http://www.lua.org/source/5.2/
[luaimpl]: http://www.lua.org/doc/jucs05.pdf
[lcthw]: http://c.learncodethehardway.org/
[jer]: http://twitter.com/jtregunna
[repo]: https://github.com/txus/terrorvm
