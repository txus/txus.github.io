---
title: Introducing Hijacker - spy on your ruby objects!
layout: post
categories: [ruby, drb]
---

Ok, so yesterday I woke up with two things in mind: doing something with DRb,
and doing something with metaprogramming (just for the lulz). So I first
implemented a little spy pattern to spy on objects, classes and their
instances, mixed it with DRb, and came up with the hijacker idea.

You can check out **hijacker**'s source code in [the Github repo][repo].

So, let's get started. Hijacker is two things indeed: a server and a spy.

##Hijacker: the spy

On the **client** side (your ruby code), hijacker is a utility that lets you
**spy** on any object or class. (Spying on a class also implies spying on its
instances, unless you override this behavior.) The first step is telling
hijacker **where** to send its spy reports. This can be done at three levels:
either globally...

``` ruby
Hijacker.configure do

  # Sets the DRb uri to which the reports are sent.
  # (Ideally, where your hijacker server listens)
  uri 'druby://localhost:8787'
end
```

...or specifying a different uri for a particular spied object...

``` ruby
Hijacker.spy(my_object,
             :uri => 'druby://localhost:9999')
```

...or specifying an uri for a particular spied object WITHIN a block:

``` ruby
Hijacker.spying(my_object,
                :uri => 'druby://localhost:8787') do
  # do something with my_object
end
```

What I mean for **spying** is registering every method call on the spied
object, and reporting the **method name**, the **arguments** and the **return
value**.

Every time any activity on a spied object is registered, it is immediately sent
to the specified URI (may it be the global one, or specified by any other
means). There, on that URI, a patient hijacker server is waiting...

##Hijacker: the server

The hijacker server is fired up serving a particular handler, which will receive
those spy reports and handle them somehow. For now the only handler implemented
is Logger, so we start hijacker like this:

    $ hijacker logger

And the server is started with the Logger handler! It will tell you the DRb uri
you have to connect to from your ruby code.

The Logger handler receives the report and shows it in a colourful way, just
like a nice logger is expected to.

##Creative uses for the Logger handler

Ok, Logger is a very simple handler, but combined with the power of DRb and
Hijacker, it can be quite useful! Imagine you have two sensitive classes in
some ruby code, let's call them **PaymentGateway** and **PlanUpgrader**. To
avoid clutter in our logging server output we could use hijacker like this:

``` ruby
# We are only spying on instances of PaymentGateway and
# sending their activity to druby://localhost:2222
Hijacker.spy(PaymentGateway,
             :only => :instance_methods,
             :uri => 'druby://localhost:2222')

# Let's spy on PlanUpgrader's class methods, which upgrade
# users' paid plans, and send the activity to a different
# hijacker logging server in druby://localhost:3333
Hijacker.spy(PlanUpgrader,
             :only => :singleton_methods,
             :uri => 'druby://localhost:3333')

# If we had a veeery sensitive part of code where the user
# plan is sent some methods, we could spy *only* that part
# like this: (in this case the server uri is the global one)
Hijacker.spying(user.plan) do
  user.plan.change_with(untrusted_args)
  # do something else
end
```

##Extending Hijacker with your own handlers!

What if you wanted a handler that calculates the average number of arguments
sent to methods of a class? Or maybe keeping track of the mostly used object
types as arguments, to see if you could replace them with more lightweight
objects?

It is really easy to write your own handlers. Inside hijacker code, handlers
live here:

    lib/hijacker/handlers/your_handler.rb

They are autoloaded and automatically registered, so all you have to do is
write them like this:

``` ruby
module Hijacker
  # You only have to subclass Hijacker::Handler...
  class MyHandler < Handler

    # You must implement a class method named cli_options
    # which must return a Trollop-friendly Proc, for
    # command-line options parsing.
    #
    # These options can be accessed from within the
    # #handlemethod by calling the `opts` method.
    #
    def self.cli_options
      Proc.new {
        opt :without_foo,
            "Don't use foo to handle the method name"
        opt :using_bar,
            "Use bar as much as you can"
      }
    end

    # This is the most important method. This is what
    # is called every time a method call is performed
    # on a hijacked object. The received params look
    # like this:
    #
    #   method    :foo
    #
    #   args      [{:inspect => '3',
    #               :class => 'Fixnum'},
    #              {:inspect => '"string"',
    #               :class => 'String'}]
    #
    #   retval    [{:inspect => ':bar',
    #               :class => 'Symbol'}]
    #
    #   object    [{:inspect => '#<MyClass:0x003457>',
    #               :class => 'MyClass'}]
    #
    def handle(method, args, retval, object)
      # Do what you want with these!
    end

  end
end
```

Try to think of creative uses of hijacker, write your own handlers and send
them to me ZOMG I CAN HAZ MOAR HENDLARZ :3

Feel free to send me feedback on what do you think!

[repo]: http://github.com/txus/hijacker

