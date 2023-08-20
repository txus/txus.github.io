---
layout: post
title:  "What are embeddings, really?"
date:   2022-08-15 16:08:00 +0000
---

*(Originally published in the [Factorial Labs blog](https://labs.factorialhr.com/posts/what-are-embeddings-really){:target="_blank"})*

Have you ever played with casting shadows as a child? Making shapes with your hand in front of a lightbulb, and watching the shadows cast, or projected, onto the wall.

Many different hand positions end up casting similar shadows, and generally, it’s a one-way street — you can’t really determine the original hand position from the shadow alone.

This is because it’s a **projection from a higher onto a lower-dimensional space**. There is some information loss, and that’s precisely the appeal of the game, guessing what shape it represents just from a blurry outline.

So what are projections useful for, other than a bit of wholesome fun? As it turns out, they help us simplify, summarise and compare complex categories. Let’s explore this with a familiar analogy: your time in high school.

## Back to high-school

As a high-school student, you were (and still are) a complex person, with a backstory, hopes and dreams, and a unique personality. There are many, many dimensions to you, as to all humans.

However, high school is all about grading with standardized scores. So the educational system strips you down of your unique person-hood and projects you onto, let’s say, a 5-dimensional space: Math, Language, History, Science, and Physical Education. 5 numbers, 5 scores at the end of the year that summarise who you are in the context of high-school.

Has most of the information about you been disregarded? Without a doubt. 5 numbers is a ridiculous over-simplification of you, but it is still a useful model to guide teachers and educators, and even to let you know when you’re weak in a certain area and need a boost. Those numbers are also useful for comparing yourself to others (which you probably did too often back then! 🙂).

## An AI-led high-school

Let’s take the analogy one step further. Imagine that you’re in high school again (oh no! 😱), but it’s an AI-driven high school in 2046. There are actually no teachers, and it’s just you hanging out with other students, reading books, and solving random problems.

There is an AI in the school that evaluates each and every student over the course of the year. But instead of pre-determined subjects (Math, Language, History, Science, Physical Education), the 5 scores are just labeled A, B, C, D, and E.

The AI observes you, and at the end of the first year, it gives you an 8.2 score in A, a 4 in B, and a 10 in C, D, and E. But, crucially, no one knows what any of these letters mean. What you’re sure though, is that the AI has been observing every student, and the score of every student in A can be compared to each other. You got 8.2, and your friend got 7, so in a way, you’re “more” or “better” in A than your friend, whatever that means.

The goal of the AI is to observe the things you do and **come up with the scoring system itself** to be able to grade all of you and decide, like a mysterious oracle, who makes it to the next course. Only the AI “knows” what A, B, C, D, or E means to itself. It has projected each of you onto this 5-dimensional space called an **embedding**, and it can use that embedding to summarise and compare you with other students.

But how can you explain your scores to your parents, if their meaning is totally opaque and only known to the AI?

Fortunately, there is a human in the loop —the school director. Their entire job is to try to reverse-engineer what A, B, C, D, and E stand for. Perhaps A represents punctuality, and B stands for motivation. Maybe C means symbolic reasoning, and D reflects physical flexibility. The director bridges the AI-learned scoring system with the human world, trying to imbue meaning to it.

## Embeddings in the real world

Many AI systems learn their own embeddings to simplify, summarise and compare things in the world. Take [GPT-3](https://en.wikipedia.org/wiki/GPT-3) for example. When you write three words, like “I am human”, GPT-3 projects each of these onto a lower-dimensional space, let’s say 5 numbers each (the **embedding size** is larger, but we’ll stay with 5 for the sake of argument).

After reading pretty much the entire internet and seeing trillions of words used in different contexts, GPT-3 has developed an intuition of how words relate to each other —it has learned to project them onto this 5-dimensional embedding space in a way that helps it do its job better (which is auto-completing your prompt with the most plausible text).

Again, this is quite inscrutable to us —maybe, the first number in the embedding represents the noun-ness of the word, the second could be whether it sounds peaceful or aggressive, and the third could be whether it’s a common or uncommon word. 

GPT-3 goes from the strings of letters “I”, “am”, and “human” (which have great meaning to us but not to itself) to a semantic representation of each word in the embedding space —it can work much better with those.

After embedding those words, there is a lot more going on, but this is commonly the first step AIs need to take in rationalizing arbitrary categories like words, student names, or anything other than real numbers, which is the only thing computers are comfortable dealing with.

## Embeddings in our brain?

Something similar must be going on in our brain. Even just the sound of a word, like the name of a person we know, evokes certain things in us —in a way, we are projecting the word (a very high-dimensional piece of information, because there are millions of words in our vocabulary) onto a lower-dimensional, semantically-rich representation, a set of a few electrical currents if you will. One of them can trigger our feeling of fondness or aversion towards that person, the other one represents how punctual we think they are in general, and the other one represents the distinctive shape of their eyebrows.

Again, we don’t know exactly what the stored representation in our brain —the embedding— really contains, but it’s been learned over time to shape our perception of that person and efficiently represent what might be relevant or important (from an adaptive standpoint, in evolutionary terms).

These electrical currents don’t represent the whole picture of who that person really is, but it helps make sense of it to us and produce very complex decisions and reactions in barely milliseconds.

## Embeddings are task-specific

We’ve seen that embeddings are learned, and they are always learned in the context of a task. The AI in high school observed us reading books and interacting with our friends, but couldn’t see how we interact with our parents, or with family members. That means those 5 scores can never tell the whole story, only the story that matters in a particular context, such as evaluating a student in high school.

A different AI, maybe one at the healthcare center, would learn very different categories and numbers from us, related to our physical fitness, mental health, genetic predisposition to certain things, etcetera.

## Why do embeddings work at all?

The key thing is *how we learn the embeddings* in the first place. And that’s determined entirely by the task we give the AI to solve.

The task back in the AI-driven high school was to **decide who would make it to the next course**, and we suppose that at some point, the AI got some feedback on how good that decision was (perhaps academic or athletic achievement later on, or success in the workplace) so that over time it got better at identifying the most relevant traits in students, which would help it make the decision at the end of the year based on those scores.

The better the task used to learn the embeddings is, the more useful the embeddings will be, that is, the more relevant categories will A, B, C, D, and E represent, and so the more sense it will make to compare students on any given category.

There is another useful phenomenon if we choose the task well: if we interpret those 5 scores as coordinates in a 5-dimensional space, we can assume that similar students will be projected to physically closer points in that space than dissimilar students. Next, we will see why this can be very powerful.

## A real-world example: Airbnb apartment listings

It is becoming quite commonplace, at least in the largest technology-driven organizations, to train AIs to learn embeddings about the things they care about the most. For example, Airbnb has embeddings about their offered apartments —I would guess they play a role in powering their recommendation system since the problem could be simplified as follows (we’ll be referring to 5-dimensional embeddings to keep it easy to talk about):

1. Train an AI to learn a 5-dimensional embedding for each apartment listed (a list of 5 numbers that represents that apartment), perhaps on the task of predicting bookings for a given season.
2. When a visitor is looking at a specific apartment on the site, take its embedding and interpret it as a point in 5-dimensional space, like coordinates.
3. Calculate the Euclidean distance (literally taking a straight line between point A and point B) between that point and the points representing every other apartment embedding, and take the apartments closest to it as possible recommendations.
4. The recommendations are of semantically similar apartments (that is, physically closer in that embedding space), so we can serve them to our customers.

Furthermore, it could be worth reverse-engineering those learned embeddings for us humans to learn about abstract features of those apartments: perhaps one of the scores tends to be higher in sea-side apartments and lower in mountain cabins. Those insights can help us make sense of vast amounts of unstructured data (assuming there is no “sea-ness” or “cabin-ness” structured field already in the Airbnb database).

## Conclusion

The most interesting data is unstructured. Evaluating students can’t be done on objective measures such as height or age, apartments can’t be compared on the amount of blue color in their pictures or the city where they’re in. If we want to truly make sense of the world, we must go deeper.

Humans are great at developing compact, efficient representations of anything: a piece of music we love, the smell of hot coffee on a winter Saturday morning, the awkward silence after our dad’s bad jokes.

Computers only truly understand numbers, but thanks to embeddings, they too can have a chance at projecting high-dimensional, complex concepts onto manageable representations in a lower-dimensional space they can navigate.

With your newly acquired intuition of what embeddings are, both at a philosophical and practical level, it won’t be long until you start seeing projections everywhere, perhaps fueling a drive to teach an AI about complex concepts that *you* care about.
