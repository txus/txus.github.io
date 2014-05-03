---
title: "Why I am excited about Clojure"
categories: clojure
---

I've been meaning to write about Clojure for some time now. Unfortunately, as
it often happens, I felt the urge to rewrite my entire blog in Clojure first,
and that delayed me a bit. So let's get to it!

My first real language was Ruby. I still do Ruby every day when doing client
work, mostly Rails apps. I still use Ruby to prototype lots of things -- it's
a pretty nice language, although it **requires a great deal of discipline** on
the programmer side to avoid common pitfalls.

I had been looking at Clojure for a while now, read a couple of books, watched
a couple of videos, but mostly just played around with it. In the past few
weeks I got the opportunity to write a small, simple service in Clojure for my
current client, and that made all the difference. From there on, prototyping
and writing other things with Clojure felt so much more natural. Here are some
thoughts and opinions that I got from that experience and why, right now,
Clojure is **my favorite practical programming language**. Bear in mind that
I'm neither a Clojure expert nor I'm claiming any of this as a fact -- it's
just my perceptions from having used it for a relatively short time in a
rather narrow set of problems.

## It feels designed

Coming from Ruby, that's one of Clojure's most shocking traits. Clojure took 2
solid years to be **designed** (before being even **built**). And one can tell
from the first impression already.

When I start using a language, there are usually some situations where I can't
understand why my code isn't doing what I expect it to do. When that happens
to me in a language like JavaScript, for example, finding out what the problem
was is generally a very frustrating experience -- mainly because when I
finally ask a more experienced JS developer they tell me:

_"Hahaha of course! You see, in JS there's this quirk. You have to work around
it by doing so and so."_

And then I lose it. What I "learn" when that happens is a workaround. It
doesn't feel like learning.

In Clojure on the other hand, I end up finding out **I was doing it wrong**
(for example, dealing with lazy sequences as if they were normal sequences),
and that the language provides either a way to do it right which is also
cleaner. It feels as if I had asked Rich Hickey (the creator of Clojure) and
he had told me:

_"Hahaha of course! Think again -- everything works like it's supposed to.
You're using the wrong function or the wrong data structure."_

That truly does feel like learning, and it makes me happy.

## It is extremely concise and elegant

Clojure is not a pure functional programming language, which makes it easier
to learn for everybody in general. But it is still *very functional*, and that
makes for very elegant, concise and powerful programs.

I find myself building programs from **very small, reusable functions**,
nicely composed together. Its super simple module system makes it really easy
to reason about both my program modules and their dependencies.

Plus, whenever some interface I've built feels awkward or there's some
duplication, often times I find that, while thinking and trying to refactor
it, **the language tries to drive me to the cleanest solution**. It feels
completely the opposite of fighting a language. It is there to help you reach
the cleanest, most elegant solution. For me, that is unprecedented, having
used Ruby, C, and JavaScript quite a lot.

## The workflow is awesome

In my opinion, one of the worst problems in the craft of programming is how
**we waste our brain power with terribly slow feedback loops**. The common
workflow in Clojure aims to fix this. _Note for the reader: this will not
surprise you if you're used to Lisps._

With Clojure your editor (be it Vim, Emacs, Light Table...) is permanently
connected to a live REPL. You continually develop, test and modify functions
with subsecond feedback. **Continuously**. All the cores in your brain are
lit, as you have literally no time to think about anything else. That's not
only deeply satisfying, but also leads you to certain thought paths that slow
feedback and its inevitable lower focus would have simply blocked. That's one
of my favorite parts of Clojure.

So, those are my current thoughts and feelings about Clojure, summarized. If
you haven't tried it, I highly recomment you to do it.
