---
title: Rexpl - an interactive bytecode console for Rubinius
author: Txus
layout: post
categories: [rubinius, ruby]
---

## A bit of a background

Since the beginning of the year I've been gradually becoming more and more
interested in language design. Last December I [implemented Brainfuck][brainfuck],
the most trivial Turing-complete programming language, in pure Ruby.

In January I discovered [parslet][parslet], a really smart piece of software
written by [Kaspar Schiess][kschiess]). Having tried a [different approach][treetop]
at generating PEG parsers before, I found parslet quite smarter and friendlier
to use, so I decided that the best way of giving it a try would be rewriting my
Brainfuck implementation with it.

Since I liked it very much, in February I started [an attempt][schemer] to
implement the [Scheme programming language][scheme] in Ruby, using what I
already knew about parslet and trying to figure out the rest (not without the
help of this [awesome book][sicp] and a refreshing trip to Paris). Although
it's far from finished, it turned out to be an enlightening experience and it
just brought my interest on language design further.

## Rubinius

A few months earlier I had become aware of [Rubinius][rubinius], which up until
that point was nothing more than a strange word that came up whenever I typed
`rvm list` `known` in the terminal. It turned out to be an amazing project to
implement Ruby... in Ruby itself! Taking advantage of modern research about
language design, they are working hard to make it both faster and more
user-friendly than the most widely used implementation, MRI.

That was last October, when I attended to [Ruby And Rails Conf 2010][rubyandrails],
a really awesome Ruby conference held in Amsterdam, and there were not only
one, but two talks about Rubinius. The first one was given by
[Dirkjan Bussink][dbussink] about the Rubinius VM itself. The other was given
by [Christopher Bertels][bakkdoor], and he talked about [Fancy][fancy], his own
programming language, which was bootstrapped on the Rubinius VM.

I found those two talks really inspiring, and in May I rewrote (again) my
Brainfuck implementation targeting the Rubinius VM. It was a lot of fun! I also
freaked out a bit when I realized that every programming language, well,
everything in computing, could be represented by a stack and a memory heap.
Man! My educational background is in Political Science, where everything is
sooo high level :) So that was kind of shocking to me.

## Pen and paper

While tinkering with the Rubinius VM, I found it useful to have pen and paper
around whenever I was coding. Given a set of VM instructions, I'd go
instruction after instruction, drawing what the stack should look like at
each step. I'd freak out at net stack underflow errors, try to follow along the
instructions that were being executed, keeping track of the stack size and
spotting that extra (or missing) `pop`. As a last resort, I'd also go crying in
the #rubinius IRC channel on Freenode :)

Once, in that very channel, I asked if there was a way to print the stack after
a particular instruction. Since there wasn't, I started reading the Rubinius
source code and came up with a short script that seemed to do exactly that &mdash;
except that it didn't work at all, so I abandoned the idea, drown in sadness.

Then [Jeremy Tregunna][jtregunna], the guy behind [Metis][metis] (an
implementation of [IO][io] targeting the Rubinius VM), tweeted that he tried to
do the same, and that he wanted to bring it further, like an IRB for bytecode.
I thought this was even better than what I had in mind, so I started hacking on
it and came up with **rexpl**, a REPL for Rubinius bytecode.

## Installing and playing with rexpl

Rexpl is built to be a fun tool to use when learning how to use Rubinius
bytecode instructions, for example when bootstraping a new language targeting
the Rubinius VM for the first time.

Its main feature is **stack introspection**, which means you can inspect
what the stack looks like after each step of your instruction set.

After you have installed Rubinius (you can find how to do so in their website),
open a terminal and type:

    $ gem install rexpl
    $ rexpl

Now you should see a welcome banner and an IRB-like prompt, and you're good to
go! Just start typing some [VM instructions][vm_instructions] and see what
happens.

There are three extra commands to take advantage of the stack introspection:

* `list` lists the instruction set of the current program.
* `reset` empties the instruction set and starts a new program.
* `draw` prints a visual representation of the stack after each instruction of
your program.

Here's a screenshot of what it looks like:

![rexpl in action][screenshot]

## Contribute!

The first version of Rexpl was just released yesterday, so it should contain a
number of unforeseen use cases and unexpected behaviors.

If you have never played with the Rubinius VM, now it's time to start! You can
tinker around, make yourself comfortble and who knows, maybe you're the author
of the next mainstream programming language!

And if you already are a language master and dream in bytecode, you can surely
find a lot of edge cases where **rexpl** would need more work &mdash; which I
will be surely glad to do.

Make sure to check out [the Github repo][repo] and create a bunch of issues!
I love them :)

[parslet]: http://github.com/kschiess/parslet
[brainfuck]: http://github.com/txus/brainfuck
[treetop]: https://github.com/nathansobo/treetop

[schemer]: http://github.com/txus/schemer
[scheme]: http://en.wikipedia.org/wiki/Scheme_(programming_language)

[sicp]: http://mitpress.mit.edu/sicp
[rubinius]: http://rubini.us

[kschiess]: http://twitter.com/kasparschiess
[dbussink]: http://twitter.com/dbussink
[bakkdoor]: http://twitter.com/bakkdoor
[jtregunna]: http://twitter.com/jtregunna

[metis]: http://github.com/jeremytregunna/metis

[io]: http://www.iolanguage.com
[rubyandrails]: http://rubyandrails.eu
[fancy]: http://www.fancy-lang.org
[repo]: http://github.com/txus/rexpl
[vm_instructions]: http://rubini.us/doc/en/virtual-machine/instructions/

[screenshot]: https://camo.githubusercontent.com/b963d444a8ff8a05220d47416506c1a2d059b83a/687474703a2f2f646c2e64726f70626f782e636f6d2f752f323537313539342f726578706c2e706e67
