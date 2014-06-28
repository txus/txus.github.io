---
title: "A thought experiment: teaching programming"
categories: teaching
---

This post was going to be a couple of tweets, but I guess it got out of hand.
Let's make a little thought experiment and forget what we know about teaching
programming.

The thing about teaching is that it is usually incremental over
generations of teachers-learners: we teach very much in the way we were
taught, with some improvements though, maybe some new metaphors, adding some
new insights. But we stick to what we know worked when we were being taught.

An exammple: I know nothing about parenting myself, but if I were to raise
future children, I would definitely start from how my parents raised me.
A pervasive reading culture, music and books being omnipresent, musical
instruments, cats, dogs and other pets.

None of these elements necessarily contribute to good parenting, but that's my
reference point, all I know.

Back to teaching programming. While teaching at a number of
[Rails Girls][railsgirls] events I gained a couple of insights about how
absolute beginners learn programming. Their typical beginners workshop
consists of following a tutorial where the learner builds a small web
application with Rails. The steps are pretty detailed and carefully designed
to provide *wow* moments periodically.  Reinforcing feedback is seemingly
invaluable to beginners.

Some abstract concepts, however, are more difficult to teach. As the beginners
are taught about how to poke the system to accomplish what they want,
understanding underlying notions such as Model View Controller or the request
lifecycle are necessary to build an accurate mental model of what they are
building. And so I learned a lot just by trying different ways of explaining
such concepts, seeing what kind of metaphors made them *click* better.

As I've stated before, the way I teach programming is not very different from
the way I learned it. The only thing I do differently is trying to come up
with better metaphors thanks to my wider perspective. I never thought whether
that is actually the best way to learn about programming. I just know it was
"good enough" for me, and "good enough" almost invariably means being stuck in
a local maxima.

Let's now look at some common assumptions when teaching beginners to program.

## Common assumptions when teaching programming

When we teach beginners to program, programming is usually defined somewhat in
the lines of *describing precise instructions for the computer to execute in
order*. That means we start with the notion of *algorithm*.

A common metaphor, for example, is that of a kitchen recipe. From there it's
easy to teach the most primitive way of code reuse, namely the *goto* and *if*
statements. Those two concepts are naturally shown in the form of a *for loop*
(for n potatoes, peel a potato). This is more generally defined as *control
flow*.

Then we may introduce the concept of variables, *baskets* or *drawers* where
we can store values and retrieve them. We are teaching about *memory*, more
specifically *mutable memory*.

And so the *very first thing* we are telling a beginner when learning to
program is that programming is about *algorithms*, *control flow* and *mutable
memory*. But is programming really about that?

## Programming is better than that

Seasoned professional programmers will usually answer no. They'll come up with
a much higher level perspective, throwing in words such as *design*,
*architecture*, *interacting components*, *modularity*. None of this is even
mentioned to the beginner.

[Chris Granger][chrisgranger] recently picked an interesting definition of
programming: *data transformation*.

What if we taught beginners that programming is about transforming data into
other data? Or if we told them, more specifically: programming is about
*representing a real-world problem with data in the computer, then describing
transformations on this data until its shape solved the problem*.

With this definition, beginners approach programming not as an imperative
kitchen recipe, but as a higher-level, declarative description of a problem in
terms of data and its transformations.

But as good as this sounds, the current tools are not good enough to
accomplish that. The cognitive load associated with using a command-line, a
text editor, a compiler and a REPL for the first time is too much, even for
teaching for loops. We need better tools for teaching this kind of high-level
programming.

Chris Granger's *Aurora* seems not only like a very interesting and novel
approach to professional programming, but also a very good fit for teaching
it. Go [watch the video][aurora] if you haven't yet. I'm really looking
forward to its public release and the ripple of new ideas that it will bring.

## Conclusion

I would really like to experiment with such an approach to teaching
programming as data transformation, without a single mention to variable
assignment or for loops. I wonder what kind of effect would it have on the
following generations of programmers.

And if you are currently teaching somebody to program, why not giving it a
shot yourself?

[railsgirls]: http://railsgirls.com/
[chrisgranger]: http://www.chris-granger.com/
[aurora]: https://www.youtube.com/watch?v=L6iUm_Cqx2s
