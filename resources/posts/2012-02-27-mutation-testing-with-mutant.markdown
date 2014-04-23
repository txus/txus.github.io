---
title: Mutation testing with Mutant
layout: post
categories: ruby, testing, rubinius
---

Hi everyone! I know it has been a while since my last post (more than half a
year). I've spent this time learning a ton of interesting stuff, and done most
of my writing on the [Codegram blog][codegram-blog]. I'm preparing a series of
blogposts about what I've learned these past few months, so expect a bunch of
updates to my RSS feed :) Anyway, today I'm going to talk about [Mutant][repo].

## What is Mutant?

As stated in the project description:

> Mutant is a mutation tester. It modifies your code and runs your tests to make
> sure they fail. The idea is that if code can be changed and your tests don't
> notice, either that code isn't being covered or it doesn't do anything.

The first time I read about mutation testing, it was in the [RSpec
book][rspec-book]. It talked about [Heckle][heckle], a mutation testing library
that runs on Ruby 1.8.x.

Last year [Justin Ko][justinko] (from the [RSpec][rspec] core team) started a
rewrite running on [Rubinius][rubinius]: Mutant was born!

A few weeks ago Justin offered me to partner up for the project, and since
he's got a lot of work to do on the upcoming RSpec 3, I eventually
[took over Mutant][repo] to bring it to 1.0!

In fact today I released 0.1.1, which is pretty much usable, and I encourage you
to try it in your projects! It works in rubinius-head, either 1.8 or 1.9 mode,
and for now it supports only the RSpec testing framework (although support for
MiniTest and other frameworks will be very soon in Mutant).

Anyway, let's see a practical example about how to use it.

## Mutation testing

Mutation testing consists in programmatically modifying the code of a method
and asserting that the tests consequently fail.

First of all, let's install Rubinius head and the Mutant gem. To do it with
RVM, just type on the terminal:

    $ rvm install rbx-head
    $ rvm use rbx-head
    $ gem install mutant

Now imagine you had this Worker class in a file and another file with its spec:

```ruby
# worker.rb
class Worker
  attr_reader :job

  def get_job
    @job = :some_job
  end
end

# worker_spec.rb
$: << '.' # Hack for 1.9 mode
require 'rspec/autorun'
require 'worker'

describe Worker do
  before do
    @worker = Worker.new
  end

  it 'is free by default' do
    @worker.job.should be_nil
  end

  context 'when it has a job' do
    it 'should have :some_job' do
      @worker.get_job
      @worker.job.should_not be_nil
    end
  end
end
```

Let's run Mutant now:

    $ mutate "Worker#get_job" worker_spec.rb

It will first run the tests, making sure they pass, **then** mutate the
Worker's `#get_job` method, and pass the tests again. In this case, the method
is simple, so it performs a single mutation, as we can see in the output:

    Mutating line 6
      @job = :some_job >>> @job = :BcNKAyqRSfzMPGeiLmGekgAIxYua

And the test still passes after that, because it isn't checking for `@job`
being specifically `:some_job`, but just any truthy value. This means Mutant
will fail, because your test isn't asserting what it should.

If we changed the test to check that `@worker.job.should eq(:some_job)`, then
Mutant wouldn't complain and life would be good :)

## How can you help out?

Now that Mutant 0.1.1 is out, the best way to help out is to try and run it
on your projects. For now those should be projects using RSpec, but soon I'll
implement support for other frameworks (or maybe you will!). So you know what to
do:

    $ rvm install rbx-head
    $ rvm use rbx-head
    $ gem install mutant

And MUTATE ALL OF THE METHODS!

If you want to contribute with code or even just suggestions, I suggest you to
check out the [development roadmap][board]. It is open to comments and votes, so
you can see what's going on and participate.

I hope you guys enjoyed reading this post. See you on Github!

[codegram-blog]: http://blog.codegram.com
[heckle]: http://github.com/seattlerb/heckle
[justinko]: http://twitter.com/justinko
[rspec]: http://github.com/rspec
[rspec-book]: http://pragprog.com/book/achbd/the-rspec-book
[rubinius]: http://rubini.us
[repo]: http://github.com/txus/mutant
[board]: https://trello.com/board/mutant/4f452510101d860b203b542d
