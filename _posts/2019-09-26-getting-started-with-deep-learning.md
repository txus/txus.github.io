---
layout: post
title: "Getting started with Deep Learning"
date:   2019-09-26 07:30:00 +0000
---

*(Originally published in the [Codegram blog](https://www.codegram.com/blog/finding-similar-documents-with-transformers/){:target="_blank"})*

Learning a new skill in software engineering is always a little bit daunting —it means, after all, stepping outside your comfort zone. However, in my experience, Deep Learning can be particularly overwhelming, even to the most seasoned developers.

As a field that's fundamentally different from traditional software engineering, many developers read a book or two, try to power through a tutorial or a MOOC, and give up. For years, that was my case. I kept revisiting the same theory-dense material, and without a background in math or even computer science, even one gram too much of _"simple linear algebra"_ ended up putting me off for months at a time.

So, how can we overcome this glass wall that separates traditional software engineering from Deep Learning?

## Top-down first, then bottom-up: enter FastAI

Surprisingly, very few MOOCs take a project-based, hands-on approach to studying Deep Learning. However, I tend to find this kind of approach works best for software developers.

So the first thing you need to do is actually get to _building_ Deep Learning models, as quickly as you can, and _see them work_. This is the equivalent of _hello world_ programs when learning a new programming language —the simplest way to get from nothing to something running.

FastAI's [Practical Deep Learning for Coders](https://course.fast.ai) course is built exactly on that mindset. It's a free course with no ads, taught by FastAI founders [Jeremy Howard](https://twitter.com/jeremyphoward) and [Rachel Thomas](https://twitter.com/math_rachel). Not only it's incredibly hands-on from the start and _gets you building state of the art classifiers_ in the first few hours, but it is also _full of insights into the very bleeding edge_ of the Deep Learning space.

To follow the course, you'll be using Python, but don't worry if you've never used it before —you'll pick it up naturally, as you presumably can already code in another language.

Once you complete the course, it's time to reverse the learning process and go _bottom-up_. That's where [Part 2: Deep Learning from the Foundations](https://course.fast.ai/part2), comes into play. In this course you get to deconstruct all the ready-made tools you used in the first part, and build them from scratch. It's the equivalent of building your own web framework after becoming proficient at building Rails applications.

The second part is invaluable to deepen your understanding and ability to tweak model hyperparameters, and to even build new model architectures from scratch. It's what will get you from a competent practitioner to an expert deep learning engineer.

## Write code (and blog posts)

If you have ever attempted to learn a new programming language, a new framework or library, you have lived through this. No amount of articles or books can teach you what _actually sitting down and writing a program_ will.

So, as you go through the first course, sign up for [Kaggle](https://kaggle.com). Kaggle is an online platform for data science, where people share datasets, notebooks (a form of literate programming with code, results and explanations intertwined), and feedback. They also run competitions to improve the state of the art of Machine Learning models in general to solve particular problems.

Regardless of whether you have interest in a particular subfield of Deep Learning (be it computer vision, reinforcement learning, NLP...), you should start with the _Getting Started_ [Kaggle competitions](https://www.kaggle.com/competitions?sortBy=grouped&group=general&page=1&pageSize=20&category=gettingStarted) first —they will give you practice to solve a wide range of problems. For some of these problems, the use of Deep Learning (as opposed to more traditional Machine Learning algorithms) is quite unusual —take this as your chance to stand out and see if you can improve upon other approaches!

This exposure to different problems and techniques will give you the necessary breadth to feel more confident about tackling various kinds of problems. Most of what you learn in one subfield will help you get better at other subfields.

As you go along, you should **share your learnings** in the form of blog posts. Not only this will help other learners too, but it will help you solidify your knowledge, and become more visible.

## Capstone project

Once you're about half-way the first course, you'll have gotten a wider perspective on what is possible with Deep Learning. Try to apply one or many of the techniques you learned to **solve a problem you deeply care about**. It doesn't matter if it's an **image classifier**, **generative art**, or **speech recognition**. Anything you want to build, focus on that one project and try to get it working.

Then, keep going back to it and making it better —and get it into production! This will be a fantastic experience that will give you depth in a particular subfield of Deep Learning, as well as giving you production experience.

## Get involved with the FastAI community

You can learn a lot from other people, both from fellow learners and from experienced Deep Learning practitioners. The [the FastAI discussion forums](https://forums.fast.ai) are a fantastic community bubbling with new ideas, novel approaches to new and old problems, and general support for everyday coding of even the simplest tasks. No matter how small or big your question is, ask it there. And read as much as you can —it will make you realize that _Deep Learning is just another field_ that you can get really good at, and you can even end up _helping_ other learners in their path to mastery.

## Conclusion

Summarizing, the steps that will set you on the right path are:

1. Go through FastAI's [Practical Deep Learning for Coders](https://course.fast.ai) course. But like really go through it, as opposed to just watching the videos eating takeout like it's Netflix.
2. Grind your way through the [basic Kaggle competitions](https://www.kaggle.com/competitions?sortBy=grouped&group=general&page=1&pageSize=20&category=gettingStarted) first, then focus on any other that interests you. (For breadth.)
3. Write blogposts about anything you learn. It will help you solidify your knowledge, spark new questions, and grow your visibility in the community.
4. Pick _one_ project that really interests you, and try to make it as good as you can. (For depth.)
5. Hang out at [the FastAI discussion forums](https://forums.fast.ai) regularly, ask for help when you get stuck, and get inspiration for new projects and techniques.

Believe it or not, as you follow these steps you _are becoming_ a Deep Learning practitioner. Congratulations, and keep going —this is just the beginning!
