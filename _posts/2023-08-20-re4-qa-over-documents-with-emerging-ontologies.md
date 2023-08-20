---
layout: post
title:  "re4 - Q&A over documents with emerging ontologies"
date:   2023-08-20 08:08:00 +0000
---

These days, the space of question answering over documents with LLMs is bubbling with cool techniques. I've been thinking about some
creative ways to utilize external memory, and wanted to give the current techniques a bit of a different spin with knowledge graphs.

The classic approach (outlined in a [previous article]({% post_url 2020-07-09-finding-similar-documents-with-transformers %}))
looks something like this:

1. Chunk a set of documents into pieces, compute their embeddings, an store them in an embedding store alongside their raw text
2. On receiving a users's question, compute that question's embedding, and perform some sort of similarity search against the embedding store.
3. Retrieve the N most similar chunks, get their raw text and put it into an LLM prompt
4. Ask the LLM to answer the query using the information provided

## Adding passive recall and active reflection

By adding a knowledge graph into the equation (like Neo4J), we can store and retrieve triples of facts that may be useful (alongside the documents)
to produce a more meaningful and accurate answer.

Upon receiving a user's question, we want to:

1. **Retrieve**: retrieve the relevant documents from the embedding store, just as we'd do in the classic approach
2. **Recall**: with the user's question, consult the knowledge graph for relevant knowledge triples we may have on the topic
3. Produce an answer for the user.
4. **Reflect**: Update entries in the knowledge graph, adding or removing as needed according to the new information surfaced during the interaction.

Over time, the knowledge triples should make the answers better and better, mainly for similar questions. We could think of this 4-step loop as
making the agent exploit specific regions of the document corpus very well, curating and growing an emerging ontology around those regions.

However, for questions that target completely different parts of the document corpus, the agent is clueless, at least until it produces some ontology
about that region in its knowledge graph.

This could be regarded as a version of the [exploration-exploitation dilemma](https://en.wikipedia.org/wiki/Exploration-exploitation_dilemma){:target="_blank"}.
In order to add exploration to the mix, we can add an off-the-loop step that represents some form of intrinsic curiosity:

* **Research**: On demand or on a schedule, the agent will consult random, unseen documents from the embedding store and start actively reflecting on them, growing
a new ontology in a previously unexplored region of the document corpus.

With this 4th step, we have a system that should grow emerging ontologies by *passively exploiting* user-directed regions of the document corpus and also *actively exploring*
self-directed regions. The ontologies live in a knowledge graph that is human-readable (factual knowledge triples in Neo4J).

## Can I try this?

I've put together [re4](https://github.com/txus/re4){:target="_blank"}, a prototype of this idea. All you need is an OpenAI API key and Docker. Currently the prototype
is aimed towards codebases, but it should be easy to adapt to any document corpus.

## Further ideas to make it better

An interface to edit knowledge triples would be amazing, so that a user can prod the system for current knowledge that may be wrong, and guide it into rewriting
it. A user may even want to manually edit knowledge triples. Explainability at its best!

Check out the [Github repository](https://github.com/txus/re4){:target="_blank"} and enjoy!
